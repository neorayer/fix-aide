package net.fixfixt.fixaide.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.api.Grpc;
import net.fixfixt.fixaide.model.DataDict;
import net.fixfixt.fixaide.repository.DataDictRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Rui Zhou on 2018/5/6.
 */
@Slf4j
public class DataDictService {

    @Autowired
    private DataDictRepository dataDictRepository;

    public DataDict create(DataDict dataDict) {
        dataDictRepository.save(dataDict);
        return dataDict;
    }

    public List<DataDict> findAll() {
        return StreamSupport.stream(dataDictRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<DataDict> read(DataDict dataDict) {
        return dataDictRepository.findById(dataDict.getUuid());
    }

    public void delete(DataDict dataDict) {
        dataDictRepository.delete(dataDict);
    }
}
