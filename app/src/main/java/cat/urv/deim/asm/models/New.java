package cat.urv.deim.asm.models;


import java.util.ArrayList;

public class New {

    private int id;
    private String date;
    private String dateUpdate;
    private String imageURL;
    private String subtitle;
    private Tag[] tags;
    private String text;
    private String title;


    public New(int id, String date, String dateUpdate, String imageURL, String subtitle, Tag[] tags, String text, String title) {
        this.id = id;
        this.date = date;
        this.dateUpdate = dateUpdate;
        this.imageURL = imageURL;
        this.subtitle = subtitle;
        this.tags = tags;
        this.text = text;
        this.title = title;
    }

    @Override
    public String toString() {
        String stringTags = "";
        for(Tag tag : tags){
            stringTags += " " + tag.toString();
        }

        return "New{" +
                "ID='" + id + '\'' +
                ", date='" + date + '\'' +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", tags='" + stringTags + '\'' +
                ", text='" + text + '\'' +
                ", title='" + title + '\'' +
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

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
