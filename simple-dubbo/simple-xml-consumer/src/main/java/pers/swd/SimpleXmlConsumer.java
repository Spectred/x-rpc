package pers.swd;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pers.swd.api.HelloService;

public class SimpleXmlConsumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-consumer.xml");
        context.start();

        HelloService helloService = context.getBean(HelloService.class);
        String result = helloService.hello("World");

        System.out.println(result);
    }
}