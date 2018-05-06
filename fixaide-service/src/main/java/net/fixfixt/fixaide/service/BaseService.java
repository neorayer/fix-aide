package net.fixfixt.fixaide.service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Created by Rui Zhou on 2018/5/6.
 */
public interface BaseService<T> {

    T create(T dataDict);

    List<T> findAll();

    Optional<T> read(T d);

    void delete(T dataDict);
}
