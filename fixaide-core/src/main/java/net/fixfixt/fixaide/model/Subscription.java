package net.fixfixt.fixaide.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Rui Zhou on 2018/5/6.
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Subscription {

    @Id
    @NonNull
    private String uuid;

    private String topic;

    private String subscriber;
}
