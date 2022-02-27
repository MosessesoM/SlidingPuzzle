package controllers;

import aialgorithms.AStar;
import aialgorithms.BoardState;
import aialgorithms.Genetic;
import command.GoToMenuCommand;
import database.DatabaseSetters;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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

    private AStar aStar1;

    private AStar aStar2;

    private Genetic genetic1;

    private Genetic genetic2;

    private int[][] coordinates;

    private int numberofmoves;

    private int time;

    private int buttonsinplace;

    private int score;

    private Timeline timer1;

    private ArrayList<BoardState> finalSolution;

    private int level;

    @FXML
    void initialize() {
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

        gametimeTextField.setText(String.valueOf(time / 60) + ":" + time / 10 + time % 10);
        time++;
    }

    private void gameTimer(String aiAlgorithm1, ArrayList<BoardState> solution1, String aiAlgorithm2, ArrayList<BoardState> solution2) {
        timer1 = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            gametimeTextField.setText((time / 60) + ":" + (time / 10) % 6 + time % 10);

                            time++;

                            if (finalSolution == null) {
                                if (endCheck(gridPane1)) {
                                    score = time + numberofmoves;
                                    DatabaseSetters databaseSetters = new DatabaseSetters();
                                    databaseSetters.setVsScore(user, score, time, numberofmoves, level);
                                    try {
                                        win(aiAlgorithm1.equals(aiAlgorithm2) ? aiAlgorithm1.concat(" 1") : aiAlgorithm1);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (solution1.size() > 0) {
                                    setBoard(solution1.remove(solution1.size() - 1).getState(), gridPane1);
                                }

                                if (endCheck(gridPane2)) {
                                    score = time + numberofmoves;
                                    DatabaseSetters databaseSetters = new DatabaseSetters();
                                    databaseSetters.setVsScore(user, score, time, numberofmoves, level);
                                    try {
                                        win(aiAlgorithm2.equals(aiAlgorithm1) ? aiAlgorithm2.concat(" 2") : aiAlgorithm2);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (solution2.size() > 0) {
                                    setBoard(solution2.remove(solution2.size() - 1).getState(), gridPane2);
                                }
                                numberofmoves++;
                                numberofmovesTextFIeld.setText(String.valueOf(numberofmoves));
                                buttonsinplaceTextField.setText("X");
                            }
                        }
                ));
        timer1.setCycleCount(Timeline.INDEFINITE);
        timer1.play();
    }

    public int getScore() {
        return score;
    }

    public void randomStart(int moves) {
        level = moves / 10;

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
            GridPane.setRowIndex(node, coordinates[index][0]);
            GridPane.setColumnIndex(node, coordinates[index][1]);
            index++;
        }

        aStar1 = new AStar(convert(buttons));
        aStar2 = new AStar(convert(buttons));
        genetic1 = new Genetic(convert(buttons));
        genetic2 = new Genetic(convert(buttons));
    }

    @FXML
    public void buttonOnAction(ActionEvent actionEvent) throws IOException {
        if (endCheck(gridPane1)) {
            score = 1000 - time - numberofmoves;
            DatabaseSetters databaseSetters = new DatabaseSetters();
            databaseSetters.setVsScore(user, score, time, numberofmoves, level);
            win("Player");
        }

        if (endCheck(gridPane2)) {
            score = time + numberofmoves;
            DatabaseSetters databaseSetters = new DatabaseSetters();
            databaseSetters.setVsScore(user, score, time, numberofmoves, level);
            win(board2Label.getText().substring(0, 6));
        }
        setBoard(finalSolution.remove(finalSolution.size() - 1).getState(), gridPane2);

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
    }

    public void win(String playerName) throws IOException {
        if (timer1 != null) {
            timer1.stop();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/win.fxml"));
        AnchorPane anchorPane = loader.load();
        WinController winController = loader.getController();
        winController.setGameController(this);
        winController.setScoreTextField();
        winController.setMainController(mainController);
        winController.setPlayer1(board1Label.getText());
        winController.setPlayer2(board2Label.getText());
        winController.setMoves(level * 10);
        winController.setWinnerNameLabel(playerName);
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

    public void aiMoves(String aiAlgorithm1, String aiAlgorithm2) {

        ArrayList<BoardState> solution1 = null;
        ArrayList<BoardState> solution2 = null;

        if (aiAlgorithm2.equals("A Star")) {
            solution2 = aStar2.findSolution();
        } else if (aiAlgorithm2.equals("Genetic")) {
            solution2 = genetic2.findSolution();
        }

        if (aiAlgorithm1.equals("A Star")) {
            solution1 = aStar1.findSolution();
        } else if (aiAlgorithm1.equals("Genetic")) {
            solution1 = genetic1.findSolution();
        } else {
            this.finalSolution = solution2;
        }

        if (solution1 != null) {
            solution1.remove(solution1.size() - 1);
        } else {
            solution1 = new ArrayList<>(0);
        }

        solution2.remove(solution2.size() - 1);

        gameTimer(aiAlgorithm1, solution1, aiAlgorithm2, solution2);
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        if (timer1 != null) {
            timer1.stop();
        }
        new GoToMenuCommand(mainController).execute();
    }

    private void setBoard(int[][] state, GridPane gridPane) {
        int index = 1;
        for (Node node : gridPane.getChildren()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (state[i][j] == index) {
                        GridPane.setRowIndex(node, i);
                        GridPane.setColumnIndex(node, j);
                    }
                }
            }
            index++;
        }
    }

    private boolean endCheck(GridPane gridPane) {
        boolean check = false;
        int index = 0;
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

    public void setBoard1Label(String player) {
        board1Label.setText(player + " Board");
    }

    public void setBoard2Label(String player) {
        board2Label.setText(player + " Board");
    }
}
