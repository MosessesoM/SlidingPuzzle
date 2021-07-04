package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class ScoreboardController extends Controller {

    @FXML
    public TableView scoreboardTableView;

    @FXML
    public TableColumn nameTableColumn;

    @FXML
    public TableColumn scoreTableColumn;

    @FXML
    public Button exitButton;

    @FXML
    void initialize(){

    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }
}
