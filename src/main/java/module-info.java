module org.example.denemeson {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.denemeson to javafx.fxml;
    exports org.example.denemeson;
}