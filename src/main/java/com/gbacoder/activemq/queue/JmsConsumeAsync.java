package com.gbacoder.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author alanulog
 * @create 7/7/2024 2:24 PM
 */
public class JmsConsumeAsync {

    public final static String ACTIVEMQ_URL = "tcp://192.168.2.100:61616";
    public final static String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage msg = (TextMessage) message;
                    String text = null;
                    try {
                        text = msg.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                    System.out.println("***onMessageListener: Consumer obtains msg: "+ text);
                }
            }
        });
        System.in.read();

        consumer.close();
        session.close();
        connection.close();

    }
}
