package com.example.medtaxi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.util.Objects;

public class MedTaxi extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("benvenuto.fxml")));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("MedTaxi");
            primaryStage.show();
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

    public static void cambiaScena(String fxml, Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(View.class.getResource(fxml)));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("style.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
