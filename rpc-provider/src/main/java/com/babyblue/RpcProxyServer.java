package com.babyblue;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void publish(Object service, int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("客户端：" + socket.getPort() + "已连接");
                executor.execute(new ProcessHandler(socket, service));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
