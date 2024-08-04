package ds.jms.story;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReceiptConsumers {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "ReceiptQueue"; // Name of the queue for receipts

    public static void main(String[] args) {
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            // Create connection factory
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            
            // Create connection
            connection = connectionFactory.createConnection();
            connection.start();
            
            // Create session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Create the queue (if it doesn't exist)
            Queue queue = session.createQueue(QUEUE_NAME);
            
            // Create consumer
            consumer = session.createConsumer(queue);
            
            // Start receiving messages
            System.out.println("Waiting for receipt messages...");
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            String text = ((TextMessage) message).getText();
                            System.out.println("Received receipt message: " + text);
                        } else {
                            System.out.println("Received message of unsupported type: " + message.getClass().getName());
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            
          
            Thread.sleep(100000); 
            
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (consumer != null) {
                    consumer.close();
                }
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
