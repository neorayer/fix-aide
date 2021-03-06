package net.fixfixt.fixaide.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class DataDict {

    @Id
    @NonNull
    private String uuid;

    private String name;

    private LocalDateTime createdTime;
}
