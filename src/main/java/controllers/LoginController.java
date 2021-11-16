package controllers;

import command.GoToMenuCommand;
import database.DatabaseGetters;
import database.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController extends Controller {

    @FXML
    public AnchorPane mainAnchorPane;

    @FXML
    public AnchorPane loginAnchorPane;

    @FXML
    public Button loginButton;

    @FXML
    public Button registerButton;

    @FXML
    public Label loginLabel;

    @FXML
    public Label passwordLabel;

    @FXML
    public PasswordField passwordTextField;

    @FXML
    public TextField loginTextField;

    @FXML
    public Button exitButton;

    @FXML
    public Label passwordWarningLabel;

    @FXML
    public Label loginWarningLabel;

    @FXML
    void initialize(){

    }

    @FXML
    public void loginButtonOnAction(ActionEvent actionEvent) throws IOException {
        DatabaseGetters databaseGetters= new DatabaseGetters();
        if (loginTextField.getText().equals("") || passwordTextField.getText().equals("")){
            loginWarningLabel.setText("Wrong data");
        }else {
            User user = databaseGetters.getUser(loginTextField.getText(),passwordTextField.getText());
            if (user==null)
            {
                loginWarningLabel.setText("Wrong data");
            } else if (loginTextField.getText().equals(user.getName()) && passwordTextField.getText().equals(user.getPassword())) {
                Main.getStage().setUserData(user);
                new GoToMenuCommand(mainController).execute();
            } else {
                loginWarningLabel.setText("Wrong data");
            }
        }
    }

    @FXML
    public void registerButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/register.fxml"));
        AnchorPane anchorPane = loader.load();
        RegisterController registerController = loader.getController();
        registerController.setMainController(mainController);
        mainController.setScreen(anchorPane);
    }

    @FXML
    public void passwordTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void loginTextFieldOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void exitButtonOnAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
