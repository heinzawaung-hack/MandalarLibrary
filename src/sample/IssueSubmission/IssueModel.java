package sample.IssueSubmission;

import java.sql.Timestamp;

public class IssueModel {
    private int bookId;
    private int memberId;
    private Timestamp timestamp;
    private int renew;

    public IssueModel(int bookId) {
        this.bookId = bookId;
    }

    public IssueModel(int bookId, int memberId) {
        this.bookId = bookId;
        this.memberId = memberId;
    }

    //Get All Data
    public IssueModel(int bookId, int memberId, Timestamp timestamp, int renew) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.timestamp = timestamp;
        this.renew = renew;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getRenew() {
        return renew;
    }

    public void setRenew(int renew) {
        this.renew = renew;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}

