package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.DatabaseDAO;
import sample.Database.DatabaseModel;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookList implements Initializable {
    public AnchorPane anchorPane;
    public TableView<DatabaseModel> tableView;
    public TableColumn<DatabaseModel, Integer> idColumn;
    public TableColumn<DatabaseModel, String> titleColumn;
    public TableColumn<DatabaseModel , String> authorColumn;
    public TableColumn<DatabaseModel, String> publisherColumn;
    public TableColumn<DatabaseModel , String> availColumn;
    public MenuItem copyItem;
    public MenuItem pasteItem;
    public MenuItem editItem;
    public MenuItem deleteItem;
    DatabaseDAO databaseDAO;
    Message message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseDAO = new DatabaseDAO();
        message = new Message();
        initColumn();
        loadTable();
    }

    private void initColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availColumn.setCellValueFactory(new PropertyValueFactory<>("isAvail"));
    }

    private void loadTable(){
        try {
             List<DatabaseModel> list = databaseDAO.getAllData();
             tableView.getItems().setAll(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void copyAction(ActionEvent event) {
    }

    public void pasteAction(ActionEvent event) {
    }

    public void editAction(ActionEvent event) throws IOException {
        DatabaseModel databaseModel = tableView.getSelectionModel().getSelectedItem();
        if (databaseModel == null){
            message.errorMessage("Error","Please select one to edit");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/BookEdit.fxml"));
        Parent root = loader.load();
        BookEditController controller = loader.getController();
        controller.setSelectedData(databaseModel);
        Scene scene = new Scene(root,631,187);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
        loadTable();
    }

    public void deleteAction(ActionEvent event) {
        DatabaseModel selectedModel = tableView.getSelectionModel().getSelectedItem();
        if (selectedModel == null){
            message.errorMessage("Error","Please select one row to delete");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are You Sure You Want To Delete?");
        alert.setHeaderText(null);
        alert.setTitle("Delete");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK){
            try {
                databaseDAO.deleteWithID(selectedModel.getId());
                tableView.getItems().remove(selectedModel);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
