package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.Database;

import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Database database = Database.getInstance();
        Connection connection = database.getConnection();

        Parent root = FXMLLoader.load(getClass().getResource("/sample/LogIn/LogIn.fxml"));
        Scene scene = new Scene(root,567,386);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
