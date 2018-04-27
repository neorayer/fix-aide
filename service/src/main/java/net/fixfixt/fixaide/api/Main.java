package net.fixfixt.fixaide.api;


import net.fixfixt.fixaide.api.Grpc.*;
import net.fixfixt.fixaide.api.Protos.*;

/**
 * Created by Rui Zhou on 2018/4/27.
 */
public class Main {

    public static void main(String[] args) {
        DictExplorer dictExplorer = DictExplorer.newBuilder().setName("Standard FIX.4.4").build();

        System.out.println("***********");
        System.out.println(dictExplorer);
        System.out.println("***********");
    }
}
