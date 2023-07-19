package com.babyblue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandler implements Runnable {

    final Socket socket;

    final Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            try {
                RpcRequest req = (RpcRequest) ois.readObject();
                Object obj = invoke(req);
                oos.writeObject(obj);
            } catch (Throwable t) {
                try {
                    ois.close();
                    oos.close();
                } catch (Throwable t_) {
                    t.addSuppressed(t_);
                }
                throw t;
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private Object invoke(RpcRequest req) throws Exception {
        Class<?> cls = Class.forName(req.getClassName());
        Method method = cls.getMethod(req.getMethodName(), req.getTypes());
        return method.invoke(service, req.getParams());
    }
}
