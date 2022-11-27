package pers.swd;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class AddServer extends AddServiceGrpc.AddServiceImplBase {

    public static void main(String[] args) throws IOException {
        Server server = ServerBuilder.forPort(9999).addService(new AddServer()).build();
        server.start();
        System.out.println(server.getPort());
        while (true) {

        }
    }

    @Override
    public void add(AddRequest request, StreamObserver<AddResponse> responseObserver) {
        int res = myAdd(request.getA(), request.getB());
        responseObserver.onNext(AddResponse.newBuilder().setRes(res).build());
        responseObserver.onCompleted();
    }

    public int myAdd(int a, int b) {
        return a + b;
    }
}
