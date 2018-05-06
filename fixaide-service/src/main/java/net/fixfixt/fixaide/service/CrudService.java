package net.fixfixt.fixaide.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.api.CrudServiceGrpc;
import net.fixfixt.fixaide.api.EnvelopeHelper;
import net.fixfixt.fixaide.api.Grpc.Envelope;
import net.fixfixt.fixaide.repository.DataDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Created by Rui Zhou on 2018/5/5.
 */
@Service
@Slf4j
public class CrudService extends CrudServiceGrpc.CrudServiceImplBase{

    @Autowired
    private DataDictRepository dataDictRepository;

    @Autowired
    private EnvelopeHelper envelopeHelper;

    @PostConstruct
    private void postConstruct() {

    }

    private BaseService baseService(String type) {
        return null;
    }

    private void handleException(StreamObserver observer, Exception e)  {
        log.error("", e);
        observer.onError(Status.INTERNAL.withCause(e).asRuntimeException());
    }

    @Override
    public void create(Envelope request, StreamObserver<Envelope> observer) {
        try {
            Object payload = envelopeHelper.payload(request);
            baseService(request.getPayloadType()).create(payload);

            Envelope response = envelopeHelper.response(request, payload);
            observer.onNext(response);
            observer.onCompleted();
        } catch (Exception e) {
            handleException(observer, e);
        }
    }

    @Override
    public void findAll(Envelope request, StreamObserver<Envelope> observer) {
        try {
            StreamSupport.stream(dataDictRepository.findAll().spliterator(), false)
                    .map(d -> envelopeHelper.response(request, d))
                    .forEach(observer::onNext);
            observer.onCompleted();
        } catch (Exception e) {
            handleException(observer, e);
        }
    }

    @Override
    public void read(Envelope request, StreamObserver<Envelope> observer) {
        try {
            System.out.println();
            DataDict dataDict = envelopeHelper.payload(request, DataDict.class);
            Optional<DataDict> optional = dataDictRepository.findById(dataDict.getUuid());
            if (!optional.isPresent()) {
                observer.onError(Status.NOT_FOUND.asException());
            } else {
                optional.map(d -> envelopeHelper.response(request, d)).ifPresent(observer::onNext);
                observer.onCompleted();
            }
        } catch (Exception e) {
            handleException(observer, e);
        }
    }

    @Override
    public void delete(Envelope request, StreamObserver<Envelope> observer) {
        try {
            DataDict dataDict = envelopeHelper.payload(request, DataDict.class);
            dataDictRepository.delete(dataDict);
            observer.onNext(envelopeHelper.response(request, dataDict));
            observer.onCompleted();
        } catch (Exception e) {
            handleException(observer, e);
        }
    }

}
