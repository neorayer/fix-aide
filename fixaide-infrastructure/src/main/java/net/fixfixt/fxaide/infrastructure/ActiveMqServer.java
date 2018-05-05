package net.fixfixt.fxaide.infrastructure;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import java.net.URI;

/**
 * Created by Rui Zhou on 2018/5/1.
 */
public class ActiveMqServer {

    public static void main(String[] args) throws Exception {
        BrokerService broker = BrokerFactory.createBroker(new URI("xbean:activemq.xml"));

        broker.addConnector("tcp://localhost:61616");

        broker.start();
    }
}
