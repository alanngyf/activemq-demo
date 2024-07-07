package com.gbacoder.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Text;

import javax.jms.*;
import java.io.IOException;

/**
 * @author alanulog
 * @create 7/7/2024 2:50 PM
 *
 * consumer needs to start first if we use topic
 */
public class JmsConsumeTopic {
    public final static String ACTIVEMQ_URL = "tcp://192.168.2.100:61616";
    public final static String TOPIC_NAME = "topic01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("*** starting consumer 1...");

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener((message) -> {

            if (null != message && message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                try {
                    String text = msg.getText();
                    System.out.println("topic----message---" + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
