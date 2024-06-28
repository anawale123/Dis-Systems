package ds.rmi.storysubmission;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StorySubmission extends Remote {
    String submitStory(String journalistName, String journalistId, String title, String content, int pageCount) throws RemoteException;
}
