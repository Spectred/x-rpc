package pers.spectred.xgrpc.server.service;

import io.grpc.stub.StreamObserver;
import pers.spectred.xgrpc.AddRequest;
import pers.spectred.xgrpc.AddResponse;
import pers.spectred.xgrpc.AddServiceGrpc;

public class AddServiceImpl extends AddServiceGrpc.AddServiceImplBase {

    @Override
    public void add(AddRequest request, StreamObserver<AddResponse> responseObserver) {
        int a = request.getA();
        int b = request.getB();
        responseObserver.onNext(AddResponse.newBuilder().setResult(a + b).build());
        responseObserver.onCompleted();
    }
}