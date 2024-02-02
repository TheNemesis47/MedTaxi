module com.example.medtaxi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;
    requires java.sql;
    requires javafx.swing;

    requires com.dlsc.formsfx;
    requires javafx.web;
    requires google.maps.services;
    requires jdk.jsobject;
    requires org.json;

    opens com.example.medtaxi to javafx.fxml;
    exports com.example.medtaxi;
    exports com.example.medtaxi.design_patterns.singleton;
    opens com.example.medtaxi.design_patterns.singleton to javafx.fxml;
    exports com.example.medtaxi.classi;
    opens com.example.medtaxi.classi to javafx.fxml;
    exports com.example.medtaxi.design_patterns.factoryMethod;
    opens com.example.medtaxi.design_patterns.factoryMethod to javafx.fxml;
    exports com.example.medtaxi.controllers.utente;
    opens com.example.medtaxi.controllers.utente to javafx.fxml;
    exports com.example.medtaxi.controllers.azienda;
    opens com.example.medtaxi.controllers.azienda to javafx.fxml;
    exports com.example.medtaxi.controllers.azienda.modifica_disponibilita;
    opens com.example.medtaxi.controllers.azienda.modifica_disponibilita to javafx.fxml;
    exports com.example.medtaxi.controllers.azienda.parco_auto;
    opens com.example.medtaxi.controllers.azienda.parco_auto to javafx.fxml;
    exports com.example.medtaxi.controllers.azienda.prenotazioni_azienda;
    opens com.example.medtaxi.controllers.azienda.prenotazioni_azienda to javafx.fxml;
    exports com.example.medtaxi.controllers.azienda.storico_prenotazioni_azienda;
    opens com.example.medtaxi.controllers.azienda.storico_prenotazioni_azienda to javafx.fxml;
    exports com.example.medtaxi.controllers.azienda.track_azienda;
    opens com.example.medtaxi.controllers.azienda.track_azienda to javafx.fxml;
    exports com.example.medtaxi.controllers.utente.annulla_prenotazione;
    opens com.example.medtaxi.controllers.utente.annulla_prenotazione to javafx.fxml;
    exports com.example.medtaxi.controllers.utente.prenota;
    opens com.example.medtaxi.controllers.utente.prenota to javafx.fxml;
    exports com.example.medtaxi.controllers.utente.registrazione_e_login;
    opens com.example.medtaxi.controllers.utente.registrazione_e_login to javafx.fxml;
    exports com.example.medtaxi.controllers.utente.storico_indirizzi_utente;
    opens com.example.medtaxi.controllers.utente.storico_indirizzi_utente to javafx.fxml;
    exports com.example.medtaxi.controllers.utente.track_utente;
    opens com.example.medtaxi.controllers.utente.track_utente to javafx.fxml;


}