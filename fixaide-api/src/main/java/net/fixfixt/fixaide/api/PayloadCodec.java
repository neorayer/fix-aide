package net.fixfixt.fixaide.api;

/**
 * Created by Rui Zhou on 2018/5/5.
 *
 * @param <T> the type of encoded content.
 */
public interface PayloadCodec<T> {

    <D> D decode(String encodedContent, Class<D> payloadClass);

    T encode(Object original);
}
