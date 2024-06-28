package ds.rmi.storysubmission;

import ds.rmi.storysubmission.StorySubmission;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class StorySubmissionClient {
    
    public static final int PORT = 1041;
    public static final String HOST = "localhost";
    public static final String SERVICE_NAME = "StorySubmissionService";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get input from the journalist
        System.out.println("Enter Journalist Name: ");
        String journalistName = scanner.nextLine();

        System.out.println("Enter Journalist ID: ");
        String journalistId = scanner.nextLine();

        System.out.println("Enter Story Title: ");
        String title = scanner.nextLine();

        System.out.println("Enter Content: ");
        String content = scanner.nextLine();

        System.out.println("Enter Page Counts: ");
        int pageCount = scanner.nextInt();
        scanner.close();

        String registryName = "rmi://" + HOST + ":" + PORT + "/" + SERVICE_NAME;
        System.out.println("CLIENT: Looking up " + registryName + " ...");

        try {
            Registry registry = LocateRegistry.getRegistry(PORT);
            StorySubmission remoteService = (StorySubmission) registry.lookup(registryName);
            
            // Submit the story via RMI
            String response = remoteService.submitStory(journalistName, journalistId, title, content, pageCount);
            System.out.println("SERVER RESPONSE: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
