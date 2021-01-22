package cat.urv.deim.asm.models;

public class Article {
    private int id;
    private String abstractText;
    private String author;
    private String date;
    private String dateUpdate;
    private String description;
    private String imageURL;
    private Tag[] tags;
    private String text;
    private String title;


    public Article(int id, String abstractText, String author, String date, String dateUpdate, String description, String imageURL, Tag[] tags, String text, String title) {
        this.id = id;
        this.abstractText = abstractText;
        this.author = author;
        this.date = date;
        this.dateUpdate = dateUpdate;
        this.description = description;
        this.imageURL = imageURL;
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
        return "Article{" +
                "ID='" + id + '\'' +
                ", abstractText='" + abstractText + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", dateUpdate='" + dateUpdate + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
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

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
