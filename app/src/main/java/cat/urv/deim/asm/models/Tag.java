package cat.urv.deim.asm.models;

public class Tag {
    private String description;
    private String name;

    public Tag(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
