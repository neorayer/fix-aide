package net.fixfixt.fixaide.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.api.DictExplorerServiceGrpc.*;
import net.fixfixt.fixaide.api.Protos;
import net.fixfixt.fixaide.model.DictExplorer;
import net.fixfixt.fixaide.repository.DictExplorerRepository;
import net.fixfixt.fixaide.service.util.ProtoConvertor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;


/**
 * Created by Rui Zhou on 2018/4/28.
 */
@Service
@Slf4j
public class DictExplorerService extends DictExplorerServiceImplBase {

    private DictExplorerRepository dictExplorerRepository;

    private ProtoConvertor protoConvertor;

    public DictExplorerService(DictExplorerRepository dictExplorerRepository,
                               ProtoConvertor protoConvertor) {
        this.dictExplorerRepository = dictExplorerRepository;
        this.protoConvertor = protoConvertor;

    }

    @Override
    public void add(Protos.DictExplorer dictExplorerProto,
                    StreamObserver<Protos.DictExplorer> responseObserver) {
        DictExplorer dictExplorer = new DictExplorer();
        dictExplorer.setUuid(dictExplorerProto.getUuid());
        dictExplorer.setName(dictExplorerProto.getName());
        dictExplorer.setCreatedTime(LocalDateTime.now());

        dictExplorerRepository.save(dictExplorer);

        responseObserver.onNext(dictExplorerProto);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Protos.Empty request,
                        StreamObserver<Protos.DictExplorer> responseObserver) {
        StreamSupport.stream(dictExplorerRepository.findAll().spliterator(), false)
                .map(protoConvertor::toProto)
                .forEach(responseObserver::onNext);

        responseObserver.onCompleted();
    }

}
