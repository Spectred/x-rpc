package pers.swd.rpc.consumer.proxy;

import pers.swd.rpc.common.RpcRequest;
import pers.swd.rpc.common.RpcResponse;
import pers.swd.rpc.consumer.client.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class RpcClientProxy {

    public static Object createProxy(Class serviceClazz) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serviceClazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest rpcRequest = buildRpcRequest(method, args);
                RpcClient rpcClient = new RpcClient("localhost", 8899);
                try {
                    Object responseMsg = rpcClient.send(rpcRequest.toJSONString());
                    RpcResponse rpcResponse = RpcResponse.parse(responseMsg.toString());
                    if (rpcResponse.getError() != null) throw new RuntimeException(rpcResponse.getError());
                    return rpcResponse.parseResult(method.getReturnType());
                } catch (Exception e) {
                    throw e;
                } finally {
                    rpcClient.close();
                }

            }
        });
    }

    private static RpcRequest buildRpcRequest(Method method, Object[] args) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setId(UUID.randomUUID().toString());
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        return rpcRequest;
    }
}
