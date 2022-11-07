package pers.swd.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import pers.swd.api.HelloService;

@Component
public class ConsumerComponent {

    @Reference
    private HelloService helloService;


    public String hello(String name){
        return helloService.hello(name);
    }
}
