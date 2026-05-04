module org.example.calendarapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.calendarapp to javafx.fxml;
    exports org.example.calendarapp;
}