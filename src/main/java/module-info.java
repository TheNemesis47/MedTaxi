module com.example.medtaxi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;
    requires java.sql;
    requires javafx.swing;

    requires com.dlsc.formsfx;
    requires org.jxmapviewer.jxmapviewer2;
    requires io.redlink.geocoding.osm;

    opens com.example.medtaxi to javafx.fxml;
    exports com.example.medtaxi;
    exports com.example.medtaxi.controllers;
    opens com.example.medtaxi.controllers to javafx.fxml;
    exports com.example.medtaxi.singleton;
    opens com.example.medtaxi.singleton to javafx.fxml;




}