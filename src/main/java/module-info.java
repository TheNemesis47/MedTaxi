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
    exports com.example.medtaxi.controllers;
    opens com.example.medtaxi.controllers to javafx.fxml;
    exports com.example.medtaxi.singleton;
    opens com.example.medtaxi.singleton to javafx.fxml;
    exports com.example.medtaxi.classi;
    opens com.example.medtaxi.classi to javafx.fxml;
    exports com.example.medtaxi.interfaces;
    opens com.example.medtaxi.interfaces to javafx.fxml;
    exports com.example.medtaxi.factoryMethod;
    opens com.example.medtaxi.factoryMethod to javafx.fxml;


}