package net.fixfixt.fixaide.service;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by Rui Zhou on 2018/4/29.
 */
public class H2DatabaseServer {

    public static void main(String[] args) throws SQLException {
         Server server = Server.createTcpServer("-tcpPort", "9092", "-trace");
         server.start();
    }
}
