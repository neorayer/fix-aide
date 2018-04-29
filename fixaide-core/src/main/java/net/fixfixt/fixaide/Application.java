package net.fixfixt.fixaide;

import net.fixfixt.fixaide.model.DictExplorer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        DictExplorer dictExplorer = new DictExplorer();
        dictExplorer.setUuid("1111");

        SpringApplication.run(Application.class, args);
    }
}
