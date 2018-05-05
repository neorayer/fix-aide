package net.fixfixt.fxaide.infrastructure;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Rui Zhou on 2018/5/1.
 */
public class ActiveMqClient {

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        TopicConnection topicConnection = factory.createTopicConnection();
        topicConnection.start();

        TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = topicSession.createTopic("topic0001");
        TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);

        TopicPublisher topicPublisher = topicSession.createPublisher(topic);
        Message message = topicSession.createTextMessage("Hello I'm message body");
        for(int i=0;i<10;i++) {
            topicPublisher.publish(message);
            System.out.println(i);
        }



    }
}
