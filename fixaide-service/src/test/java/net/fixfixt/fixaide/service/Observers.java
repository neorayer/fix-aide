package net.fixfixt.fixaide.service;

import io.grpc.stub.StreamObserver;

import java.util.function.Consumer;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
public class Observers {

    public static final StdoutObserver stdoutObserver = new StdoutObserver();

    /**
     * This StreamObserver prints the response or error to stdout.
     *
     * @param <T>
     */
    public static class StdoutObserver<T> implements StreamObserver<T> {

        @Override
        public void onNext(T o) {
            System.out.println(o);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            System.out.println("comleted");
        }
    }

    public static <T> StreamObserver<T> of(Consumer<T> onNext,
                                           Consumer<Throwable> onError,
                                           Runnable onCompleted) {
        return new StreamObserver<T>() {

            @Override
            public void onNext(T t) {
                onNext.accept(t);
            }

            @Override
            public void onError(Throwable throwable) {
                onError.accept(throwable);
            }

            @Override
            public void onCompleted() {
                onCompleted.run();
            }
        };
    }
}
