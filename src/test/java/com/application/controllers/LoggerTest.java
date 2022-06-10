package com.application.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        FXMLLoader fxmlLoader = new FXMLLoader(LoggerController.class.getResource("/com/application/logger.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @Tag("Initialization")
    @DisplayName("Test that the window is visible and has the right dimensions")
    @Test void checksForWindowApparitionCorrectness(FxRobot robot) {
        verifyThat("#panel",
                ((BorderPane borderPane) -> borderPane.getHeight() == 280 && borderPane.getWidth() == 450 && borderPane.isVisible()));

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
        verifyThat("#passwordErrorLabel", (Label label) -> !label.isVisible());
        verifyThat("#usernameErrorLabel", (Label label) -> !label.isVisible());
    }


    @Tag("Functional")
    @DisplayName("Testing that username input is working properly with correct usernames")
    @ParameterizedTest
    @ValueSource(strings = {"user", "usern", "username", "usernameusernam"})
    void usernameInputTestWithCorrectData(String username, FxRobot robot){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write(username);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());
        verifyThat("#usernameErrorLabel", (Label label) -> !label.isVisible());
    }


    @Tag("Functional")
    @DisplayName("Testing that username input is working properly with wrong usernames")
    @ParameterizedTest
    @ValueSource(strings = {"", "u", "us", "use", "usernameusername"})
    void usernameInputTestWithWrongData(String username, FxRobot robot, TestInfo testInfo){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write(username);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());

        Node usernameInput = robot.lookup("#panel").queryAs(BorderPane.class).lookup("#usernameInput");

        verifyThat(
                "#usernameErrorLabel",
                (Label label) -> label.isVisible(),
                saveNode(
                        robot.lookup("#panel").queryAs(BorderPane.class).lookup("#usernameInput"),
                        SCREENSHOT_FAILING_TEST_PATH + "[TEST: usernameInputTestWithWrongData]", 1
                        ));
    }


    @Disabled
    @DisplayName("Failing test that show the screenshot feature ")
    @Test void exampleOfFailingTestWithScreenshot(TestInfo testInfo){
        verifyThat("#cancelButton", Node::isDisabled, saveWindow((() -> Path.of(SCREENSHOT_FAILING_TEST_PATH, testInfo.getDisplayName() + ".png")), ""));
    }

}