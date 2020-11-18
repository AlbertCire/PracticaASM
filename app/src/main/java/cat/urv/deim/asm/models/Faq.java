package cat.urv.deim.asm.models;

public class Faq {
    private String body;
    private String title;

    public Faq(String body, String title) {
        this.body = body;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Faq{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
