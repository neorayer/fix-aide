package net.fixfixt.fixaide.service;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import net.fixfixt.fixaide.api.DictExplorerServiceGrpc;
import net.fixfixt.fixaide.api.DictExplorerServiceGrpc.DictExplorerServiceBlockingStub;
import net.fixfixt.fixaide.api.Protos;
import net.fixfixt.fixaide.repository.DictExplorerRepository;
import net.fixfixt.fixaide.service.util.ProtoConvertor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DictExplorerServiceTest {

    @Autowired
    DictExplorerRepository repository;

    ProtoConvertor protoConvertor = new ProtoConvertor();

    @Test
    public void add() {
        // create service
        DictExplorerService service = new DictExplorerService(repository, protoConvertor);

        // create proto message
        Protos.DictExplorer dictExplorerProto = Protos.DictExplorer.newBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setName("Standard FIX.4.4")
                .build();

        // call service: add
        service.add(dictExplorerProto, Observers.stdoutObserver);

        // call service: findAll
        List<Protos.DictExplorer> dictExplorers = new LinkedList<>();
        service.findAll(
                Protos.Empty.newBuilder().build(),
                Observers.of(dictExplorers::add, v -> v.printStackTrace(), () -> {
                    Protos.DictExplorer d0 = dictExplorers.get(0);
                    assertEquals("Standard FIX.4.4", d0.getName());
                }));
    }

    @Test
    public void add2() throws IOException {
        // create service
        DictExplorerService service = new DictExplorerService(repository, protoConvertor);
        InProcessServerBuilder.forName("inner")
                .addService(service)
                .build()
                .start();

        ManagedChannel channel = InProcessChannelBuilder.forName("inner").usePlaintext().build();

        DictExplorerServiceBlockingStub stub = DictExplorerServiceGrpc.newBlockingStub(channel);
        // create proto message
        Protos.DictExplorer dictExplorerProto = Protos.DictExplorer.newBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setName("Standard FIX.4.4")
                .build();

        stub.add(dictExplorerProto);

        Iterator<Protos.DictExplorer> dictExplorers = stub.findAll(Protos.Empty.getDefaultInstance());
        Protos.DictExplorer d0 = dictExplorers.next();
        assertEquals("Standard FIX.4.4", d0.getName());
    }
}
