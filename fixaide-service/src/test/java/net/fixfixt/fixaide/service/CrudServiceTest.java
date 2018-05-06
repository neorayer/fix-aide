package net.fixfixt.fixaide.service;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import net.fixfixt.fixaide.api.DataDictServiceGrpc;
import net.fixfixt.fixaide.api.DataDictServiceGrpc.DataDictServiceBlockingStub;
import net.fixfixt.fixaide.api.EnvelopeHelper;
import net.fixfixt.fixaide.api.Grpc.Envelope;
import net.fixfixt.fixaide.model.DataDict;
import net.fixfixt.fixaide.repository.DataDictRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan("net.fixfixt.fixaide")
public class CrudServiceTest {

    @Autowired
    private DataDictRepository repository;

    @Autowired
    private CrudService service;

    @Autowired
    EnvelopeHelper envelopeHelper;

    DataDictServiceBlockingStub stub;

    @PostConstruct
    public void init() throws IOException {
        // Server
        InProcessServerBuilder.forName("inner")
                .addService(service)
                .build()
                .start();

        ManagedChannel channel = InProcessChannelBuilder.forName("inner").usePlaintext().build();
        stub = DataDictServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void add() throws IOException {
        // Domain Model
        DataDict dataDict = new DataDict();
        dataDict.setName("Standard FIX.4.4");
        dataDict.setUuid(UUID.randomUUID().toString());
        dataDict.setCreatedTime(LocalDateTime.now());

        // Envelope
        Envelope envelope = envelopeHelper.envelopeBuilder(dataDict).build();

        stub.create(envelope);

        Iterator<Envelope> envelopes = stub.findAll(Envelope.newBuilder().build());
        while(envelopes.hasNext()) {
            Envelope enve = envelopes.next();
            DataDict dataDict1 = envelopeHelper.payload(enve, DataDict.class);
            System.out.println("************************");
            System.out.println(dataDict1);
            System.out.println("************************");
        }
    }
}
