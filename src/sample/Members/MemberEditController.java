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

public class MemberEditController implements Initializable {

    public JFXTextField nameField;
    public JFXTextField mobileField;
    public JFXTextField addressField;
    public JFXButton cancelButton;
    public JFXButton saveButton;
    private int memberId;
    MemberDAO memberDAO;
    Message message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberDAO = new MemberDAO();
        message = new Message();
    }

    public void cancelAction(ActionEvent event) {
        Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.close();
    }

    public void saveAction(ActionEvent event) {
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();
        if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()){
            message.errorMessage("Empty Field","Please fill all fields to update");
            return;
        }
        MemberModel memberModel = new MemberModel(memberId,name,mobile,address);
        try {
            memberDAO.editData(memberModel);
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedData(MemberModel memberModel){
        memberId = memberModel.getId();
        nameField.setText(memberModel.getName());
        addressField.setText(memberModel.getAddress());
        mobileField.setText(memberModel.getMobile());
    }
}
