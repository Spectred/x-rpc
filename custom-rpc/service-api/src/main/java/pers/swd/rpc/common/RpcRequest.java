package pers.swd.rpc.common;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class RpcRequest {

    private String id;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] parameters;

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
