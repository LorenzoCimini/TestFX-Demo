package com.application.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LoggerController {
    public Pane panel;

    public Label usernameLabel;

    public TextField usernameInput;

    public PasswordField passwordInput;

    public Label passwordLabel;

    public Button cancelButton;

    public Button confirmButton;


    public void onConfirmButtonClicked(ActionEvent actionEvent) {
        checkPassword(usernameInput.getText(), passwordInput.getText());
    }

    private void checkPassword(String username, String password){
        System.out.println(username + password);
    }

    public void onCancelButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }
}
