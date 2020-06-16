package sample.Database;

public class DatabaseModel {
    private int id;
    private String title;
    private String author;
    private String publisher;
    private String isAvail;

    public DatabaseModel(int id, String title, String author, String publisher, String isAvail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isAvail = isAvail;
    }

    public DatabaseModel(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsAvail(String isAvail) {
        this.isAvail = isAvail;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsAvail() {
        return isAvail;
    }
}
