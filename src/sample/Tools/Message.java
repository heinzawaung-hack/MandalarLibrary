package sample.Tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Message {

    public void errorMessage(String title,String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.show();
    }

    public void infoMessage(String title,String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.show();
    }
}
