package controllers;

import command.GoToMenuCommand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.Console;
import java.io.IOException;
import java.time.zone.ZoneOffsetTransitionRule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SoloGameController extends Controller {

    @FXML
    public Button exitButton;

    @FXML
    public AnchorPane statisticsAnchorPane;

    @FXML
    public AnchorPane gamefieldAnchorPane;

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
    public GridPane gridPane;

    @FXML
    public TextField numberofmovesTextFIeld;

    @FXML
    public TextField gametimeTextField;

    @FXML
    public TextField buttonsinplaceTextField;

    private final int[][] end = {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}, {2, 0}, {2, 1}};

    private int[][] coordinates;

    private int numberofmoves;

    private int time;

    private int buttonsinplace;

    @FXML
    void initialize() {
        coordinates = new int[end.length][2];
        numberofmoves = 0;
        time = 0;
        buttonsinplace=0;

        int index = 0;
        for (Node node : gridPane.getChildren()) {
            coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
            coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
            index++;
        }
        numberofmovesTextFIeld.setText(String.valueOf(numberofmoves));
        gametimeTextField.setText(String.valueOf(time/60)+":"+ time / 10 + time % 10);
        buttonsinplaceTextField.setText(String.valueOf(buttonsinplace));
        time++;

        Timeline timer = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<>() {
                            @Override
                            public void handle(ActionEvent event) {
                                gametimeTextField.setText(String.valueOf(time/60)+":"+ (time / 10) %6 + time % 10);
                                time++;
                            }
                        }
                ));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        randomStart(1000);
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
            for (Node node : gridPane.getChildren()) {
                coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
                coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
                index++;
            }
        }
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
        for (Node node : gridPane.getChildren()) {
            coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
            coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
            index++;
        }
        check1 = true;
        buttonsinplace=0;
        for (int i = 0; i < coordinates.length; i++) {
            if (end[i][0] != coordinates[i][0] || end[i][1] != coordinates[i][1]) {
                check1 = false;
            }
            if (end[i][0] == coordinates[i][0] && end[i][1] == coordinates[i][1]){
                buttonsinplace++;
            }
        }
        numberofmovesTextFIeld.setText(String.valueOf(numberofmoves));
        buttonsinplaceTextField.setText(String.valueOf(buttonsinplace));
        if (check1) {
            win();
        }
    }

    public void win() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/endgame.fxml"));
        AnchorPane anchorPane = loader.load();
        EndGameController endGameController = loader.getController();
        endGameController.setGame_mode("solo");
        endGameController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/confirmation.fxml"));
        AnchorPane anchorPane = loader.load();
        ConfirmationController confirmationController = loader.getController();
        confirmationController.setGame_mode("solo");
        confirmationController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }


}
