package sample.Members;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import sample.Tools.Message;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    public JFXTextField idField;
    public JFXTextField nameField;
    public JFXTextField mobileField;
    public JFXTextField addressField;
    public JFXButton saveButton;
    public JFXButton cancelButton;
    Message message;
    MemberDAO memberDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message = new Message();
        memberDAO = new MemberDAO();
    }

    public void saveAction(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();
        if (id.isEmpty() || name.isEmpty() || mobile.isEmpty() || address.isEmpty()){
            message.errorMessage("404 Not Found","Please fill all field to save");
            return;
        }
        int idData = Integer.parseInt(id);
        MemberModel memberModel = new MemberModel(idData,name,mobile,address);
        try {
            memberDAO.insertData(memberModel);
            message.infoMessage("Success","Saved successfully");
            nameField.clear();
            mobileField.clear();
            addressField.clear();
            idField.clear();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) mobileField.getScene().getWindow();
        stage.close();
    }
}
