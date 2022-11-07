package pers.swd;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SimpleXmlMain {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("dubbo-provider.xml");
        context.start();
        System.in.read();
    }
}