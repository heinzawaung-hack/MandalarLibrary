package sample.IssueSubmission;

import sample.Database.Database;
import sample.Tools.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {
    public int issueBook(IssueModel issueModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int bookId = issueModel.getBookId();
        int memberId = issueModel.getMemberId();
        String saveData = "insert into ISSUE (bookId,memberId) values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(saveData);
        preparedStatement.setInt(1,bookId);
        preparedStatement.setInt(2,memberId);
        int issueCount = preparedStatement.executeUpdate();
        System.out.println("Book Issue successfully");
        return issueCount;
    }

    //This method is, if issue a book , in BOOk table's book isAvail column will change to false
    public void changeAvail(IssueModel issueModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int bookId = issueModel.getBookId();
        String check  = "false";
        String change = "update BOOK set isAvail=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(change);
        preparedStatement.setString(1,check);
        preparedStatement.setInt(2,bookId);
        preparedStatement.executeUpdate();
        System.out.println("Available changed to false");
    }

    public List<IssueModel> getDataWithId(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String getData = "select * from ISSUE where bookId=" + id + "";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getData);
        List<IssueModel> list = new ArrayList<>();
        if (resultSet.next()){
            int bookId = resultSet.getInt("bookId");
            int memberId = resultSet.getInt("memberId");
            Timestamp timestamp = resultSet.getTimestamp("issueTime");
            int renew = resultSet.getInt("renew");
            IssueModel issueModel = new IssueModel(bookId,memberId,timestamp,renew);
            list.add(issueModel);
        }else {
            Message message = new Message();
            message.errorMessage("404","There is no ID that you entered");
        }
        return list;
    }

    public void deleteDataWithId(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String deleteData = "delete from ISSUE where bookId=" + id + "";
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteData);
        System.out.println("Delete data successfully");
    }

    //This method is, if user return book(submission) isAvail to true on Book database
    public void trueAvail(IssueModel issueModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int bookId = issueModel.getBookId();
        String check  = "True";
        String change = "update BOOK set isAvail=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(change);
        preparedStatement.setString(1,check);
        preparedStatement.setInt(2,bookId);
        preparedStatement.executeUpdate();
        System.out.println("Available changed to true");
    }

    public void renewBook(IssueModel issueModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int id = issueModel.getBookId();
        String renew = "update ISSUE set issueTime=CURRENT_TIMESTAMP,renew=renew+1 where bookId=" + id + "";
        Statement statement = connection.createStatement();
        statement.executeUpdate(renew);
        System.out.println("Book renewed success");
    }
}
