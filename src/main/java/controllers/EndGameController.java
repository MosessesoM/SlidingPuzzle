package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EndGameController extends Controller {

    private String game_mode;

    public Button exitButton;
    public Button playagainButton;

    public void setGame_mode(String game_mode){
        this.game_mode=game_mode;
    }

    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    public void playagainButtonOnAction(ActionEvent actionEvent) throws IOException {
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
}
