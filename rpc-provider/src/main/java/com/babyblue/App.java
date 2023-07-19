package com.babyblue;

import com.babyblue.service.HelloService;

public class App {

    public static void main(String[] args) {
        HelloService helloService = new HelloService();
        RpcProxyServer server = new RpcProxyServer();
        System.out.println("在8888端口开始监听");
        server.publish(helloService, 8888);
    }
}
