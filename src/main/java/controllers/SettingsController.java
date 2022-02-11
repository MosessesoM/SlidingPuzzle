package controllers;

import command.GoToMenuCommand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingsController extends Controller {

    @FXML
    public Button backButton;

    @FXML
    public Button logoutButton;

    @FXML
    public Button exitButton;

    @FXML
    void initialize() {

    }

    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    @FXML
    public void logoutButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/login.fxml"));
        AnchorPane anchorPane = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
