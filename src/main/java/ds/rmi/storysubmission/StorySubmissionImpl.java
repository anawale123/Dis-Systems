package ds.rmi.storysubmission;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StorySubmissionImpl extends UnicastRemoteObject implements StorySubmission, Serializable {

    private static final long serialVersionUID = 1L;
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "ReceiptQueue";

    public StorySubmissionImpl() throws RemoteException {
        super();
    }

    @Override
    public String submitStory(String journalistName, String journalistId, String title, String content, int pageCount) throws RemoteException {
        String storySummary = "";

        System.out.println("StorySubmission component: submitStory() invoked...");
        System.out.println("Journalist Name: " + journalistName);
        System.out.println("Journalist ID: " + journalistId);
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("Page Count: " + pageCount);

        storySummary = "Story submitted by " + journalistName + " (" + journalistId + ") titled '" + title + "' with " + pageCount + " pages.";

        // Send the story using JMS
        sendMessageToJMS(journalistName, journalistId, title, content, pageCount);

        return storySummary;
    }

    private void sendMessageToJMS(String journalistName, String journalistId, String title, String content, int pageCount) {
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
            
            // Create and send the message
            String messageContent = "Receipt for your submission. Thank you!";
            TextMessage message = session.createTextMessage(messageContent);
            producer.send(message);

            System.out.println("Message sent to JMS: " + messageContent);
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
