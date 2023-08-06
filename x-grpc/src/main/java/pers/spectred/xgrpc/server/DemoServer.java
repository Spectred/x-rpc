package pers.spectred.xgrpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import pers.spectred.xgrpc.server.service.AddServiceImpl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DemoServer {

    public static void main(String[] args) throws IOException {
        // 创建Server实例,添加服务接口
        Server server = ServerBuilder.forPort(9999)
                .addService(new AddServiceImpl())
                .build();
        // 启动Server
        server.start();

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> System.out.println("Server is running..."), 0, 3, TimeUnit.SECONDS);
    }
}