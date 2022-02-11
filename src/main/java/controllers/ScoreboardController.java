package controllers;

import command.GoToMenuCommand;
import database.DatabaseGetters;
import database.SoloScore;
import database.VsScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
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
    public TableColumn timeTableColumn;

    @FXML
    public TableColumn movesTableColumn;

    @FXML
    public TableColumn levelTableColumn;

    @FXML
    public Button exitButton;

    private DatabaseGetters databaseGetters = new DatabaseGetters();

    @FXML
    void initialize() {
        databaseGetters = new DatabaseGetters();
        List<SoloScore> soloScores = databaseGetters.getSoloScores();
        List<VsScore> vsScores = databaseGetters.getVsScores();

        ObservableList<Scoreboard> scoreboard = FXCollections.observableArrayList();

        for (SoloScore soloScore : soloScores) {
            scoreboard.add(new Scoreboard(soloScore.getUser().getName(), "Solo", soloScore.getScore(),soloScore.getTime(),soloScore.getMoves(),soloScore.getLevel()));
        }
        for (VsScore vsScore : vsScores) {
            scoreboard.add(new Scoreboard(vsScore.getUser().getName(), "VS", vsScore.getScore(),vsScore.getTime(),vsScore.getMoves(),vsScore.getLevel()));
        }

        scoreboardTableView.setItems(scoreboard);
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard, String>("userName"));
        gamemodeTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard, String>("gameMode"));
        scoreTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard, Integer>("score"));
        timeTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard, Integer>("time"));
        movesTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard, Integer>("moves"));
        levelTableColumn.setCellValueFactory(new PropertyValueFactory<Scoreboard,Integer>("level"));
        scoreboardTableView.getColumns().setAll(nameTableColumn, gamemodeTableColumn, scoreTableColumn,timeTableColumn,movesTableColumn,levelTableColumn);
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        new GoToMenuCommand(mainController).execute();
    }

    public static class Scoreboard {
        private String userName;

        private String gameMode;

        private Integer score;

        private Integer time;

        private Integer moves;

        private Integer level;

        public Scoreboard(String userName, String gameMode, Integer score, Integer time, Integer moves, Integer level) {
            this.userName = userName;
            this.gameMode = gameMode;
            this.score = score;
            this.time=time;
            this.moves=moves;
            this.level=level;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getGameMode() {
            return gameMode;
        }

        public void setGameMode(String gameMode) {
            this.gameMode = gameMode;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Integer getMoves() {
            return moves;
        }

        public void setMoves(Integer moves) {
            this.moves = moves;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }
    }
}
