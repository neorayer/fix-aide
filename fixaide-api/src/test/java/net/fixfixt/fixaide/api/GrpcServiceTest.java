package net.fixfixt.fixaide.api;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;

import net.fixfixt.fixaide.api.DictExplorerServiceGrpc;
import net.fixfixt.fixaide.api.DictExplorerServiceGrpc.*;
import net.fixfixt.fixaide.api.Protos.*;


/**
 * Created by Rui Zhou on 2018/4/29.
 */
public class GrpcServiceTest {

    @Test
    public void test() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 10240)
                .usePlaintext()
                .build();
        DictExplorerServiceBlockingStub stub = DictExplorerServiceGrpc.newBlockingStub(channel);

        DictExplorer dictExplorer =  DictExplorer.newBuilder()
                .setName("Standard FIX.4.4")
                .setUuid("10001")
                .build();

        stub.add(dictExplorer);

        channel.shutdown();
    }

}
