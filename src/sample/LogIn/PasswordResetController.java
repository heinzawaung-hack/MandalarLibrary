package sample.LogIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import sample.Tools.Message;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasswordResetController implements Initializable {
    public JFXButton saveButton;
    public JFXButton cancelButton;
    public JFXPasswordField newPasswordField;
    public JFXPasswordField againNewPassword;
    LogInDAO logInDAO;
    Message message;
    private String userEmail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInDAO = new LogInDAO();
        message = new Message();
    }

    public void getEmail(String email){
         userEmail = email;
    }

    public void saveAction(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String againNewPasswordUser = againNewPassword.getText();
        if (!newPassword.equals(againNewPasswordUser)){
            message.errorMessage("Error","Two password are not match, Please enter again");
            return;
        }
        //This will change to SHA1
        String shaPassword = DigestUtils.sha1Hex(againNewPasswordUser);
        try {
            logInDAO.resetPassword(userEmail,shaPassword);
            message.infoMessage("Success","Your password has been changed, Now,you can log In");
            newPasswordField.clear();
            againNewPassword.clear();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
