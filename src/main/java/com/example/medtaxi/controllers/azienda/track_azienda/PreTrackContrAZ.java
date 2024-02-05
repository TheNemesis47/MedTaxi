package com.example.medtaxi.controllers.azienda.track_azienda;

import com.example.medtaxi.design_patterns.command.ChangeSceneAndUpdateAziendaCommand;
import com.example.medtaxi.design_patterns.command.Command;
import com.example.medtaxi.design_patterns.command.CommandExecutor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class PreTrackContrAZ {
    private Stage stage;
    @FXML
    private TextField code_track;



    // Metodo per tornare alla schermata principale dell'azienda
    @FXML
    public void switchBack(ActionEvent event) throws IOException {
        Command command = new ChangeSceneAndUpdateAziendaCommand(event, "/com/example/medtaxi/azienda/homeAz.fxml");
        CommandExecutor.executeCommand(command);
    }



    // Metodo per passare alla schermata di tracciamento
    @FXML
    public void SwitchRight(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/medtaxi/azienda/track_azienda/trackAZ.fxml"));
        Parent root = loader.load();
        TrackContrAZ trackContr = loader.getController();
        String code = code_track.getText();
        trackContr.visualizzaRoute(code);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}