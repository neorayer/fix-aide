package net.fixfixt.fixaide.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.api.DictExplorerServiceGrpc.DictExplorerServiceImplBase;
import net.fixfixt.fixaide.api.Grpc;
import net.fixfixt.fixaide.api.Protos;
import net.fixfixt.fixaide.model.DictExplorer;
import net.fixfixt.fixaide.repository.DictExplorerRepository;
import net.fixfixt.fixaide.service.util.ProtoConvertor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
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

    private LongAdder longAdder = new LongAdder();

    @Override
    public void subscribe(Grpc.SubRequest subRequest,
                          StreamObserver<Grpc.SubResponse> subResponse) {
        subResponse.onNext(Grpc.SubResponse.newBuilder()
                .setRespType(Grpc.SubResponse.RespType.SUB_SUCC)
                .build());

        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> {
            String fixMsg = "8=FIX.4.4\u000125=" + longAdder.intValue();
            longAdder.increment();
            subResponse.onNext(Grpc.SubResponse.newBuilder()
                    .setRespType(Grpc.SubResponse.RespType.PUB_DATA)
                    .setStrData(fixMsg)
                    .build());
        }, 0, 1, TimeUnit.SECONDS);
    }

}
