package org.example.calendarapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class UIController implements Initializable {

    // ---------- plus button / create task pane ----------
    @FXML private Button plusButton;
    @FXML private AnchorPane createNewTaskPane;
    @FXML private Button toDoExitButton;
    @FXML private Button createButton;

    // ---------- color picker ----------
    @FXML private Button selectColorButton;
    @FXML private Button selectColorExitButton;
    @FXML private Pane selectColorPane;
    @FXML private Button applyButton;
    @FXML private ToggleGroup colorToggle;

    // ---------- form fields ----------
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private DatePicker datePicker1;

    // ---------- spinners ----------
    @FXML private Spinner<Integer> hourSpinner1;
    @FXML private Spinner<Integer> minuteSpinner1;
    @FXML private Spinner<Integer> hourSpinner2;
    @FXML private Spinner<Integer> minuteSpinner2;
    @FXML private Label hourLabel1;
    @FXML private Label minuteLabel1;
    @FXML private Label hourLabel2;
    @FXML private Label minuteLabel2;
    @FXML private ToggleGroup time1;
    @FXML private ToggleGroup time2;

    // ---------- weekly grid ----------
    @FXML private GridPane weeklyGrid;

    // ---------- view task pane ----------
    @FXML private AnchorPane viewTaskPane;
    @FXML private Label viewTaskTitle;
    @FXML private Label viewTaskType;
    @FXML private Label viewTaskDescription;
    @FXML private Label viewTaskDate;
    @FXML private Label viewTaskTime;
    @FXML private Pane viewTaskColorBox;
    @FXML private Button completeButton;
    @FXML private Button eraseButton;
    @FXML private Button viewTaskExitButton;

    private Label currentTaskLabel = null;

    // ---------- spinner value factories ----------
    private SpinnerValueFactory<Integer> hourSpin1;
    private SpinnerValueFactory<Integer> minuteSpin1;
    private SpinnerValueFactory<Integer> hourSpin2;
    private SpinnerValueFactory<Integer> minuteSpin2;

    private int hourNum1, minuteNum1, hourNum2, minuteNum2;

    // stores the color the user picked (default blue)
    private String selectedColor = "#9FD5FF";

    // -------------------------------------------------------
    //  PLUS BUTTON
    // -------------------------------------------------------
    @FXML
    protected void onPlusButtonClick() {
        createNewTaskPane.setVisible(true);
        createNewTaskPane.toFront();
    }

    @FXML
    protected void onToDoExitButtonClick() {
        createNewTaskPane.setVisible(false);
    }

    // -------------------------------------------------------
    //  CREATE BUTTON — main logic
    // -------------------------------------------------------
    @FXML
    protected void onCreateButtonClick() {

        // 1. read title
        String title = titleField.getText();
        if (title == null || title.isBlank()) title = "(no title)";

        // 2. read date → figure out which column (0=Mon … 6=Sun)
        LocalDate date = datePicker1.getValue();
        if (date == null) {
            date = LocalDate.now();
        }
        int col = getDayColumn(date.getDayOfWeek());

        // 3. read start hour → figure out which row (0=12AM … 23=11PM)
        int hour = hourSpinner1.getValue();
        ToggleButton amPm1 = (ToggleButton) time1.getSelectedToggle();
        boolean isPM = amPm1 != null && amPm1.getText().equals("PM");
        int row = convertToRow(hour, isPM);

        // 4. build a label for the task
        Label taskLabel = new Label(title);
        taskLabel.setMaxWidth(Double.MAX_VALUE);
        taskLabel.setMaxHeight(Double.MAX_VALUE);
        taskLabel.setStyle(
                "-fx-background-color: " + selectedColor + ";" +
                        "-fx-background-radius: 5;" +
                        "-fx-padding: 2 4 2 4;" +
                        "-fx-font-size: 11px;" +
                        "-fx-wrap-text: true;"
        );
        // store the entry data on the label so we can show it later
        CalendarEntry entry = new CalendarEntry(
                title,
                descriptionField.getText(),
                date,
                hourSpinner1.getValue(), minuteSpinner1.getValue(), isPM,
                hourSpinner2.getValue(), minuteSpinner2.getValue(),
                ((ToggleButton) time2.getSelectedToggle()) != null &&
                        ((ToggleButton) time2.getSelectedToggle()).getText().equals("PM"),
                selectedColor
        );

        taskLabel.setOnMouseClicked(e -> openViewTaskPane(entry, taskLabel));
        // 5. place it in the grid
        weeklyGrid.add(taskLabel, col, row);

        // 6. close the pane and reset the form
        createNewTaskPane.setVisible(false);
        titleField.clear();
        descriptionField.clear();
        datePicker1.setValue(null);
    }

    // -------------------------------------------------------
    //  COLOR PICKER
    // -------------------------------------------------------
    @FXML
    protected void onSelectColorButtonClick() {
        selectColorPane.setVisible(true);
        selectColorPane.toFront();
    }

    @FXML
    protected void onSelectColorExitButtonClick() {
        selectColorPane.setVisible(false);
    }

    @FXML
    protected void onSelectApplyButton() {
        ToggleButton selected = (ToggleButton) colorToggle.getSelectedToggle();
        if (selected != null) {
            // pull the color straight from the button's inline style
            String style = selected.getStyle();
            // style looks like "-fx-background-color: #FF9898;"
            String color = style.replace("-fx-background-color:", "").replace(";", "").trim();
            selectedColor = color;
            selectColorButton.setStyle(
                    "-fx-background-color: " + selectedColor + ";" +
                            "-fx-border-color: #8EABE8;" +
                            "-fx-border-radius: 10;" +
                            "-fx-background-radius: 10;"
            );
        }
        selectColorPane.setVisible(false);
    }

    @FXML
    protected void onViewTaskExitButtonClick() {
        viewTaskPane.setVisible(false);
    }

    @FXML
    protected void onCompleteButtonClick() {
        // you can add strikethrough or remove the task later
        viewTaskPane.setVisible(false);
    }

    @FXML
    protected void onEraseButtonClick() {
        // remove the label from the grid
        if (currentTaskLabel != null) {
            weeklyGrid.getChildren().remove(currentTaskLabel);
        }
        viewTaskPane.setVisible(false);
    }

    private void openViewTaskPane(CalendarEntry entry, Label taskLabel) {
        currentTaskLabel = taskLabel;
        viewTaskTitle.setText(entry.getTitle());
        viewTaskDescription.setText(entry.getDescription());
        viewTaskDate.setText(entry.getDate().toString());
        viewTaskTime.setText(entry.getTimeString());
        viewTaskColorBox.setStyle("-fx-background-color: " + entry.getColor() + "; -fx-background-radius: 4;");
        viewTaskPane.setVisible(true);
        viewTaskPane.toFront();
    }

    // -------------------------------------------------------
    //  SPINNERS
    // -------------------------------------------------------
    protected void spinMethod() {
        hourSpin1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 12);
        hourSpinner1.setValueFactory(hourSpin1);

        minuteSpin1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minuteSpinner1.setValueFactory(minuteSpin1);

        hourSpin2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 12);
        hourSpinner2.setValueFactory(hourSpin2);

        minuteSpin2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        minuteSpinner2.setValueFactory(minuteSpin2);
    }

    public void displayTime() {
        hourNum1 = hourSpinner1.getValue();
        minuteNum1 = minuteSpinner1.getValue();
        hourLabel1.setText(String.valueOf(hourNum1));
        minuteLabel1.setText(String.valueOf(minuteNum1));

        hourNum2 = hourSpinner2.getValue();
        minuteNum2 = minuteSpinner2.getValue();
        hourLabel2.setText(String.valueOf(hourNum2));
        minuteLabel2.setText(String.valueOf(minuteNum2));
    }

    // -------------------------------------------------------
    //  HELPERS
    // -------------------------------------------------------

    /**
     * Maps a day of week to the grid column index.
     * Mon=0, Tue=1, Wed=2, Thu=3, Fri=4, Sat=5, Sun=6
     */
    private int getDayColumn(DayOfWeek day) {
        return switch (day) {
            case MONDAY    -> 0;
            case TUESDAY   -> 1;
            case WEDNESDAY -> 2;
            case THURSDAY  -> 3;
            case FRIDAY    -> 4;
            case SATURDAY  -> 5;
            case SUNDAY    -> 6;
        };
    }

    /**
     * Converts a 12-hour clock value + AM/PM into a grid row (0–23).
     * Row 0 = 12AM, Row 1 = 1AM, ... Row 12 = 12PM, Row 13 = 1PM ...
     */
    private int convertToRow(int hour12, boolean isPM) {
        int hour24;
        if (!isPM) {
            // AM: 12AM → 0, 1AM → 1 ... 11AM → 11
            hour24 = (hour12 == 12) ? 0 : hour12;
        } else {
            // PM: 12PM → 12, 1PM → 13 ... 11PM → 23
            hour24 = (hour12 == 12) ? 12 : hour12 + 12;
        }
        return hour24;
    }

    // -------------------------------------------------------
    //  INITIALIZE
    // -------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spinMethod();
    }
}