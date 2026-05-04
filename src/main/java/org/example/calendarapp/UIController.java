package org.example.calendarapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class UIController implements Initializable {

// this is for the plus button / to do list  stuff

    @FXML
    private Button plusButton;

    @FXML
    private AnchorPane createNewTaskPane;

    @FXML
    private Button toDoExitButton;

    @FXML
    protected void onPlusButtonClick() {
        createNewTaskPane.setVisible(true);
        createNewTaskPane.toFront();
    }
    @FXML
    protected void onToDoExitButtonClick() {
        createNewTaskPane.setVisible(false);
    }

    @FXML
    private Button createButton;
    @FXML
    protected void onCreateButtonClick(){
        createNewTaskPane.setVisible(false);
    }

    @FXML
    private Button selectColorButton;
    @FXML
    private Button selectColorExitButton;
    @FXML
    private Pane selectColorPane;

    @FXML
    protected void onSelectColorButtonClick(){
        selectColorPane.setVisible(true);
        selectColorPane.toFront();
    }
    @FXML
    protected void onSelectColorExitButtonClick() {
        selectColorPane.setVisible(false);
    }

    @FXML
    private Button applyButton;
    @FXML
    protected void onSelectApplyButton(){
        selectColorPane.setVisible(false);
    }



// All this is for the hourSpinner1 and minuteSpinner1

    @FXML
    private Spinner<Integer> hourSpinner1;
    @FXML
    private Spinner<Integer> minuteSpinner1;

    @FXML
    private Spinner<Integer> hourSpinner2;
    @FXML
    private Spinner<Integer> minuteSpinner2;


    @FXML
    private Label hourLabel1;
    @FXML
    private Label minuteLabel1;
    @FXML
    private Label hourLabel2;
    @FXML
    private Label minuteLabel2;

    private SpinnerValueFactory<Integer> hourSpin1;
    private SpinnerValueFactory<Integer> minuteSpin1;
    private SpinnerValueFactory<Integer> hourSpin2;
    private SpinnerValueFactory<Integer> minuteSpin2;

    private int hourNum1;
    private int minuteNum1;
    private int hourNum2;
    private int minuteNum2;


    protected void spinMethod(){
        hourSpin1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,12,0);
        hourSpinner1.setValueFactory(hourSpin1);

        minuteSpin1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        minuteSpinner1.setValueFactory(minuteSpin1);

        hourSpin2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,12,0);
        hourSpinner2.setValueFactory(hourSpin2);

        minuteSpin2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,0);
        minuteSpinner2.setValueFactory(minuteSpin2);
    }

    public void displayTime(){
        hourNum1 = hourSpinner1.getValue();
        minuteNum1 = minuteSpinner1.getValue();

        hourLabel1.setText(String.valueOf(hourNum1));
        minuteLabel1.setText(String.valueOf(minuteNum1));

        hourNum2 = hourSpinner2.getValue();
        minuteNum2 = minuteSpinner2.getValue();

        hourLabel2.setText(String.valueOf(hourNum2));
        minuteLabel2.setText(String.valueOf(minuteNum2));


    }
    // end of hour/minute spinner stuff

    @Override
    public void initialize(URL url, ResourceBundle rb){
        spinMethod();
    }








}
