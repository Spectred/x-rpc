package pers.swd.rpc.common;

import lombok.Data;

@Data
public class RpcResponse {
    /**
     * 同请求ID
     */
    private String id;

    private String error;

    private Object result;
}
