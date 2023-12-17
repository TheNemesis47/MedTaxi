module com.example.medtaxi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.base;
    requires java.sql;

    requires com.dlsc.formsfx;

    opens com.example.medtaxi to javafx.fxml;
    exports com.example.medtaxi;
}