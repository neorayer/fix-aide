package net.fixfixt.fixaide.api;

import com.google.gson.Gson;

/**
 * Created by Rui Zhou on 2018/5/5.
 */
public class GsonPayloadCodec implements PayloadCodec<String> {

    private Gson gson = new Gson();

    @Override
    public <D> D decode(String encodedContent, Class<D> payloadClass) {
        return gson.fromJson(encodedContent, payloadClass);
    }

    @Override
    public String encode(Object original) {
        return gson.toJson(original);
    }
}
