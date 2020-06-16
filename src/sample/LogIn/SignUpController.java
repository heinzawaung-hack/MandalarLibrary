package sample.LogIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public JFXTextField userNameField;
    public JFXButton signInButton;
    public JFXPasswordField passwordField;
    public JFXPasswordField pinNumberField;
    public JFXTextField addressField;
    public JFXRadioButton femaleRadio;
    public JFXRadioButton maleRadio;
    public ToggleGroup genderToggle;
    public JFXButton signUpButton;
    Message message;
    LogInDAO logInDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message = new Message();
        logInDAO = new LogInDAO();
        maleRadio.setUserData("Male");
        femaleRadio.setUserData("Female");
    }

    public void signInAction(ActionEvent event) throws IOException {
        maleRadio.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/LogIn/LogIn.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root,567,386);
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.setResizable(false);
        stage.show();
    }

    public void signUpAction(ActionEvent event) {
        String email = userNameField.getText();
        String userPassword = passwordField.getText();
        String userPinNumber = pinNumberField.getText();
        String userAddress = addressField.getText();
        String gender = (String) genderToggle.getSelectedToggle().getUserData();
        if (email.isEmpty() || userPassword.isEmpty() || userPinNumber.isEmpty() || userAddress.isEmpty()){
            message.errorMessage("Empty Field","Please fill all fields to Register");
            return;
        }
        String shaPassword = DigestUtils.sha1Hex(userPassword);
        LogInModel logInModel = new LogInModel(email,shaPassword,userAddress,userPinNumber,gender);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Sign Up");
        alert.setContentText("Are you sure you want to register?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK){
            try {
                logInDAO.insertData(logInModel);
                message.infoMessage("Success","Register successfully, Now you can Log In");
                clearFields();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        userNameField.clear();
        passwordField.clear();
        pinNumberField.clear();
        addressField.clear();
    }
}
