package pers.spectred.xgrpc.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import pers.spectred.xgrpc.AddRequest;
import pers.spectred.xgrpc.AddResponse;
import pers.spectred.xgrpc.AddServiceGrpc;

public class DemoClient {


    /**
     * 声明Channel
     */
    ManagedChannel managedChannel;
    /**
     * 声明Stub 存根
     */
    AddServiceGrpc.AddServiceBlockingStub stub;


    public DemoClient() {
        // 使用ManagedChannelBuilder创建Channel
        managedChannel = ManagedChannelBuilder.forAddress("localhost", 9999).usePlaintext().build();
        // 通过Channel获取存根对象
        stub = AddServiceGrpc.newBlockingStub(managedChannel);
    }

    public static void main(String[] args) {
        // 准备参数
        int a = 1, b = 2;
        // 创建客户端
        DemoClient client = new DemoClient();
        // 构造请求
        AddRequest addRequest = AddRequest.newBuilder().setA(a).setB(b).build();
        // 调用服务
        AddResponse addResponse = client.stub.add(addRequest);
        // 打印响应 (3)
        System.out.println(addResponse.getResult());
    }
}

