package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GameSettingsController extends Controller {

    private String game_mode;

    @FXML
    public Button cancelButton;

    @FXML
    public Button acceptButton;

    @FXML
    public Label rowsLabel;

    @FXML
    public Label columnsLabel;

    @FXML
    public Label dificultylevelLabel;

    @FXML
    public MenuButton levelMenuButton;

    @FXML
    public MenuItem level1MenuItem;

    @FXML
    public MenuItem level2MenuItem;

    @FXML
    public MenuItem level3MenuItem;

    @FXML
    public TextField rowsTextField;

    @FXML
    public TextField columnsTextField;

    @FXML
    void initialize(){

    }

    @FXML
    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    @FXML
    public void acceptButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        switch (game_mode) {
            case "solo":
                loader.setLocation(this.getClass().getResource("/views/sologame.fxml"));
                AnchorPane anchorPanesolo = loader.load();
                SoloGameController soloGameController = loader.getController();
                soloGameController.setMainController(mainController);
                mainController.setScreen(anchorPanesolo);
                break;
            case "vs":
                loader.setLocation(this.getClass().getResource("/views/vsgame.fxml"));
                AnchorPane anchorPanevs = loader.load();
                VsGameController vsGameController = loader.getController();
                vsGameController.setMainController(mainController);
                mainController.setScreen(anchorPanevs);
                break;
            case "coop":
                loader.setLocation(this.getClass().getResource("/views/coopgame.fxml"));
                AnchorPane anchorPanecoop = loader.load();
                CoopGameController coopGameController = loader.getController();
                coopGameController.setMainController(mainController);
                mainController.setScreen(anchorPanecoop);
                break;
            default:
                break;
        }
    }

    @FXML
    public void levelManuButtonOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void level1MenuItemOnAction(ActionEvent actionEvent) {
        levelMenuButton.setText(level1MenuItem.getText());
    }

    @FXML
    public void level2MenuItemOnAction(ActionEvent actionEvent) {
        levelMenuButton.setText(level2MenuItem.getText());
    }

    @FXML
    public void level3MenuItemOnAction(ActionEvent actionEvent) {
        levelMenuButton.setText(level3MenuItem.getText());
    }

    @FXML
    public void rowsTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void columnsTextFieldOnAction(ActionEvent actionEvent) {
    }

    public void setGame_mode(String game_mode){
        this.game_mode=game_mode;
    }
}
