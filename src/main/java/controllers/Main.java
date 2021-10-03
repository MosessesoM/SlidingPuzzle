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

//        entityManager.getTransaction().begin();
//
//        User user = new User();
//        user.setEmail("rafal.godlewski.98@gmail.com");
//        user.setName("admin");
//        user.setPassword("haslo");
//        user.setPermission(true);
//
//        entityManager.persist(user);
//
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();

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
