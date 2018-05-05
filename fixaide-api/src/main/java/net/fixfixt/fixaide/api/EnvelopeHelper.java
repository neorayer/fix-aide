package net.fixfixt.fixaide.api;

import com.google.common.collect.Lists;
import net.fixfixt.fixaide.api.Grpc.Envelope;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Rui Zhou on 2018/5/5.
 */
public class EnvelopeHelper {

    public Map<Envelope.PayloadEnc, PayloadCodec> payloadCodecMap = new HashMap<>();

    public EnvelopeHelper() {
        payloadCodecMap.put(Envelope.PayloadEnc.JSON, new GsonPayloadCodec());
    }

    private PayloadCodec codec(Envelope.PayloadEnc enc) {
        PayloadCodec payloadCodec = payloadCodecMap.get(enc);
        if (payloadCodec == null) {
            throw new RuntimeException("No payload codec for " + enc);
        }
        return payloadCodec;
    }

    public <T> T payload(Envelope envelope, Class<T> payloadClass) {
        PayloadCodec payloadCodec = codec(envelope.getPayloadEnc());

        return (T) payloadCodec.decode(envelope.getStrPayload(), payloadClass);
    }

    public <T> List<T> payloads(Iterator<Envelope> envelopeIterator, Class<T> payloadClass) {
        return Lists.newArrayList(envelopeIterator).stream()
                .map(envelope -> payload(envelope, payloadClass))
                .collect(Collectors.toList());
    }

    public Envelope.Builder envelopeBuilder(Object original, Envelope.PayloadEnc payloadEnc) {
        PayloadCodec payloadCodec = codec(payloadEnc);

        return Envelope.newBuilder()
                .setPayloadEnc(payloadEnc)
                // TODO here should verify the encoded content type
                .setStrPayload((String) payloadCodec.encode(original));
    }
}
