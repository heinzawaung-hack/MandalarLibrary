package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Database.DatabaseDAO;
import sample.Database.DatabaseModel;
import sample.IssueSubmission.IssueDAO;
import sample.IssueSubmission.IssueModel;
import sample.Members.MemberDAO;
import sample.Members.MemberModel;
import sample.Settings.SettingsModel;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button addMemberButton;
    public Button addBookButton;
    public Button viewMemberButton;
    public Button viewBookButton;
    public Button settingButton;
    public JFXTextField bookIdField;
    public JFXButton bookIdFieldCheck;
    public Label bookNameLabel;
    public Label authorLabel;
    public JFXTextField memberIdField;
    public JFXButton idCheckButton;
    public Label memberNameLabel;
    public Label memberAddressLabel;
    public JFXButton issueButton;
    public JFXListView<String> listView;
    public JFXButton renewButton;
    public JFXButton submissionButton;
    public JFXTextField renewBookIDField;
    public ImageView logOutImage;
    public JFXButton logOutButton;
    public MenuItem versionMenuItem;
    public MenuItem exitItem;
    public MenuItem addBookMenuItem1;
    public MenuItem addMemberItem1;
    public MenuItem viewBookItem;
    public MenuItem viewMemberItem;
    Boolean isReadyForSubmit = false;
    Message message;
    DatabaseDAO databaseDAO;
    MemberDAO memberDAO;
    IssueDAO issueDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message = new Message();
        databaseDAO = new DatabaseDAO();
        memberDAO = new MemberDAO();
        issueDAO = new IssueDAO();
        imageInit();
    }

    public void imageInit(){
        Image image = new Image(getClass().getResourceAsStream("/sample/Images/logOut.png"));
        logOutImage.setImage(image);
    }

    public void addMemberAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Members/AddMember.fxml"));
        Scene scene = new Scene(root,485,380);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Member");
        stage.setResizable(false);
        stage.show();
    }

    public void addBookAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/SaveBook.fxml"));
        Scene scene = new Scene(root,420,493);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Book");
        stage.setResizable(false);
        stage.show();
    }

    public void viewMemberAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Members/MemberList.fxml"));
        Scene scene = new Scene(root,786,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("MemberList");
        stage.setResizable(true);
        stage.show();
    }

    public void viewBookAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/BookList.fxml"));
        Scene scene = new Scene(root,700,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Book");
        stage.setResizable(true);
        stage.show();
    }

    public void settingAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Settings/Settings.fxml"));
        Scene scene = new Scene(root,550,348);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Settings");
        stage.setResizable(false);
        stage.show();
    }


    public void issueAction(ActionEvent event) {
        String bookId = bookIdField.getText();
        String memberId = memberIdField.getText();
        if (bookId.isEmpty() || memberId.isEmpty()){
            message.errorMessage("Error","Please enter ID to issue book");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Issue");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure this book " + bookNameLabel.getText() + " to issue to " + memberNameLabel.getText() + " ?");
        Optional<ButtonType> optional = alert.showAndWait();
        int bookID = Integer.parseInt(bookId);
        int memberID = Integer.parseInt(memberId);
        IssueModel issueModel = new IssueModel(bookID,memberID);
        if (optional.get() == ButtonType.OK){
            try {
                issueDAO.issueBook(issueModel);
                issueDAO.changeAvail(issueModel);
                message.infoMessage("Success","This book successfully to " + memberNameLabel.getText());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void bookIdCheck(ActionEvent event) {
        String bookId = bookIdField.getText();
        try {
            if (bookId.isEmpty()){
                message.errorMessage("EmptyField","Please enter a number to check");
            }else {
                int id = Integer.parseInt(bookId);
                List<DatabaseModel> list = databaseDAO.checkWithId(id);
                for (DatabaseModel app : list){
                    bookNameLabel.setText(app.getTitle());
                    authorLabel.setText(app.getAuthor());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void memberIdCheckAction(ActionEvent event) {
        String memberId = memberIdField.getText();
        try {
            if (memberId.isEmpty()){
                message.errorMessage("Empty Field","Please enter id to check");
            }else {
                int id = Integer.parseInt(memberId);
                List<MemberModel> list = memberDAO.checkMemberWithId(id);
                for (MemberModel data : list){
                    memberNameLabel.setText(data.getName());
                    memberAddressLabel.setText(data.getAddress());
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //This method is, When press enter from renew/submission ,get all data from database including 3 Tables
    // and insert to List View

    public void LoadRenewInfo(ActionEvent event) {
        ObservableList<String> issueData = FXCollections.observableArrayList();

        String bookId = renewBookIDField.getText();
        isReadyForSubmit = false;
        if (bookId.isEmpty()){
            message.errorMessage("Empty Field","Please enter an Id");
            return;
        }
        int id = Integer.parseInt(bookId);
        try {
            List<IssueModel> list = issueDAO.getDataWithId(id);
            for (IssueModel data : list){
                int bookIdOne = data.getBookId();
                int memberId = data.getMemberId();
                Timestamp timestamp = data.getTimestamp();
                int renew = data.getRenew();

                issueData.add("Issue Date & Time " + timestamp.toGMTString());
                issueData.add("Renew : " + renew);

                List<DatabaseModel> databaseModelList = databaseDAO.getAllDataWithID(id);
                for (DatabaseModel databaseModel : databaseModelList){
                    int bookIdTwo = databaseModel.getId();
                    String bookName = databaseModel.getTitle();
                    String author = databaseModel.getAuthor();
                    String publisher = databaseModel.getPublisher();
                    String isAvail = databaseModel.getIsAvail();

                    issueData.add("Book ID : " + bookIdTwo);
                    issueData.add("Book Name : " + bookName);
                    issueData.add("Book Author : " + author);
                    issueData.add("Book Publisher : " + publisher);
                    issueData.add("Is Available : " + isAvail);
                }

                List<MemberModel> memberModelList = memberDAO.getAllDataWithId(memberId);
                for (MemberModel memberModel : memberModelList){
                    int memberIdOne = memberModel.getId();
                    String name = memberModel.getName();
                    String mobile = memberModel.getMobile();
                    String address = memberModel.getAddress();

                    issueData.add("Member ID : " + memberIdOne);
                    issueData.add("Member Name : " + name);
                    issueData.add("Mobile Number : " + mobile);
                    issueData.add("Address : " + address);
                }
            }
            isReadyForSubmit = true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        listView.getItems().setAll(issueData);
    }

    public void submissionAction(ActionEvent event) {
        if (!isReadyForSubmit){
            message.errorMessage("404","ID is not entered, Please enter");
            return;
        }
        String bookId = renewBookIDField.getText();
        if (bookId.isEmpty()){
            message.errorMessage("Empty","Please enter book ID to Submit");
            return;
        }
        int id = Integer.parseInt(bookId);
        IssueModel issueModel = new IssueModel(id);

        if (listView.getItems().isEmpty()){
            message.errorMessage("Error","This book is not issue yet to user");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Submission");
            alert.setContentText("Are you sure this book to submission?");
            Optional<ButtonType> type = alert.showAndWait();
            if (type.get() == ButtonType.OK){
                try {
                    issueDAO.deleteDataWithId(id);
                    issueDAO.trueAvail(issueModel);
                    message.infoMessage("Success","Submission Book success");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void renewAction(ActionEvent event) {
        if (!isReadyForSubmit){
            message.errorMessage("404","Please enter Book Id to renew");
            return;
        }
        String bookId = renewBookIDField.getText();
        if (bookId.isEmpty()){
            message.errorMessage("Error","Please enter BOOK ID to renew.");
            return;
        }
        int id = Integer.parseInt(bookId);
        IssueModel issueModel = new IssueModel(id);

        if (listView.getItems().isEmpty()){
            message.errorMessage("Error","You can't renew, This book ID that you entered is not issue to user");
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Renew");
            alert.setContentText("Are you sure you want to renew?");
            alert.setHeaderText(null);
            Optional<ButtonType> optional = alert.showAndWait();
            if (optional.get() == ButtonType.OK){
                try {
                    issueDAO.renewBook(issueModel);
                    message.infoMessage("Success","Congrats, This book renew successfully");
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void logOutAction(ActionEvent event) throws IOException {
        settingButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/LogIn/LogIn.fxml"));
        Scene scene = new Scene(root,567,386);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.setResizable(false);
        stage.show();
    }

    public void versionAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/VersionInfo/Version.fxml"));
        Scene scene = new Scene(root,497,309);
        Stage stage = new Stage();
        stage.setTitle("Version");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void exitAction(ActionEvent event) {
        Stage stage = (Stage) addBookButton.getScene().getWindow();
        stage.close();
    }
}
