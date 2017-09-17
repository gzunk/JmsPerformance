package com.gzunk.jmsperformance.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javax.annotation.Resource;

public class MainForm {

    @FXML
    private TextField textField1;

    @FXML
    private Slider slider1;

    @Resource
    private ClientApp clientApp;

    @FXML
    protected void clickButton(ActionEvent event) {
        String message = textField1.getText();
        final int value = (int)slider1.getValue();
        clientApp.postMessages(message, value);
    }
}
