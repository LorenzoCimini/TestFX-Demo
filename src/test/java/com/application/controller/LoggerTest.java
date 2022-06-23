package com.application.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.testfx.util.DebugUtils.saveNode;
import static org.testfx.util.DebugUtils.saveWindow;

import java.io.IOException;
import java.nio.file.Path;


@ExtendWith(ApplicationExtension.class)
public class LoggerTest {
    private static final String SCREENSHOT_FAILING_TEST_PATH = "build/reports/tests/";


    @BeforeAll
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }


    @Start
    private void start(Stage stage) throws IOException {
        LoggerController loggerController = new LoggerController();
        FXMLLoader fxmlLoader = new FXMLLoader(LoggerController.class.getResource("/com/application/logger.fxml"));
        fxmlLoader.setController(loggerController);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @DisplayName("Test that the window is visible and has the right dimensions")
    @Test void checksForWindowApparitionCorrectness() {
        verifyThat("#panel",
                ((BorderPane borderPane) -> borderPane.getHeight() == 280 && borderPane.getWidth() == 450 && borderPane.isVisible()));

    }


    @DisplayName("Testing correct initialization of nodes' visibility inside the login window")
    @Test void initializationTest(){
        verifyThat("#cancelButton",  Node::isVisible);
        verifyThat("#confirmButton",  Node::isVisible);
        verifyThat("#usernameLabel", Node::isVisible);
        verifyThat("#usernameInput", Node::isVisible);
        verifyThat("#passwordLabel", Node::isVisible);
        verifyThat("#passwordInput", Node::isVisible);
        verifyThat("#passwordErrorLabel", (Label label) -> !label.isVisible());
        verifyThat("#usernameErrorLabel", (Label label) -> !label.isVisible());
    }


    @DisplayName("Testing that username error label is not appearing with correct usernames")
    @ParameterizedTest
    @ValueSource(strings = {"user", "usern", "username", "usernameusernam"})
    void usernameInputTestWithCorrectData(String username, FxRobot robot){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write(username);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());
        verifyThat("#usernameErrorLabel", (Label label) -> !label.isVisible());
    }


    @DisplayName("Testing that username error label is appearing with wrong usernames")
    @ParameterizedTest
    @ValueSource(strings = {"", "u", "us", "use", "usernameusername"})
    void usernameInputTestWithWrongData(String username, FxRobot robot){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write(username);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());

        verifyThat(
                "#usernameErrorLabel",
                (Label label) -> label.isVisible(),
                saveNode(
                        robot.lookup("#panel").queryAs(BorderPane.class).lookup("#usernameInput"),
                        SCREENSHOT_FAILING_TEST_PATH + "[TEST: usernameInputTestWithWrongData]", 1
                        ));
    }

    @Disabled
    @DisplayName("Testing that password input is working properly with correct passwords")
    @ParameterizedTest
    @ValueSource(strings = {"Lorenzo9@", "Abcdefg1&", "Jdkdfhfj4$"})
    void passwordInputWithCorrectData(String password, FxRobot robot){
        robot.clickOn(robot.lookup("#passwordInput").queryAs(TextField.class)).write(password);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());
        verifyThat("#passwordErrorLabel", (Label label) -> !label.isVisible());
    }


    @Disabled
    @DisplayName("Testing that password input is working properly with wrong password")
    @ParameterizedTest
    @ValueSource(strings = {"", "u", "Lorenzo", "Lorenzo9", "lorenzo@"})
    void passwordInputWithWrongData(String password, FxRobot robot){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write("username");
        robot.clickOn(robot.lookup("#passwordInput").queryAs(TextField.class)).write(password);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());

        verifyThat(
                "#passwordErrorLabel",
                (Label label) -> label.isVisible(),
                saveNode(
                        robot.lookup("#passwordInput").queryAs(TextField.class),
                        SCREENSHOT_FAILING_TEST_PATH + "[TEST: usernameInputTestWithWrongData]",
                        1
                ));
    }


    @Disabled
    @DisplayName("Failing test that show the screenshot feature ")
    @Test void exampleOfFailingTestWithScreenshot(TestInfo testInfo){
        verifyThat("#cancelButton", Node::isDisabled, saveWindow((() -> Path.of(SCREENSHOT_FAILING_TEST_PATH, testInfo.getDisplayName() + ".png")), ""));
    }
}