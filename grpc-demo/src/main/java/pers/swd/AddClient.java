package pers.swd;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class AddClient {

    ManagedChannel ch;
    AddServiceGrpc.AddServiceBlockingStub stub;

    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        AddClient client = new AddClient();
        AddResponse res = client.stub.add(AddRequest.newBuilder().setA(a).setB(b).build());
        System.out.println(res.getRes());
    }

    public AddClient() {
        ch = ManagedChannelBuilder.forAddress("localhost", 9999).usePlaintext().build();
        stub = AddServiceGrpc.newBlockingStub(ch);
    }
}
