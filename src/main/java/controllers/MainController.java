package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class MainController extends Controller {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    void initialize() throws IOException {
        loadLogin();
    }

    public void loadLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/login.fxml"));
        AnchorPane anchorPane = loader.load();
        LoginController loginController = loader.getController();
        loginController.setMainController(this);
        setScreen(anchorPane);
    }

    public void setScreen(Pane pane) {
        mainAnchorPane.getChildren().clear();
        mainAnchorPane.getChildren().add(pane);
    }
}
