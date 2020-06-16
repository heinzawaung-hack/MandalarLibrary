package sample.Database;

import java.sql.*;

public class Database {
    private static Database database;
    private Connection connection;
    private final String url = "jdbc:derby:database;create=true";

    private Database() throws SQLException {
        connect();
        createTable();
        createMemberTable();
        createIssueTable();
        createSignInTable();
    }

    public static Database getInstance() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        if (database == null){
            database = new Database();
        }
        return database;
    }

    public void connect(){
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Derby Database Connected.");
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public void createTable(){
        //create table and check if exists , this is derby database
        String tableName = "BOOK";
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet table = databaseMetaData.getTables(null,null,tableName.toUpperCase(),null);
            if (table.next()){
                System.out.println("Table is already created");
            }else {
                String sql = "CREATE TABLE BOOK(id int not null primary key,title varchar(100),author varchar(50),publisher varchar(40),isAvail varchar(10))";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                System.out.println("Table created success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createMemberTable(){
        String name = "MEMBER";
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet table = databaseMetaData.getTables(null,null,name.toUpperCase(),null);
            if (table.next()){
                System.out.println("Member table is already created");
            }else {
                String createTable = "CREATE TABLE MEMBER(id int not null primary key,name varchar(30),mobile varchar(20),address varchar(30))";
                Statement statement = connection.createStatement();
                statement.execute(createTable);
                System.out.println("Member table is created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createIssueTable(){
        String name = "ISSUE";
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null,null,name.toUpperCase(),null);
            if (resultSet.next()){
                System.out.println("Issue table is already created");
            }else {
                String createTable = "CREATE TABLE ISSUE (bookId int not null,memberId int not null,issueTime timestamp default CURRENT_TIMESTAMP,renew int default 0,FOREIGN KEY (bookId) REFERENCES BOOK (id),FOREIGN KEY (memberId) REFERENCES MEMBER (id))";
                Statement statement = connection.createStatement();
                statement.execute(createTable);
                System.out.println("Issue table created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSignInTable(){
        String name = "USERDATA";
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null,null,name.toUpperCase(),null);
            if (resultSet.next()){
                System.out.println("USERDATA table is already Created");
            }else {
                String createTable = "CREATE TABLE USERDATA (id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),email VARCHAR(30),password VARCHAR(70),address VARCHAR(30),pinNumber VARCHAR(10),gender VARCHAR(10))";
                Statement statement = connection.createStatement();
                statement.execute(createTable);
                System.out.println("USERDATA Table created successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropDatabase() throws SQLException {
        String drop = "DROP TABLE USERDATA";
        Statement statement = connection.createStatement();
        statement.execute(drop);
        System.out.println("USERDATA table deleted success");
    }
}




