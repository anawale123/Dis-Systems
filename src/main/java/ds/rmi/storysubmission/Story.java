package ds.rmi.storysubmission;

import java.io.Serializable;
//story.java 
public class Story implements Serializable {
    private String name;
    private String id;
    private String title;
    private String content;
    private int page;

    // Constructors, getters, setters
    // Constructor are to initialize the story object with the jounralist name 
    public Story(String name, String id, String title, String content, int page) {
        this.name = name;
        this.id = id;
        this.title = title;
        this.content = content;
        this.page = page;
    }

    //Getters and setters are used  to get and set attributes.
    // name if the journalist 
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }

    // Id of the journalist 
    public String getId() { 
        return id; 
    }
    public void setId(String id) { 
        this.id = id; 
    }

    // Title of the journalist 
    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        this.title = title; 
    }

    // Content that is input by the joournalist
    public String getContent() { 
        return content; 
    }
    public void setContent(String content) { 
        this.content = content; 
    }

    // number of pages input by the journalist 
    public int getPage() { 
        return page; 
    }
    public void setPage(int page) { 
        this.page = page; 
    }
}
 