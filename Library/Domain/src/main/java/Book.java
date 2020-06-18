import Utils.RentalStatus;

import java.io.Serializable;

public class Book implements Serializable {
    Integer id;
    String title;
    String author;
    String rentalStatus;
    String bookCondition;

    public Book(){}

    public Book(String title, String author, String bookCondition) {
        this.title = title;
        this.author = author;
        this.rentalStatus = RentalStatus.UNDISPOSED.toString();
        this.bookCondition = bookCondition;
    }

    public Book(Integer id, String title, String author, String rentalStatus, String bookCondition) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rentalStatus = rentalStatus;
        this.bookCondition = bookCondition;
    }

    public Book(Integer id, String title, String author, String bookCondition) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.rentalStatus = rentalStatus;
        this.bookCondition = bookCondition;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    @Override
    public String toString() {
        return  title + '\'' +
                "de " + author ;
    }
}
