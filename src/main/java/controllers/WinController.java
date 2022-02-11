package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WinController extends Controller {

    @FXML
    public TextField scoreTextField;

    @FXML
    public Label winnerNameLabel;

    private GameController gameController;

    private String player1;

    private String player2;

    private Integer moves;

    @FXML
    void initialize() {
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setScoreTextField() {
        scoreTextField.setText(String.valueOf(gameController.getScore()));
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
                soloGameController.randomStart(moves);
                mainController.setScreen(anchorPanesolo);
                break;
            case "controllers.VsGameController":
                loader.setLocation(this.getClass().getResource("/views/vsgame.fxml"));
                AnchorPane anchorPanevs = loader.load();
                VsGameController vsGameController = loader.getController();
                vsGameController.setMainController(mainController);
                vsGameController.randomStart(moves);
                vsGameController.aiMoves(player1.substring(0,7).stripTrailing(),player2.substring(0,7).stripTrailing());
                String number1 = "";
                String number2 = "";
                if (player1.substring(0,7).stripTrailing().equals(player2.substring(0,7).stripTrailing())) {
                    number1 = " 1";
                    number2 = " 2";
                }
                vsGameController.setBoard1Label(player1.substring(0,7).stripTrailing().concat(number1));
                vsGameController.setBoard2Label(player2.substring(0,7).stripTrailing().concat(number2));
                mainController.setScreen(anchorPanevs);
                break;
            default:
                break;
        }
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Integer getMoves() {
        return moves;
    }

    public void setMoves(Integer moves) {
        this.moves = moves;
    }

    public Label getWinnerNameLabel() {
        return winnerNameLabel;
    }

    public void setWinnerNameLabel(String winnerName) {
        this.winnerNameLabel.setText(winnerName+" Won!!!");
    }
}
