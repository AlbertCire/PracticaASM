package cat.urv.deim.asm.models;

public class Event {

    private String description;
    private String imageURL;
    private String name;
    private String tags;
    private String type;
    private String webURL;


    public Event(String description, String imageURL, String name, String tags, String type, String webURL) {
        this.description = description;
        this.imageURL = imageURL;
        this.name = name;
        this.tags = tags;
        this.type = type;
        this.webURL = webURL;
    }

    @Override
    public String toString() {
        return "Event{" +
                "description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }
}
