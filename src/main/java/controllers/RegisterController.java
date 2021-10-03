package controllers;

import database.DatabaseGetters;
import database.DatabaseSetters;
import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.io.IOException;
import java.util.List;

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
    public Label warningLabel;

    @FXML
    void initialize(){

    }

    @FXML
    public void registerButtonOnAction(ActionEvent actionEvent) throws IOException {
        DatabaseSetters databaseSetters = new DatabaseSetters();
        DatabaseGetters databaseGetters = new DatabaseGetters();

        boolean check = false;

        List<User> users = databaseGetters.getUsers();

        if (nameTextField.getText().equals("") || emailTextField.getText().equals("") || passwordTextField.getText().equals("") || confirmpasswordTextField.getText().equals("")){
            warningLabel.setText("Fill in all fields");
        } else {
            for (User u : users) {
                if (u.getName().equals(nameTextField.getText()) || u.getEmail().equals(passwordLabel.getText())){
                    warningLabel.setText("Account with this data already exists");
                    check=true;
                    break;
                }
            }
            if (!check){
                if (passwordTextField.getText().equals(confirmpasswordTextField.getText())){
                    databaseSetters.setUser(nameTextField.getText(),passwordTextField.getText(),emailTextField.getText());

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("/views/login.fxml"));
                    AnchorPane anchorPane = loader.load();
                    LoginController loginController = loader.getController();
                    loginController.setMainController(mainController);
                    loginController.loginWarningLabel.setText("Account registered");
                    mainController.setScreen(anchorPane);
                } else {
                    warningLabel.setText("Password not confirmed");
                }
            }
        }
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
