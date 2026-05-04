package org.example.calendarapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CalendarApp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 780);
        stage.setTitle("CalendarApp");

        stage.setScene(scene);
        stage.setResizable(false);

        scene.getStylesheets().add("org/example/calendarapp/checkBoxStyle.css");
        scene.getStylesheets().add("org/example/calendarapp/amPmboxStyle.css");
        scene.getStylesheets().add("org/example/calendarapp/colorClick.css");

        stage.show();
    }
}
