module com.example.bakery {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bakery to javafx.fxml;
    exports com.example.bakery;
}