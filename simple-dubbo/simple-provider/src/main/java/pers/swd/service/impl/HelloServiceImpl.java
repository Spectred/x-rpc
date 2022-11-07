package pers.swd.service.impl;

import org.apache.dubbo.config.annotation.Service;
import pers.swd.api.HelloService;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello," + name;
    }
}
