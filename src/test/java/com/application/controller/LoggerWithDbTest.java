package com.application.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.testfx.api.FxToolkit.registerPrimaryStage;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class LoggerWithDbTest{

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    private PrintStream originalOut;


    @BeforeEach
    public void setOutputReader() {
        originalOut = System.out;
        System.setOut(new PrintStream(out));
    }


    @AfterEach
    public void restoreInitialStreams() {
        System.setOut(originalOut);
    }


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
        fxmlLoader.setController(new LoggerController());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @ParameterizedTest
    @CsvSource
            ({
            "Username,  Password1#,         LOGGED IN",
            "User,      notTheRightOne7@,   ACCESS DENIED",
            "Pluto,     Password5%,         LOGGED IN",
            "usernam,   Abcdefgh8@,         ACCESS DENIED"
            })
     void testIntegrationBetweenLoginAndDB(String username, String password, String expected, FxRobot robot) {
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write(username);
        robot.clickOn(robot.lookup("#passwordInput").queryAs(TextField.class)).write(password);
        robot.clickOn(robot.lookup("#confirmButton").queryButton());

        assertThat(out.toString()).isEqualTo(expected);
    }
}
