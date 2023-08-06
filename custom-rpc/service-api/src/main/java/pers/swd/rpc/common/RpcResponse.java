package pers.swd.rpc.common;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class RpcResponse {
    /**
     * 同请求ID
     */
    private String id;

    private String error;

    private Object result;

    public RpcResponse() {
    }

    public RpcResponse(String id) {
        this.id = id;
    }


    public static RpcResponse parse(String str) {
        return JSON.parseObject(str, RpcResponse.class);
    }

    public <T> T parseResult(Class<T> clazz) {
        return JSON.parseObject(result.toString(), clazz);
    }
}
