package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoseController extends Controller {
    @FXML
    public Button exitButton;
    @FXML
    public Button playagainButton;
    @FXML
    public TextField scoreTextField;

    private GameController gameController;

    @FXML
    void initialize() {
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    public void playagainButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        switch (gameController.getClass().getName()) {
            case "controllers.SoloGameController":
                loader.setLocation(this.getClass().getResource("/views/sologame.fxml"));
                AnchorPane anchorPanesolo = loader.load();
                SoloGameController soloGameController = loader.getController();
                soloGameController.setMainController(mainController);
                mainController.setScreen(anchorPanesolo);
                break;
            case "controllers.VsGameController":
                loader.setLocation(this.getClass().getResource("/views/vsgame.fxml"));
                AnchorPane anchorPanevs = loader.load();
                VsGameController vsGameController = loader.getController();
                vsGameController.setMainController(mainController);
                mainController.setScreen(anchorPanevs);
                break;
            case "controllers.CoopGameController":
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

    public void setScoreTextField(Integer score) {
        scoreTextField.setText(String.valueOf(score));
    }
}
