package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MenuController extends Controller {

    @FXML
    public Button solomodeButton;

    @FXML
    public Button versusmodeButton;

    @FXML
    public Button coopmodeButton;

    @FXML
    public Button scoreboardButton;

    @FXML
    public Button settingsButton;

    @FXML
    void initialize(){

    }

    @FXML
    public void solomodeButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/sologamesettings.fxml"));
        AnchorPane anchorPane = loader.load();
        SoloGameSettingsController soloGameSettingsController = loader.getController();
        soloGameSettingsController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void versusmodeButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/vsgamesettings.fxml"));
        AnchorPane anchorPane = loader.load();
        VsGameSettingsController vsGameSettingsController = loader.getController();
        vsGameSettingsController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void coopmodeButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/vsgamesettings.fxml"));
        AnchorPane anchorPane = loader.load();
        VsGameSettingsController gameSettingController = loader.getController();
        gameSettingController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void scoreboardButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/scoreboard.fxml"));
        AnchorPane anchorPane = loader.load();
        ScoreboardController scoreboardController = loader.getController();
        scoreboardController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void settingsButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/settings.fxml"));
        AnchorPane anchorPane = loader.load();
        SettingsController settingsController = loader.getController();
        settingsController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }
}
