package sample.LogIn;

import sample.Database.Database;
import sample.Tools.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogInDAO {
    public void insertData(LogInModel logInModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String email = logInModel.getEmail();
        String password = logInModel.getPassword();
        String address = logInModel.getAddress();
        String gender = logInModel.getGender();
        String pinNumber = logInModel.getPinNumber();
        String insertData = "insert into USERDATA (email,password,address,pinNumber,gender) values (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertData);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,address);
        preparedStatement.setString(4,pinNumber);
        preparedStatement.setString(5,gender);
        preparedStatement.executeUpdate();
        System.out.println("User Sign Up Successfully");
    }

    public boolean isUserExists(LogInModel logInModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String email = logInModel.getEmail();
        String password = logInModel.getPassword();
        String isUserExists = "select * from USERDATA where email=? and password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(isUserExists);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        ResultSet resultSet =  preparedStatement.executeQuery();
        int count = 0;
        if (resultSet.next()){
            count = +1;
        }
        if (count == 1){
            System.out.println("LogIn Success");
            return true;
        }else{
            return false;
        }
    }

    public boolean toReset(String email,String pinNumber) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String isUserExists = "select * from USERDATA where email=? and pinNumber=?";
        PreparedStatement preparedStatement = connection.prepareStatement(isUserExists);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,pinNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0;
        if (resultSet.next()){
            count = +1;
        }
        if (count == 1){
            System.out.println("Continue to reset");
            return true;
        }else {
            return false;
        }
    }

    //update password
    public void resetPassword(String email,String password) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String updateData = "update USERDATA set password=? where email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateData);
        preparedStatement.setString(1,password);
        preparedStatement.setString(2,email);
        preparedStatement.executeUpdate();
        System.out.println("Password reset success");
    }

    //get All Data
    public List<LogInModel> getAllData() throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String getData = "select * from USERDATA";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getData);
        List<LogInModel> list = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String address = resultSet.getString("address");
            String pinNumber = resultSet.getString("pinNumber");
            String gender = resultSet.getString("gender");
            LogInModel logInModel = new LogInModel(id,email,password,address,pinNumber,gender);
            list.add(logInModel);
        }
        return list;
    }
}
