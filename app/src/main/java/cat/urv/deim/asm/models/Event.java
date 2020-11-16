package cat.urv.deim.asm.models;

public class Event {

    private String description;
    private String imageURL;
    private String type;

    public Event(String description, String imageURL, String type) {
        this.description = description;
        this.imageURL = imageURL;
        this.type = type;
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
}
