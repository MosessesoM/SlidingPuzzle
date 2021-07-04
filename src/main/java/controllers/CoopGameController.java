package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CoopGameController extends Controller {

    @FXML
    public AnchorPane statisticsAnchorPane;

    @FXML
    public AnchorPane gamefieldAnchorPane;

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
        confirmationController.setGame_mode("coop");
        confirmationController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }
}
