package pers.swd.service.impl;

import pers.swd.api.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello," + name;
    }
}
