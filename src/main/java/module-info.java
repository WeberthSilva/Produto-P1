module org.example.projetop1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projetop1 to javafx.fxml;
    exports org.example.projetop1;


}