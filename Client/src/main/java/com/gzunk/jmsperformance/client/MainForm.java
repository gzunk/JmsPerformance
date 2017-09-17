package com.gzunk.jmsperformance.client;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

public class MainForm implements Initializable {

    @FXML
    private TextField textField1;

    @FXML
    private Slider slider1;

    @FXML
    private Label sliderVal;

    @Resource
    private ClientApp clientApp;

    @FXML
    protected void clickButton(ActionEvent event) {
        String message = textField1.getText();
        final int value = (int)slider1.getValue();
        clientApp.postMessages(message, value);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sliderVal.textProperty().bind(Bindings.format("%.0f", slider1.valueProperty()));
    }
}
