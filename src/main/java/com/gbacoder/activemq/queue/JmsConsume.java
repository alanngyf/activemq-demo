package com.gbacoder.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author alanulog
 * @create 7/6/2024 9:31 PM
 */
public class JmsConsume {

    public final static String ACTIVEMQ_URL = "tcp://192.168.2.100:61616";
    public final static String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                String text = message.getText();
                System.out.println("*** Consumer obtains msg: "+ text);
            } else {
                break;
            }
        }

        // close resource
        consumer.close();
        session.close();
        connection.close();

    }
}
