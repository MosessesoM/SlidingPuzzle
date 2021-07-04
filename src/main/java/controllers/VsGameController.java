package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class VsGameController extends Controller {

    @FXML
    public AnchorPane sigamefieldAnchorPane;

    @FXML
    public AnchorPane playergamefieldAnchorPane;

    @FXML
    public Button exitButton;

    @FXML
    void initialize(){

    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/confirmation.fxml"));
        AnchorPane anchorPane = loader.load();
        ConfirmationController confirmationController = loader.getController();
        confirmationController.setGame_mode("vs");
        confirmationController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }
}
