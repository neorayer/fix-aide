package net.fixfixt.fixaide.restful;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Rui Zhou on 2018/5/6.
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NoFoundEntityException extends RuntimeException{

    public NoFoundEntityException() {
        this("No Found Entity");
    }

    public NoFoundEntityException(String msg) {
        super(msg);
    }
}
