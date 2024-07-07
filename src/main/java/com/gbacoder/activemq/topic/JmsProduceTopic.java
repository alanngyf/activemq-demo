package com.gbacoder.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author alanulog
 * @create 7/7/2024 2:44 PM
 *
 * consumer needs to start first if we use topic
 *
 */
public class JmsProduceTopic {

    public final static String ACTIVEMQ_URL = "tcp://192.168.2.100:61616";
    public final static String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer producer = session.createProducer(topic);

        for (int i = 0; i < 10; i++) {
            TextMessage textMessage = session.createTextMessage("topic: " + i);
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();

        System.out.println("*** msg sent to MQ successfully");
    }
}
