package net.fixfixt.fixaide.restful;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import net.fixfixt.fixaide.api.CrudServiceGrpc;
import net.fixfixt.fixaide.api.EnvelopeHelper;
import net.fixfixt.fixaide.api.Grpc.Envelope;
import net.fixfixt.fixaide.model.DataDict;
import net.fixfixt.fixaide.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rui Zhou on 2018/5/5.
 */

@RestController
public class DataDictController {

    @Autowired
    private CrudService crudService;

    private EnvelopeHelper envelopeHelper = new EnvelopeHelper();

    private CrudServiceGrpc.CrudServiceBlockingStub stub;

    public DataDictController() {

        ManagedChannel channel = InProcessChannelBuilder.forName("inner").usePlaintext().build();
        stub = CrudServiceGrpc.newBlockingStub(channel);
    }

    @GetMapping("/data-dict/")
    public List<DataDict> findAll() {
        Envelope request = Envelope.newBuilder().build();
        Iterator<Envelope> envelopeIterator = stub.findAll(request);

        return envelopeHelper.payloads(envelopeIterator, DataDict.class);
    }

    @PostMapping("/data-dict/")
    public String create(@RequestBody  DataDict dataDict) {
        dataDict.setCreatedTime(LocalDateTime.now());
        Envelope request = envelopeHelper.envelopeBuilder(dataDict, Envelope.PayloadEnc.JSON)
                .build();
        stub.create(request);

        return "OK";
    }

}
