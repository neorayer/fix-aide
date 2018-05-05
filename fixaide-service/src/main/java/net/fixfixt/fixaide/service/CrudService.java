package net.fixfixt.fixaide.service;

import io.grpc.stub.StreamObserver;
import net.fixfixt.fixaide.api.CrudServiceGrpc;
import net.fixfixt.fixaide.api.EnvelopeHelper;
import net.fixfixt.fixaide.api.Grpc.Envelope;
import net.fixfixt.fixaide.model.DataDict;
import net.fixfixt.fixaide.repository.DataDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

/**
 * Created by Rui Zhou on 2018/5/5.
 */
@Service
public class CrudService extends CrudServiceGrpc.CrudServiceImplBase {

    @Autowired
    private DataDictRepository dataDictRepository;

    private EnvelopeHelper envelopeHelper = new EnvelopeHelper();

    @Override
    public void create(Envelope envelope, StreamObserver<Envelope> observer) {
        DataDict dataDict = envelopeHelper.payload(envelope, DataDict.class);
        dataDictRepository.save(dataDict);

        Envelope responseEnvelope = Envelope.newBuilder()
                .setDirection(Envelope.Direction.RESPONSE)
                .setRequestId(envelope.getRequestId())
                .setPayloadEnc(Envelope.PayloadEnc.PLAIN)
                .setStrPayload("Save OK").build();

        observer.onNext(responseEnvelope);
        observer.onCompleted();
    }

    @Override
    public void findAll(Envelope envelope, StreamObserver<Envelope> observer){
        StreamSupport.stream(dataDictRepository.findAll().spliterator(), false)
                .map(this::payloadToEnvelope)
                .forEach(observer::onNext);
        observer.onCompleted();
    }

    private Envelope payloadToEnvelope(DataDict dataDict) {
        return envelopeHelper.envelopeBuilder(dataDict, Envelope.PayloadEnc.JSON).build();
    }
}
