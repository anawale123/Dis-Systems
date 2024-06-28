package ds.rmi.storysubmission;

import ds.rmi.storysubmission.StorySubmission;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StorySubmissionServer {
    
    public static final int PORT = 1041;
    public static final String HOST = "localhost";
    public static final String SERVICE_NAME = "StorySubmissionService";

    public static void main(String[] args) {
        try {
            System.out.println("SERVER: Registering StorySubmission Service");
            LocateRegistry.createRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry(PORT);
            
            StorySubmission remoteComponent = new StorySubmissionImpl();
            registry.rebind("rmi://" + HOST + ":" + PORT + "/" + SERVICE_NAME, remoteComponent);
            
            System.out.println("SERVER: Ready...");
        } catch (Exception e) {
            System.out.println("SERVER: Failed to register StorySubmission Service: " + e);
        }
    }
}
