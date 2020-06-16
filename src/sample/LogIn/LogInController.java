package sample.LogIn;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.codec.digest.DigestUtils;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public ImageView bookImage;
    public ImageView userNameImg;
    public ImageView passwordImg;
    public ImageView progressGif;
    public JFXTextField userNameField;
    public JFXPasswordField passwordField;
    public JFXButton signInButton;
    public JFXButton signUpButton;
    public JFXCheckBox rememberMeBox;
    public JFXButton passwordResetButton;
    Message message;
    LogInDAO logInDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        message = new Message();
        logInDAO = new LogInDAO();
        imageInit();
    }

    private void imageInit() {
        Image image = new Image(getClass().getResourceAsStream("/sample/Images/book1.png"));
        bookImage.setImage(image);
        Image userName = new Image(getClass().getResourceAsStream("/sample/Images/userName1.png"));
        userNameImg.setImage(userName);
        Image password = new Image(getClass().getResourceAsStream("/sample/Images/password1.png"));
        passwordImg.setImage(password);
        Image progress = new Image(getClass().getResourceAsStream("/sample/Images/746.gif"));
        progressGif.setImage(progress);
        progressGif.setVisible(false);
    }

    public void signInAction(ActionEvent event) {
        progressGif.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(2));
        pauseTransition.setOnFinished(event1 -> {
            String email = userNameField.getText();
            String password = passwordField.getText();
            if (email.isEmpty() || password.isEmpty()){
                message.errorMessage("404","Please Fill User Name And Password");
                progressGif.setVisible(false);
                return;
            }
            //This will change this password to SHA1 on Server
            String sha1Password = DigestUtils.sha1Hex(password);
            LogInModel logInModel = new LogInModel(email,sha1Password);
            try {
                if (logInDAO.isUserExists(logInModel)){
                    signUpButton.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
                    Scene scene = new Scene(root,800,451);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Home");
                    stage.show();
                }else {
                    Message message = new Message();
                    message.errorMessage("Invalid User","There is no user in database that you entered or Your password is incorrect, Please Enter Again");
                    progressGif.setVisible(false);
                }
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });
        pauseTransition.play();
    }

    public void signUpAction(ActionEvent event) throws IOException {
        signInButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/LogIn/SignUp.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root,554,429);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.setResizable(false);
        stage.show();
    }

    public void rememberMeAction(ActionEvent event) {
        
    }


    public void passwordResetAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/LogIn/ForgotPassword.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root,500,342);
        stage.setScene(scene);
        stage.setTitle("Password Reset");
        stage.setResizable(false);
        stage.show();
    }
}
