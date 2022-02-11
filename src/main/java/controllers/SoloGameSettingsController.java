package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SoloGameSettingsController extends Controller {

    @FXML
    public Button cancelButton;

    @FXML
    public Button acceptButton;

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
    public Label warningLabel;

    @FXML
    void initialize() {

    }

    @FXML
    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    @FXML
    public void acceptButtonOnAction(ActionEvent actionEvent) throws IOException {
        warningLabel.setText("");

        FXMLLoader loader = new FXMLLoader();

        int moves = 0;

        switch (levelMenuButton.getText()) {
            case "Level 1":
                moves = 10;
                break;
            case "Level 2":
                moves = 20;
                break;
            case "Level 3":
                moves = 30;
                break;
            default:
                break;
        }
        if (moves != 0) {
            loader.setLocation(this.getClass().getResource("/views/sologame.fxml"));
            AnchorPane anchorpaneSolo = loader.load();
            SoloGameController soloGameController = loader.getController();
            soloGameController.setMainController(mainController);
            soloGameController.randomStart(moves);
            mainController.setScreen(anchorpaneSolo);
        } else {
            warningLabel.setText("Choose difficulty level.");
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

}
