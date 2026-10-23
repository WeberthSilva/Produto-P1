module org.example.projetop1 {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.example.projetop1.app;
    opens org.example.projetop1.app to javafx.fxml;

    exports org.example.projetop1.controller;
    opens org.example.projetop1.controller to javafx.fxml;

    exports org.example.projetop1.model;
    opens org.example.projetop1.model to javafx.fxml;

    exports org.example.projetop1.service;
    opens org.example.projetop1.service to javafx.fxml;

    exports org.example.projetop1.repository;
    opens org.example.projetop1.repository to javafx.fxml;
}