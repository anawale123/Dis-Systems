package ds.jms.story;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReceiptProducer {

    private static final String BROKER_URL = "tcp://localhost:61616"; // ActiveMQ broker URL
    private static final String QUEUE_NAME = "ReceiptQueue"; // Name of the queue for receipts

    public static void main(String[] args) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

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
            
            // Create producer
            producer = session.createProducer(queue);
            
            // Create message
            String messageContent = "Receipt for your service. Thank you!";
            TextMessage message = session.createTextMessage(messageContent);
            
            // Send message
            producer.send(message);
            System.out.println("Sent receipt message: " + messageContent);
            
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // Clean up
            try {
                if (producer != null) {
                    producer.close();
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
