package com.gbacoder.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author alanulog
 * @create 7/6/2024 8:24 PM
 */
public class JmsProduce {

    public final static String ACTIVEMQ_URL = "tcp://http://192.168.2.100:61616";
    public final static String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        // 1. create activemq connection factory
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 2. create connection from factory
        Connection connection = factory.createConnection();
        connection.start();

        // 3. create session
        // Transacted: This parameter specifies whether the session is transacted or not.
        // Acknowledge Mode: This parameter specifies how the session will acknowledge the receipt of messages.
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4. create destination (queue or topic)
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5. create producer
        MessageProducer producer = session.createProducer(queue);

        // 5.1 send message
        for (int i = 0; i < 10; i++) {
            // 5.2 create text message
            TextMessage textMessage = session.createTextMessage("msg: " + i);
            // 5.3 producer sends msg to mq broker
            producer.send(textMessage);
        }

        // 6. close resource
        producer.close();
        session.close();
        connection.close();

        System.out.println("****msg sent successfully");
    }
}
