package net.fixfixt.fixaide.api;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.Test;

import net.fixfixt.fixaide.api.DictExplorerServiceGrpc;
import net.fixfixt.fixaide.api.DictExplorerServiceGrpc.*;
import net.fixfixt.fixaide.api.Protos.*;

import java.io.IOException;


/**
 * Created by Rui Zhou on 2018/4/29.
 */
public class GrpcServiceTest {

    @Test
    public void test() throws IOException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 10240)
                .usePlaintext()
                .build();
        DictExplorerServiceBlockingStub blockingStub = DictExplorerServiceGrpc.newBlockingStub(channel);

        DictExplorer dictExplorer =  DictExplorer.newBuilder()
                .setName("Standard FIX.4.4")
                .setUuid("10001")
                .build();

        blockingStub.add(dictExplorer);

        DictExplorerServiceStub stub = DictExplorerServiceGrpc.newStub(channel);
        stub.subscribe(Grpc.SubRequest.newBuilder().setTopic("topic1").build(), new StreamObserver<Grpc.SubResponse>(){

            @Override
            public void onNext(Grpc.SubResponse subResponse) {
                System.out.println("**********");
                System.out.println(subResponse);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {

            }
        });

        //channel.shutdown();

        System.in.read();
    }

}
