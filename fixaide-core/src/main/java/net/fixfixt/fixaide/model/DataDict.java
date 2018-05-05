package net.fixfixt.fixaide.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@Data
@Entity
public class DataDict {

    @Id
    private String uuid;

    private String name;

    private LocalDateTime createdTime;
}
