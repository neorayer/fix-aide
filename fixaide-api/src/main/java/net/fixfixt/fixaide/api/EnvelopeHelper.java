package net.fixfixt.fixaide.api;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.fixfixt.fixaide.api.Grpc.Envelope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Rui Zhou on 2018/5/5.
 */
@Slf4j
@Component
public class EnvelopeHelper {

    private Map<Envelope.PayloadEnc, PayloadCodec> payloadCodecMap = new HashMap<>();

    private Envelope.PayloadEnc payloadEnc = Envelope.PayloadEnc.JSON;

    public EnvelopeHelper() {
        payloadCodecMap.put(Envelope.PayloadEnc.JSON, new GsonPayloadCodec());
    }

    public EnvelopeHelper(Envelope.PayloadEnc payloadEnc) {
        this();
        this.payloadEnc = payloadEnc;
    }

    public Object payload(Envelope envelope) {
        try {
            Class payloadClass = Class.forName(envelope.getPayloadType());
            PayloadCodec payloadCodec = codec(envelope.getPayloadEnc());

            return payloadCodec.decode(envelope.getStrPayload(), payloadClass);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }


    public Envelope.Builder envelopeBuilder(Object original) {
        return envelopeBuilder(original, payloadEnc);
    }


    public Envelope response(Envelope request, Object payload) {
        return envelopeBuilder(payload)
                .setDirection(Envelope.Direction.RESPONSE)
                .setRequestId(request.getRequestId())
                .build();
    }

    public Object call(Object requestPayload, Function<Envelope, Envelope> caller) {
        try {
            Envelope request = envelopeBuilder(requestPayload).build();
            Envelope response = caller.apply(request);

            Class payloadClass = Class.forName(response.getPayloadType());
            PayloadCodec payloadCodec = codec(response.getPayloadEnc());

            return payloadCodec.decode(response.getStrPayload(), payloadClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List callList(Object requestPayload,
                         Function<Envelope, Iterator<Envelope>> caller) {
        Envelope request = envelopeBuilder(requestPayload).build();
        Iterator<Envelope> response = caller.apply(request);

        return Lists.newArrayList(response).stream()
                .map(this::payload)
                .collect(Collectors.toList());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Begin Private Methods
    ////////////////////////////////////////////////////////////////////////////

    private PayloadCodec codec(Envelope.PayloadEnc enc) {
        PayloadCodec payloadCodec = payloadCodecMap.get(enc);
        if (payloadCodec == null) {
            throw new RuntimeException("No payload codec for " + enc);
        }
        return payloadCodec;
    }

    private Envelope.Builder envelopeBuilder(Object original, Envelope.PayloadEnc payloadEnc) {
        PayloadCodec payloadCodec = codec(payloadEnc);

        return Envelope.newBuilder()
                .setPayloadEnc(payloadEnc)
                // TODO here should verify the encoded content type
                .setStrPayload((String) payloadCodec.encode(original));
    }
}
