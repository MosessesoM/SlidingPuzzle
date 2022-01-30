package controllers;

import database.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main extends Application {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Main.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setStage(primaryStage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/main.fxml"));
        AnchorPane anchorPane = loader.load();

        Scene scene = new Scene(anchorPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setTitle("Sliding Puzzle");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
