package com.application.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public void onCancelButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }


    private void checkPassword(String username, String password){
        if(isASuitableUsername(username)){
            if ((isASuitablePassword(password))){
                System.out.println("OK");
            }
            else
                passwordInput.setStyle("-fx-prompt-text-fill: red;");
        }
        else
            usernameInput.setStyle("-fx-prompt-text-fill: red;");

    }


    private boolean isASuitablePassword(String password) {
        return isPasswordMatchingWithRegex(password);
    }


    private boolean isPasswordMatchingWithRegex(String password) {
        Matcher m = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$").matcher(password);
        return m.matches();
    }


    private boolean isASuitableUsername(String username) {
        return username.length() >= 4 && username.length() <= 15;
    }
}
