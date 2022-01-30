package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class VsGameSettingsController extends Controller {

    @FXML
    public MenuItem player2MenuItem1;
    @FXML
    public MenuItem player2MenuItem2;
    @FXML
    public Label warningLabel;
    @FXML
    public MenuButton player1MenuButton;
    @FXML
    public MenuButton player2MenuButton;
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
    public MenuItem player1MenuItem1;
    @FXML
    public MenuItem player1MenuItem2;
    @FXML
    public MenuItem player1MenuItem3;

    @FXML
    void initialize(){

    }

    @FXML
    public void cancelButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    @FXML
    public void acceptButtonOnAction(ActionEvent actionEvent) throws IOException {
        warningLabel.setText("Wait a second. I'm thinking.");
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
            if (moves!=0 && !player2MenuButton.getText().isEmpty() && !player1MenuButton.getText().isEmpty()){
                loader.setLocation(this.getClass().getResource("/views/vsgame.fxml"));
                AnchorPane anchorPanevs = loader.load();
                VsGameController vsGameController = loader.getController();
                vsGameController.setMainController(mainController);
                vsGameController.randomStart(moves);
                if (!player1MenuButton.getText().equals("Player")) {
                    vsGameController.aiMoves(player1MenuButton.getText(), 1);
                }
                vsGameController.aiMoves(player2MenuButton.getText(), 2);
                mainController.setScreen(anchorPanevs);
            } else {
                warningLabel.setText("Fill all field.");
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

    public void player1MenuButtonOnAction(ActionEvent actionEvent) {
    }

    public void player1MenuItemOnAction(ActionEvent actionEvent) {
        player1MenuButton.setText(player1MenuItem1.getText());
    }

    public void player1MenuItem2OnAction(ActionEvent actionEvent) {
        player1MenuButton.setText(player1MenuItem2.getText());
    }

    public void player1MenuItem3OnAction(ActionEvent actionEvent) {
        player1MenuButton.setText(player1MenuItem3.getText());
    }

    public void player2MenuButtonOnAction(ActionEvent actionEvent) {
    }

    public void player2MenuItem1OnAction(ActionEvent actionEvent) {
        player2MenuButton.setText(player2MenuItem1.getText());
    }

    public void player2MenuItem2OnAction(ActionEvent actionEvent) {
        player2MenuButton.setText(player2MenuItem2.getText());
    }
}
