package com.application.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.application.model.Database;
import com.application.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoggerController {

    public TextField usernameInput;

    public PasswordField passwordInput;


    public Button cancelButton;


    public Button confirmButton;

    public Label usernameErrorLabel;

    public Label passwordErrorLabel;

    private final Database database;


    public LoggerController(Database database){
        this.database = database;
    }


    public LoggerController(){
        this.database = Database.getDatabase();
    }


    public void onConfirmButtonClicked(ActionEvent actionEvent) {
        checkPassword(usernameInput.getText(), passwordInput.getText());
    }


    public void onCancelButtonClicked(ActionEvent actionEvent) {
        System.exit(0);
    }


    private void checkPassword(String username, String password){
        if(handleUsernameInput(username) && handlePasswordInput(password)){
            if(database.login(new User(username, password)))
                System.out.print("LOGGED IN");
            else
                System.out.print("ACCESS DENIED");
        }
    }


    private boolean handlePasswordInput(String password) {
        if(isPasswordMatchingWithRegex(password)){
            if(passwordErrorLabel.isVisible())
                passwordErrorLabel.setVisible(false);
            return true;
        }
        else{
            passwordErrorLabel.setVisible(true);
            return false;
        }
    }


    /**
     * Checks if the password is minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character
     * @param password that must be checked
     * @return {@code true} if the password matches else {@code false}
     */
    private boolean isPasswordMatchingWithRegex(String password) {
        Matcher m = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$").matcher(password);
        return m.matches();
    }


    private boolean handleUsernameInput(String username) {
        if (username.length() >= 4 && username.length() <= 15){
            if(usernameErrorLabel.isVisible())
                usernameErrorLabel.setVisible(false);
            return true;
        }
        else {
            usernameErrorLabel.setVisible(true);
            return false;
        }


    }
}
