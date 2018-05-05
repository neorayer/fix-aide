package net.fixfixt.fixaide;

import net.fixfixt.fixaide.model.DataDict;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Rui Zhou on 2018/4/28.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        DataDict dataDict = new DataDict();
        dataDict.setUuid("1111");

        SpringApplication.run(Application.class, args);
    }
}
