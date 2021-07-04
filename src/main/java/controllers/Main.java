package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.concurrent.RecursiveTask;

public class Main extends Application {

    private static Stage stage;

    public static Stage getStage(){
        return stage;
    }

    public static void setStage(Stage stage){
        Main.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        setStage(primaryStage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/main.fxml"));
        AnchorPane anchorPane = loader.load();

        Scene scene = new Scene(anchorPane,1024,600);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Sliding Puzzle");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(1024);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
