import Utils.BookCondition;
import Utils.RentalStatus;

import java.io.Serializable;
import java.util.Date;

public class Rent implements Serializable {
    //RentId id;
    Integer bookID;
    Integer clientID;
    Integer librarianID;
    Date rentDate;
    String bookCondition;

    public String getBookCondition() {
        return bookCondition;
    }

    public void setBookCondition(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    public Rent(){}

    public Rent(Integer bookID, Integer clientID, Integer librarianID, Date rentDate, String bookCondition) {
        this.bookID = bookID;
        this.clientID = clientID;
        this.librarianID = librarianID;
        this.rentDate = rentDate;
        this.bookCondition = bookCondition;
    }

    public Rent(Integer bookID, Integer clientID, Date rentDate, String bookCondition) {
        this.bookID = bookID;
        this.clientID = clientID;
        this.librarianID = 1;
        this.rentDate = rentDate;
        this.bookCondition = bookCondition;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public Integer getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(Integer LibrarianID) {
        this.librarianID = LibrarianID;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public String getRentalStatus() {
        return bookCondition;
    }

    public void setRentalStatus(String bookCondition) {
        this.bookCondition = bookCondition;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "bookID=" + bookID +
                ", clientID=" + clientID +
                ", LibrarianID=" + librarianID +
                ", rentDate=" + rentDate +
                ", rentalStatus=" + bookCondition +
                '}';
    }
}
