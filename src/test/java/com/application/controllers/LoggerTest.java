package com.application.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.DebugUtils.saveWindow;

import java.io.IOException;
import java.nio.file.Path;


@ExtendWith(ApplicationExtension.class)
class LoggerTest {
    private static final String SCREENSHOT_FAILING_TEST_PATH = "build/reports/tests/test";


    @Start
    private void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoggerController.class.getResource("/com/application/logger.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }


    @Tag("Initialization")
    @DisplayName("Test that the window is visible and has the right dimensions")
    @Test void checksForWindowApparitionCorrectness(FxRobot robot) {
        verifyThat("#panel",
                ((BorderPane borderPane) -> borderPane.getHeight() == 400 && borderPane.getWidth() == 600 && borderPane.isVisible()));

    }


    @Tag("Initialization")
    @DisplayName("Testing correct initialization of the nodes inside the login window")
    @Test void initializationTest(FxRobot robot){
        verifyThat("#cancelButton", Node::isVisible);
        verifyThat("#confirmButton", Node::isVisible);
        verifyThat("#usernameLabel", Node::isVisible);
        verifyThat("#usernameInput", Node::isVisible);
        verifyThat("#passwordLabel", Node::isVisible);
        verifyThat("#passwordInput", Node::isVisible);
        verifyThat("#passwordInput", (TextField textfield) -> textfield.getStyle().equals("-fx-prompt-text-fill: transparent;"));
        verifyThat("#usernameInput", (TextField textfield) -> textfield.getStyle().equals("-fx-prompt-text-fill: transparent;"));
    }

    @Tag("Functional")
    @DisplayName("Testing that username label is working properly with correct usernames")
    @ParameterizedTest
    @ValueSource(strings = {"user", "usern", "username", "usernameusernam"})
    void usernameInputTest(String username, FxRobot robot){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write(username);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());
        verifyThat("#usernameInput", (TextField textfield) -> textfield.getStyle().equals("-fx-prompt-text-fill: transparent;"));
    }


    /*
        saveWindow genera una immagine nel caso il test fallisce. Ho creato quindi un task gradle da eseguire alla fine di
        ogni build per assicurarmi che non siano rimaste immagini provenienti da test falliti precedentemenete.
     */
    @Disabled
    @DisplayName("Failing test that show the screenshot feature ")
    @Test void exampleOfFailingTestWithScreenshot(TestInfo testInfo){
        verifyThat("#cancelButton", Node::isDisabled, saveWindow((() -> Path.of(SCREENSHOT_FAILING_TEST_PATH, testInfo.getDisplayName() + ".png")), ""));
    }

}