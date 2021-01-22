package cat.urv.deim.asm.models;

public class Calendar {
    private int id;
    private String date;
    private String description;
    private String hour;
    private String imageURL;
    private String name;
    private Tag[] tags;
    private String venue;


    public Calendar(int id, String date, String description, String hour, String imageURL, String name, Tag[] tags, String venue) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.hour = hour;
        this.imageURL = imageURL;
        this.name = name;
        this.tags = tags;
        this.venue = venue;
    }

    @Override
    public String toString() {
        String stringTags = "";
        for(Tag tag : tags){
            stringTags += " " + tag.toString();
        }
        return "Calendar{" +
                "ID='" + id + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", hour='" + hour + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", tags='" + stringTags + '\'' +
                ", venue='" + venue + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
