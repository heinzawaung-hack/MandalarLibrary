package sample.Members;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Tools.Message;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberListController implements Initializable {
    public TableColumn<MemberModel , Integer> idColumn;
    public TableColumn<MemberModel , String> nameColumn;
    public TableColumn<MemberModel, String> mobileColumn;
    public TableColumn<MemberModel , String> addressColumn;
    public TableView<MemberModel> tableView;
    public MenuItem deleteItem;
    public MenuItem copyItem;
    public MenuItem pasteItem;
    public MenuItem edit;
    MemberDAO memberDAO;
    Message message;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberDAO = new MemberDAO();
        message = new Message();
        initColumn();
        loadTable();
    }

    private void loadTable() {
        try {
            List<MemberModel> list = memberDAO.getAllData();
            tableView.getItems().setAll(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initColumn() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void deleteAction(ActionEvent event) {
        MemberModel selectedModel = tableView.getSelectionModel().getSelectedItem();
        if (selectedModel == null) {
            message.errorMessage("Error", "Please select row to delete");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try {
                memberDAO.deleteData(selectedModel.getId());
                tableView.getItems().remove(selectedModel);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyAction(ActionEvent event) {
    }

    public void pasteAction(ActionEvent event) {
    }

    public void EditAction(ActionEvent event) throws IOException {
        //Load Selected Data to Edit FXML Window
        MemberModel selectedItemModel = tableView.getSelectionModel().getSelectedItem();
        if (selectedItemModel == null){
            message.errorMessage("Error","Please select one row to edit");
            return;
        }
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/sample/Members/MemberEdit.fxml"));
        Parent root = appLoader.load();
        MemberEditController controller = appLoader.getController();
        controller.setSelectedData(selectedItemModel);
        Scene scene = new Scene(root,600,227);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit");
        stage.setResizable(false);
        stage.showAndWait();
        loadTable();
    }
}
