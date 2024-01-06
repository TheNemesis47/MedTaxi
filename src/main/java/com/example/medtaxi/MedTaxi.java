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
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("benvenuto.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm());
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.getIcons();
            stage.setScene(scene);
            stage.setTitle("MedTaxi");
            stage.show();
        } catch (Throwable e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
