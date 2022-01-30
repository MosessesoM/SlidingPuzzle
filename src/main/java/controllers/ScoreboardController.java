package controllers;

import command.GoToMenuCommand;
import database.DatabaseGetters;
import database.SoloScore;
import database.User;
import database.VsScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.bytebuddy.implementation.Implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardController extends Controller {

    @FXML
    public TableView scoreboardTableView;

    @FXML
    public TableColumn nameTableColumn;

    @FXML
    public TableColumn gamemodeTableColumn;

    @FXML
    public TableColumn scoreTableColumn;

    @FXML
    public Button exitButton;

    private DatabaseGetters databaseGetters = new DatabaseGetters();

    @FXML
    void initialize(){
        databaseGetters = new DatabaseGetters();
        List<SoloScore> soloScores=databaseGetters.getSoloScores();
        List<VsScore> vsScores=databaseGetters.getVsScores();

        ObservableList<Scoreboard> scoreboard = FXCollections.observableArrayList();

        for (SoloScore soloScore:soloScores){
            scoreboard.add(new Scoreboard(soloScore.getUser().getName(),"Solo",soloScore.getScore()));
        }
        for (VsScore vsScore:vsScores){
            scoreboard.add(new Scoreboard(vsScore.getUser().getName(),"VS",vsScore.getScore()));
        }

        scoreboardTableView.setItems(scoreboard);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard,String>("UserName"));
        gamemodeTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard,String>("GameMode"));
        scoreTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard,Integer>("Score"));
        scoreboardTableView.getColumns().setAll(nameTableColumn,gamemodeTableColumn,scoreTableColumn);
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    public static class Scoreboard{
        private String UserName;

        private String GameMode;

        private Integer Score;

        public Scoreboard(String userName, String gameMode, Integer score) {
            UserName = userName;
            GameMode = gameMode;
            Score = score;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getGameMode() {
            return GameMode;
        }

        public void setGameMode(String gameMode) {
            GameMode = gameMode;
        }

        public Integer getScore() {
            return Score;
        }

        public void setScore(Integer score) {
            Score = score;
        }
    }
}
