module com.example.Departure {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.Departure to javafx.fxml;
    exports com.example.Departure;
}