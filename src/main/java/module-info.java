module com.example.bakery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.bakery to javafx.fxml;
    exports com.example.bakery;
}