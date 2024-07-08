package com.gbacoder.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author alanulog
 * @create 7/7/2024 2:24 PM
 *
 * MessageListener
 * Asynchronous Processing: With a MessageListener, messages are consumed asynchronously. You set up a listener that is automatically notified when a message arrives.
 * Event-driven: The MessageListener interface is a callback interface. When a message arrives, the onMessage method is called automatically.
 * Non-blocking: Since it operates asynchronously, the listener does not block the application, allowing it to perform other tasks while waiting for messages.
 * Implementation: You implement the MessageListener interface and register it with the consumer.
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

                if (null != message && message instanceof MapMessage) {
                    MapMessage msg = (MapMessage) message;
                    try {
                        String text = msg.getString("k1");
                        System.out.println("***onMessageListener: Consumer obtains MapMessage: k1----"+ text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();

        consumer.close();
        session.close();
        connection.close();

    }
}
