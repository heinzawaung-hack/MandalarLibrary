package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.Database.Database;
import sample.Database.DatabaseDAO;
import sample.Database.DatabaseModel;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SaveBookController implements Initializable {
    public JFXTextField bookTitle;
    public JFXTextField bookId;
    public JFXTextField publisher;
    public JFXTextField author;
    public JFXButton saveButton;
    public JFXButton cancelButton;
    public ToggleGroup isAvail;
    public JFXRadioButton trueRadio;
    public JFXRadioButton falseRadio;
    Message message;
    DatabaseDAO databaseDAO;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Database database = Database.getInstance();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        //Tools Package Message
        message = new Message();
        //Database DAO
        databaseDAO = new DatabaseDAO();
        //Radio button User Data
        trueRadio.setUserData("True");
        falseRadio.setUserData("False");
    }

    public void saveAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String title = bookTitle.getText();
        String id = bookId.getText();
        String authorText = author.getText();
        String publisherField = publisher.getText();
        if (title.isEmpty() || id.isEmpty() || authorText.isEmpty() || publisherField.isEmpty()){
            message.errorMessage("Error","Please fill all fields to save data");
            return;
        }
        int bookId = Integer.parseInt(id);
        String radioData = (String) isAvail.getSelectedToggle().getUserData();
        DatabaseModel databaseModel = new DatabaseModel(bookId,title,authorText,publisherField,radioData);
        databaseDAO.insertData(databaseModel);
        clearFields();
        message.infoMessage("Success","This book saved to database");
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void clearFields(){
        bookId.clear();
        author.clear();
        bookTitle.clear();
        publisher.clear();
    }
}
