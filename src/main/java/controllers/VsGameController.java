package controllers;

import aialgorithms.AStar;
import aialgorithms.BoardState;
import aialgorithms.Genetic;
import database.DatabaseSetters;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class VsGameController extends Controller implements GameController {

    @FXML
    public AnchorPane sigamefieldAnchorPane;

    @FXML
    public AnchorPane playergamefieldAnchorPane;

    @FXML
    public Button exitButton;
    @FXML
    public GridPane gridPane1;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;
    @FXML
    public Button button4;
    @FXML
    public Button button5;
    @FXML
    public Button button6;
    @FXML
    public Button button7;
    @FXML
    public Button button8;
    @FXML
    public GridPane gridPane2;
    @FXML
    public Button button11;
    @FXML
    public Button button21;
    @FXML
    public Button button31;
    @FXML
    public Button button41;
    @FXML
    public Button button51;
    @FXML
    public Button button61;
    @FXML
    public Button button71;
    @FXML
    public Button button81;

    @FXML
    public Label board1Label;

    @FXML
    public Label board2Label;

    @FXML
    public TextField numberofmovesTextFIeld;

    @FXML
    public TextField gametimeTextField;

    @FXML
    public TextField buttonsinplaceTextField;

    private final int[][] end = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}};

    private AStar aStar;

    private Genetic genetic;

    private int[][] coordinates;

    private int numberofmoves;

    private int time;

    private int buttonsinplace;

    private int score;

    private Timeline timer1;

    @FXML
    void initialize() throws IOException {
        coordinates = new int[end.length][2];
        numberofmoves = 0;
        time = 0;
        buttonsinplace = 0;

        int index = 0;
        for (Node node : gridPane1.getChildren()) {
            coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
            coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
            index++;
        }
        numberofmovesTextFIeld.setText(String.valueOf(numberofmoves));
        gametimeTextField.setText(String.valueOf(time / 60) + ":" + time / 10 + time % 10);
        buttonsinplaceTextField.setText(String.valueOf(buttonsinplace));
        time++;

        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<>() {
                            @Override
                            public void handle(ActionEvent event) {
                                gametimeTextField.setText(String.valueOf(time / 60) + ":" + (time / 10) % 6 + time % 10);
                                time++;
                            }
                        }
                ));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public int getScore() {
        return score;
    }

    //    TODO: Pamiętać żeby dać zmienne wielkości zamiast sztywnych przy zmianie rozmiarów
    public void randomStart(int moves) {
        Random random = new Random();

        ArrayList<Button> buttons = new ArrayList<>() {
            {
                add(button1);
                add(button2);
                add(button3);
                add(button4);
                add(button5);
                add(button6);
                add(button7);
                add(button8);
            }
        };
        ArrayList<Button> copy = new ArrayList<>();
        copy.addAll(buttons);

        ArrayList<int[][]> coordArrayList = new ArrayList<>();

        while (moves > 0) {
            Button button = copy.remove(random.nextInt(copy.size()));
            int column = GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0;
            int row = GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0;
            boolean check1 = false;
            boolean check2 = false;
            boolean check3 = false;
            boolean check4 = false;
            for (int[] coordinate : coordinates) {
                if ((row + 1 == coordinate[0] && column == coordinate[1]) || row + 1 > 2) {
                    check1 = true;
                }
                if ((row - 1 == coordinate[0] && column == coordinate[1]) || row - 1 < 0) {
                    check2 = true;
                }
                if ((column + 1 == coordinate[1] && row == coordinate[0]) || column + 1 > 2) {
                    check3 = true;
                }
                if ((column - 1 == coordinate[1] && row == coordinate[0]) || column - 1 < 0) {
                    check4 = true;
                }
            }

            int[][] pom = new int[coordinates.length][coordinates[0].length];
            if (!check1) {
                GridPane.setRowIndex(button, row + 1);
                copy.clear();
                copy.addAll(buttons);
                copy.remove(button);
                moves--;
            } else if (!check2) {
                GridPane.setRowIndex(button, row - 1);
                copy.clear();
                copy.addAll(buttons);
                copy.remove(button);
                moves--;
            } else if (!check3) {
                GridPane.setColumnIndex(button, column + 1);
                copy.clear();
                copy.addAll(buttons);
                copy.remove(button);
                moves--;
            } else if (!check4) {
                GridPane.setColumnIndex(button, column - 1);
                copy.clear();
                copy.addAll(buttons);
                copy.remove(button);
                moves--;
            }

            int index = 0;
            for (Node node : gridPane1.getChildren()) {
                coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
                coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
                index++;
            }

            for (int i = 0; i < coordinates.length; i++) {
                for (int j = 0; j < coordinates[0].length; j++) {
                    pom[i][j] = coordinates[i][j];
                }
            }
            coordArrayList.add(pom);
        }

        int index = 0;
        for (Node node : gridPane2.getChildren()) {
            GridPane.setRowIndex(node,coordinates[index][0]);
            GridPane.setColumnIndex(node,coordinates[index][1]);
            index++;
        }

        aStar = new AStar(convert(buttons));
        genetic = new Genetic(convert(buttons));
    }

    @FXML
    public void buttonOnAction(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getTarget();
        int column = GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0;
        int row = GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0;

        int index = 0;
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;
        boolean check4 = false;
        for (int[] coordinate : coordinates) {
            if ((row + 1 == coordinate[0] && column == coordinate[1]) || row + 1 > 2) {
                check1 = true;
            }
            if ((row - 1 == coordinate[0] && column == coordinate[1]) || row - 1 < 0) {
                check2 = true;
            }
            if ((column + 1 == coordinate[1] && row == coordinate[0]) || column + 1 > 2) {
                check3 = true;
            }
            if ((column - 1 == coordinate[1] && row == coordinate[0]) || column - 1 < 0) {
                check4 = true;
            }
        }
        if (!check1) {
            GridPane.setRowIndex(button, row + 1);
            numberofmoves++;
        } else if (!check2) {
            GridPane.setRowIndex(button, row - 1);
            numberofmoves++;
        } else if (!check3) {
            GridPane.setColumnIndex(button, column + 1);
            numberofmoves++;
        } else if (!check4) {
            GridPane.setColumnIndex(button, column - 1);
            numberofmoves++;
        }
        for (Node node : gridPane1.getChildren()) {
            coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
            coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
            index++;
        }
        buttonsinplace = 0;
        for (int i = 0; i < coordinates.length; i++) {
            if (end[i][0] == coordinates[i][0] && end[i][1] == coordinates[i][1]) {
                buttonsinplace++;
            }
        }
        numberofmovesTextFIeld.setText(String.valueOf(numberofmoves));
        buttonsinplaceTextField.setText(String.valueOf(buttonsinplace));
        if (endCheck(gridPane1) ){
            score = 1000 - time - numberofmoves;
            DatabaseSetters databaseSetters = new DatabaseSetters();
            databaseSetters.setSoloScore(user, score);
            win();
        }
    }

    public void win() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/win.fxml"));
        AnchorPane anchorPane = loader.load();
        WinController winController = loader.getController();
        winController.setGameController(this);
        winController.setScoreTextField();
        winController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    public void lose() throws IOException {
        timer1.stop();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/lose.fxml"));
        AnchorPane anchorPane = loader.load();
        LoseController loseController = loader.getController();
        loseController.setGameController(this);
        loseController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    private int[][] convert(ArrayList<Button> buttons) {
        int[][] current = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                current[i][j] = -1;
            }
        }
        for (Button button : buttons) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    switch (button.getId()) {
                        case "button1":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 1;
                            }
                            break;
                        case "button2":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 2;
                            }
                            break;
                        case "button3":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 3;
                            }
                            break;
                        case "button4":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 4;
                            }
                            break;
                        case "button5":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 5;
                            }
                            break;
                        case "button6":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 6;
                            }
                            break;
                        case "button7":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 7;
                            }
                            break;
                        case "button8":
                            if ((GridPane.getRowIndex(button) != null ? GridPane.getRowIndex(button) : 0) == i && (GridPane.getColumnIndex(button) != null ? GridPane.getColumnIndex(button) : 0) == j) {
                                current[i][j] = 8;
                            }
                            break;
                    }
                }
            }
        }
        return current;
    }

    public void aiMoves(String aiAlgorithm,Integer player_number) throws IOException {
        ArrayList<BoardState> solution = null;
        if (aiAlgorithm.equals("A Star")){
             solution= aStar.findSolution();
        } else if (aiAlgorithm.equals("Genetic")){
            solution= genetic.findSolution();
        }

        ArrayList<BoardState> finalSolution = solution;
        timer1 = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            try {
                                setBoard(finalSolution.remove(finalSolution.size()-1).getState(),player_number==1 ? gridPane1 : gridPane2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                ));
        timer1.setCycleCount(Timeline.INDEFINITE);
        timer1.play();
        if (endCheck(player_number==1 ? gridPane1 : gridPane2)){
            DatabaseSetters databaseSetters = new DatabaseSetters();
            databaseSetters.setSoloScore(user, 0);
            lose();
        }
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/confirmation.fxml"));
        AnchorPane anchorPane = loader.load();
        ConfirmationController confirmationController = loader.getController();
        confirmationController.setGame_mode("vs");
        confirmationController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    private void setBoard(int[][] state,GridPane gridPane) throws IOException {
        int index=1;
        for (Node node:gridPane.getChildren()){
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    if (state[i][j]==index){
                        GridPane.setRowIndex(node,i);
                        GridPane.setColumnIndex(node,j);
                    }
                }
            }
            index++;
        }
        if (endCheck(gridPane)){
            DatabaseSetters databaseSetters = new DatabaseSetters();
            databaseSetters.setSoloScore(user, 0);
            lose();
        }
    }

    private boolean endCheck(GridPane gridPane){
        boolean check=false;
        int index=0;
            for (Node node : gridPane.getChildren()) {
                if (end[index][0] == (GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0) && end[index][1] == (GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0)) {
                    check = true;
                } else {
                    check = false;
                    break;
                }
                index++;
            }
        return check;
    }
}
