package sample.Members;

import sample.Database.Database;
import sample.Database.DatabaseModel;
import sample.Tools.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public void insertData(MemberModel memberModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int id = memberModel.getId();
        String name = memberModel.getName();
        String mobile = memberModel.getMobile();
        String address = memberModel.getAddress();
        String insertData = "insert into MEMBER(id,name,mobile,address) values (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertData);
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2,name);
        preparedStatement.setString(3,mobile);
        preparedStatement.setString(4,address);
        preparedStatement.executeUpdate();
        System.out.println("Insert Data Successfully");
    }

    public List<MemberModel> getAllData() throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String retrieveData = "select * from MEMBER";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(retrieveData);
        List<MemberModel> list = new ArrayList<>();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String mobile = resultSet.getString("mobile");
            String address = resultSet.getString("address");
            MemberModel memberModel = new MemberModel(id,name,mobile,address);
            list.add(memberModel);
        }
        return list;
    }

    public void deleteData(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String deleteData = "delete from MEMBER where id=" + id + "";
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteData);
        System.out.println("Delete data success, You deleted id " + id);
    }

    public List<MemberModel> checkMemberWithId(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String checkId = "select name,address from MEMBER where id=" + id + "";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(checkId);
        List<MemberModel> list = new ArrayList<>();
        if (resultSet.next()){
            String memberName = resultSet.getString("name");
            String address = resultSet.getString("address");
            MemberModel memberModel = new MemberModel(memberName,address);
            list.add(memberModel);
        }else {
            Message message = new Message();
            message.errorMessage("Not Found","This id is not found on database");
        }
        return list;
    }

    public void editData(MemberModel memberModel) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        int id = memberModel.getId();
        String name = memberModel.getName();
        String mobile = memberModel.getMobile();
        String address = memberModel.getAddress();
        String editData = "update MEMBER set name=?,mobile=?,address=? where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(editData);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,mobile);
        preparedStatement.setString(3,address);
        preparedStatement.setInt(4,id);
        preparedStatement.executeUpdate();
        System.out.println("Update Data Success");
    }

    public List<MemberModel> getAllDataWithId(int id) throws SQLException, ClassNotFoundException {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        String retrieveData = "select * from MEMBER where id=" + id + "";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(retrieveData);
        List<MemberModel> list = new ArrayList<>();
        if (resultSet.next()){
            int memberId = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String mobile = resultSet.getString("mobile");
            String address = resultSet.getString("address");
            MemberModel memberModel = new MemberModel(memberId,name,mobile,address);
            list.add(memberModel);
        }else {
            Message message = new Message();
            message.errorMessage("404","There is no Id on Database");
        }
        return list;
    }
}