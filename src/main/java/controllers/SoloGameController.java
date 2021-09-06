package controllers;

import command.GoToMenuCommand;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.Console;
import java.io.IOException;
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

    public int[][] end= {{0,0},{0,1},{0,2},{1,0},{1,1},{1,2},{2,0},{2,1}};

    public int[][] coordinates = new int[end.length][2];

    @FXML
    void initialize() {
        int index=0;
        for (Node node : gridPane.getChildren()) {
            coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
            coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
            index++;
        }
        randomStart(1000);
    }

//    TODO: Pamiętać żeby dać zmienne wielkości zamiast sztywnych przy zmianie rozmiarów
    public void randomStart(int moves){
        Random random = new Random();
//        ArrayList<int[]> numbers = new ArrayList<>();
//        Button[] buttons = {button1,button2,button3,button4,button5,button6,button7,button8};
        ArrayList<Button> buttons = new ArrayList<>(){
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
//        for (int i=0;i<end.length;i++){
//            numbers.add(i,end[i]);
//        }
//        for (int i=0;i<buttons.length;i++){
//            index=random.nextInt(numbers.size());
//            GridPane.setColumnIndex(buttons[i], numbers.get(index)[0]);
//            GridPane.setRowIndex(buttons[i],numbers.get(index)[1]);
//            numbers.remove(index);
//        }
        while (moves>0) {
            System.out.println(moves);
            System.out.println(random.nextInt(copy.size()));
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
        } else if (!check2) {
            GridPane.setRowIndex(button, row - 1);
        } else if (!check3) {
            GridPane.setColumnIndex(button, column + 1);
        } else if (!check4) {
            GridPane.setColumnIndex(button, column - 1);
        }
        for (Node node : gridPane.getChildren()) {
            coordinates[index][0] = GridPane.getRowIndex(node) != null ? GridPane.getRowIndex(node) : 0;
            coordinates[index][1] = GridPane.getColumnIndex(node) != null ? GridPane.getColumnIndex(node) : 0;
            index++;
        }
        check1=true;
        for (int i=0;i< coordinates.length;i++){
            if (end[i][0] != coordinates[i][0] || end[i][1] != coordinates[i][1]) {
                check1 = false;
                break;
            }
        }
        if (check1){
            for (int[] coordinate : coordinates) {
                System.out.println(coordinate[0] + "" + coordinate[1]);
            }
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
