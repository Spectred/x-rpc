package pers.swd.rpc.consumer;

import pers.swd.rpc.api.IUserService;
import pers.swd.rpc.consumer.proxy.RpcClientProxy;
import pers.swd.rpc.pojo.User;

public class ClientBootstrap {

    public static void main(String[] args) {
        IUserService userService = (IUserService) RpcClientProxy.createProxy(IUserService.class);
        User user = userService.getById(1);
        System.out.println(user);
    }
}
