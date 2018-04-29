package net.fixfixt.fixaide.service.util;

import com.google.protobuf.Message;
import net.fixfixt.fixaide.api.Protos;
import net.fixfixt.fixaide.model.DictExplorer;
import org.springframework.stereotype.Component;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@Component
public class ProtoConvertor {

    public Protos.DictExplorer toProto(DictExplorer dictExplorer) {
        return Protos.DictExplorer.newBuilder()
                .setUuid(dictExplorer.getUuid())
                .setName(dictExplorer.getName())
                .build();
    }
}
