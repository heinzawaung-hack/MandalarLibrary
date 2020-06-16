package sample.Database;

import sample.Tools.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO {

    public void insertData(DatabaseModel databaseModel) throws ClassNotFoundException, SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int id = databaseModel.getId();
        String title = databaseModel.getTitle();
        String author = databaseModel.getAuthor();
        String publisher = databaseModel.getPublisher();
        String isAvail = databaseModel.getIsAvail();
        String insertData = "insert into BOOK(id,title,author,publisher,isAvail) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertData);
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,title);
        preparedStatement.setString(3,author);
        preparedStatement.setString(4,publisher);
        preparedStatement.setString(5,isAvail);
        preparedStatement.execute();
        System.out.println("Insert Data Successfully");
    }

    public List<DatabaseModel> getAllData() throws ClassNotFoundException, SQLException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String retrieveData = "select * from BOOK";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(retrieveData);
        List<DatabaseModel> list = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            String isAvail = resultSet.getString("isAvail");
            DatabaseModel databaseModel = new DatabaseModel(id,title,author,publisher,isAvail);
            list.add(databaseModel);
        }
        return list;
    }

    public void deleteWithID(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String deleteData = "delete from BOOK where id=" + id + " ";
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteData);
        System.out.println("Delete Row Success, You deleted ID " + id);
    }

    public List<DatabaseModel> checkWithId(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String checkId = "select title,author from BOOK where id=" + id + "";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(checkId);
        List<DatabaseModel> list = new ArrayList<>();
        if (resultSet.next()){
            String bookName = resultSet.getString("title");
            String author = resultSet.getString("author");
            DatabaseModel databaseModel = new DatabaseModel(bookName,author);
            list.add(databaseModel);
        }else {
            Message message = new Message();
            message.errorMessage("Not Found","This id is not found on database");
        }
        return list;
    }

    public void editData(DatabaseModel databaseModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int id = databaseModel.getId();
        String bookName = databaseModel.getTitle();
        String author = databaseModel.getAuthor();
        String publisher = databaseModel.getPublisher();
        String isAvail = databaseModel.getIsAvail();
        String editData = "update BOOK set title=?,author=?,publisher=?,isAvail=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(editData);
        preparedStatement.setString(1,bookName);
        preparedStatement.setString(2,author);
        preparedStatement.setString(3,publisher);
        preparedStatement.setString(4,isAvail);
        preparedStatement.setInt(5,id);
        preparedStatement.executeUpdate();
        System.out.println("Edit Data Success");
    }

    public List<DatabaseModel> getAllDataWithID(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String checkId = "select * from BOOK where id=" + id + "";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(checkId);
        List<DatabaseModel> list = new ArrayList<>();
        if (resultSet.next()){
            int bookId = resultSet.getInt("id");
            String bookName = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            String isAvail = resultSet.getString("isAvail");
            DatabaseModel databaseModel = new DatabaseModel(bookId,bookName,author,publisher,isAvail);
            list.add(databaseModel);
        }else {
            Message message = new Message();
            message.errorMessage("Not Found","This id is not found on database");
        }
        return list;
    }
}
