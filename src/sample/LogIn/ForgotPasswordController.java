package sample.LogIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    public JFXButton resetButton;
    public JFXButton cancelButton;
    public JFXTextField emailField;
    public JFXPasswordField pinNumberField;
    Message message;
    LogInDAO logInDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message = new Message();
        logInDAO = new LogInDAO();
    }

    public void resetAction(ActionEvent event) {
        String userEmail = emailField.getText();
        String userPinNumber = pinNumberField.getText();
        if (userEmail.isEmpty() || userPinNumber.isEmpty()){
            message.errorMessage("Empty","Please all fields to reset password");
            return;
        }
        try {
            if (logInDAO.toReset(userEmail,userPinNumber)){
                cancelButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/LogIn/PasswordReset.fxml"));
                Parent root = loader.load();
                PasswordResetController passwordResetController = loader.getController();
                passwordResetController.getEmail(userEmail);
                Stage stage = new Stage();
                Scene scene = new Scene(root,534,308);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle("Reset");
                stage.show();
                emailField.clear();
                pinNumberField.clear();
            }else {
                message.errorMessage("Error","Email and Pin Number are not match, Please enter again");
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) resetButton.getScene().getWindow();
        stage.close();
    }
}
