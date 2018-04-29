package net.fixfixt.fixaide.service;


import io.grpc.Server;
import io.grpc.ServerBuilder;
import net.fixfixt.fixaide.repository.DictExplorerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by Rui Zhou on 2018/4/27.
 */
@SpringBootApplication
@EnableJpaRepositories("net.fixfixt.fixaide")
@EntityScan("net.fixfixt.fixaide")
public class Launcher implements CommandLineRunner {

    @Value("${fxaide.service.port}")
    private int port;

    @Autowired
    private DictExplorerService dictExplorerService;

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:tcp://localhost:9092/~/db-fxaide");
//        dataSource.setUsername("sa");
//        return dataSource;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Server server = ServerBuilder.forPort(port)
                .addService(dictExplorerService)
                .build()
                .start();

        server.awaitTermination();
    }
}
