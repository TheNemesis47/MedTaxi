package com.example.medtaxi.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeContr {

    @FXML
    private Label helloText;

    @FXML
    private ImageView notificationImage;

    @FXML
    private Pane panenotifiche;

    private int index = 0;
    private static final Duration DURATION = Duration.seconds(3);

    private String[] stringheDaMostrare = {
            "Benvenuto in MedTaxi, la soluzione innovativa per trasferimenti sanitari sicuri e affidabili. Prenota ora e vivi un'esperienza di trasporto senza stress!",
            "Notifica: Il tuo autista è in arrivo. Si prega di essere pronto per il trasferimento.",
            "Notifica: Aggiornamento prenotazione - Nuovo orario confermato per il tuo trasferimento.",
            // ... altre notifiche ...
    };

    @FXML
    public void initialize() {
        notificationImage.addEventHandler(MouseEvent.MOUSE_CLICKED, this::toggleNotificationPanel);

        Timeline timeline = new Timeline(new KeyFrame(
                DURATION,
                this::updateText
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateText(ActionEvent event) {
        addNewLabel(stringheDaMostrare[index]);
        index = (index + 1) % stringheDaMostrare.length;
    }

    private void toggleNotificationPanel(MouseEvent event) {
        panenotifiche.setVisible(!panenotifiche.isVisible());

        if (panenotifiche.isVisible()) {
            Timeline closeTimeline = new Timeline(new KeyFrame(
                    Duration.seconds(5),
                    ae -> panenotifiche.setVisible(false)
            ));
            closeTimeline.setDelay(Duration.seconds(3));
            closeTimeline.play();
        }
    }

    private void addNewLabel(String newText) {
        Text newLabelText = new Text(newText);
        newLabelText.getStyleClass().addAll("notification-label");
        newLabelText.setWrappingWidth(520);

        Label dateLabel = new Label(getCurrentDateTime());
        dateLabel.getStyleClass().addAll("time-label");

        Pane labelPane = new Pane();
        labelPane.getStyleClass().add("notification-pane");
        labelPane.getChildren().addAll(newLabelText, dateLabel);

        labelPane.setPrefWidth(520);

        // Calcola l'altezza totale e posiziona il nuovo label sopra i precedenti con lo spazio fisso
        double totalHeight = 0.0;
        for (javafx.scene.Node node : panenotifiche.getChildren()) {
            totalHeight += node.getBoundsInLocal().getHeight() + 10; // altezza + spaziatura
        }
        labelPane.setLayoutY(totalHeight);

        panenotifiche.getChildren().add(0, labelPane);
    }

    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }

    public void displayName(String mail) {
        helloText.setText("Ciao " + mail);
    }
}
