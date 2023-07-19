package com.babyblue.service;

import com.babyblue.api.IHelloService;

public class HelloService implements IHelloService {
    @Override
    public String sayHello(String something) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello " + something;
    }
}
