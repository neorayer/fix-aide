package net.fixfixt.fixaide;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import net.fixfixt.fixaide.api.CrudServiceGrpc;
import net.fixfixt.fixaide.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Rui Zhou on 2018/5/5.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CrudService crudService;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Server
        InProcessServerBuilder.forName("inner")
                .addService(crudService)
                .build()
                .start();

    }

    ManagedChannel channel = InProcessChannelBuilder.forName("inner").usePlaintext().build();

    @Bean
    public CrudServiceGrpc.CrudServiceBlockingStub crudServiceBlockingStub() {
        return CrudServiceGrpc.newBlockingStub(channel);
    }



}
