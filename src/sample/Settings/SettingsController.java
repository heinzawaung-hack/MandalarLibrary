package sample.Settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public ImageView bookImageView;
    public JFXTextField withoutFees;
    public JFXTextField fees;
    public JFXTextField userName;
    public JFXPasswordField password;
    public JFXButton saveButton;
    public JFXButton cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/sample/Images/icons8.png"));
        bookImageView.setImage(image);
        try {
            setDefaultValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultValue() throws IOException {
        SettingsModel settingsModel = SettingsModel.getInstance();
        withoutFees.setText(String.valueOf(settingsModel.getWithoutFee()));
        fees.setText(String.valueOf(settingsModel.getFees()));
        userName.setText(settingsModel.getUserName());
        password.setText(settingsModel.getPassword());
    }

    public void saveAction(ActionEvent event) throws IOException {
        String withoutFee = withoutFees.getText();
        String feesOne = fees.getText();
        String userNameOne = userName.getText();
        String passwordOne = password.getText();
        int without = Integer.parseInt(withoutFee);
        float fees = Float.parseFloat(feesOne);

        SettingsModel settingsModel = SettingsModel.getInstance();
        settingsModel.setWithoutFee(without);
        settingsModel.setFees(fees);
        settingsModel.setUserName(userNameOne);
        settingsModel.setPassword(passwordOne);

        SettingsModel.writeModel(settingsModel);
        fieldsClear();
    }

    public void cancelAction(ActionEvent event) {
        Stage stage = (Stage) userName.getScene().getWindow();
        stage.close();
    }

    public void fieldsClear(){
        withoutFees.clear();
        fees.clear();
        userName.clear();
        password.clear();
    }
}
