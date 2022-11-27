package pers.swd.rpc.provider.handler;

import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import pers.swd.rpc.common.RpcRequest;
import pers.swd.rpc.common.RpcResponse;
import pers.swd.rpc.provider.annotation.RpcService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RpcServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    private static final Map<String, Object> SERVICE_INSTANCE_MAP = new ConcurrentHashMap<>();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 1. 接收客户端请求 - msg转换为RpcRequest
        RpcRequest rpcRequest = JSON.parseObject(msg, RpcRequest.class);
        RpcResponse rpcResponse = new RpcResponse(rpcRequest.getId());

        // 2. 业务处理
        try {
            Object res = handler(rpcRequest);
            rpcResponse.setResult(res);
        } catch (Exception e) {
            e.printStackTrace();
            rpcResponse.setError(e.getMessage());
        }
        // 3. 响应客户端
        ctx.writeAndFlush(JSON.toJSONString(rpcResponse));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (CollectionUtils.isEmpty(serviceMap)) return;
        serviceMap.forEach((id, bean) -> {
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (interfaces.length == 0) throw new RuntimeException("服务必须实现接口");
            // 默认取第一个接口作为缓存bean的名称
            String name = interfaces[0].getName();
            SERVICE_INSTANCE_MAP.put(name, bean);
        });
    }


    public Object handler(RpcRequest rpcRequest) throws InvocationTargetException {
        Object bean = SERVICE_INSTANCE_MAP.get(rpcRequest.getClassName());
        if (bean == null) throw new RuntimeException("根据beanName找不到服务,beanName=" + rpcRequest.getClassName());

        Class<?> clazz = bean.getClass();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();

        // 反射调用bean的方法 - cglib
        FastClass fastClass = FastClass.create(clazz);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(bean, parameters);
    }
}
