package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterController extends Controller {

    @FXML
    public Button registerButton;

    @FXML
    public Button backButton;

    @FXML
    public Label nameLabel;

    @FXML
    public Label emailLabel;

    @FXML
    public Label passwordLabel;

    @FXML
    public Label confirmpasswordLabel;

    @FXML
    public PasswordField passwordTextField;

    @FXML
    public PasswordField confirmpasswordTextField;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public Button settingButton;

    @FXML
    void initialize(){

    }

    @FXML
    public void registerButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/login.fxml"));
        AnchorPane anchorPane = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/login.fxml"));
        AnchorPane anchorPane = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void passwordTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void confirmpasswordTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void nameTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void emailTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void settingButtonOnAction(ActionEvent actionEvent) {
    }
}
