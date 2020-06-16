package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.Database.DatabaseDAO;
import sample.Database.DatabaseModel;
import sample.Tools.Message;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookEditController implements Initializable {


    public JFXTextField bookNameField;
    public JFXTextField authorField;
    public JFXTextField publisherField;
    public JFXRadioButton trueRadio;
    public JFXRadioButton falseRadio;
    public ToggleGroup isAvail;
    public JFXButton cancelButton;
    public JFXButton saveButton;
    private int bookId;
    Message message;
    DatabaseDAO databaseDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message = new Message();
        databaseDAO = new DatabaseDAO();
        trueRadio.setUserData("True");
        falseRadio.setUserData("False");
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) authorField.getScene().getWindow();
        stage.close();
    }
    
    public void saveAction(ActionEvent event) {
        String bookName = bookNameField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        if (bookName.isEmpty() || author.isEmpty() || publisher.isEmpty()){
            message.errorMessage("Empty Field","Please fill all field to update");
            return;
        }
        String toggleData = (String) isAvail.getSelectedToggle().getUserData();
        DatabaseModel databaseModel = new DatabaseModel(bookId,bookName,author,publisher,toggleData);
        try {
            databaseDAO.editData(databaseModel);
            Stage stage = (Stage) bookNameField.getScene().getWindow();
            stage.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedData(DatabaseModel databaseModel){
        bookId = databaseModel.getId();
        bookNameField.setText(databaseModel.getTitle());
        authorField.setText(databaseModel.getAuthor());
        publisherField.setText(databaseModel.getPublisher());
        if (databaseModel.getIsAvail().equals("True")){
            trueRadio.setSelected(true);
        }else {
            falseRadio.setSelected(true);
        }
    }
}
