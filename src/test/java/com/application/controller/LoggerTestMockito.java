package com.application.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.application.model.Database;
import com.application.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@ExtendWith(ApplicationExtension.class)
public class LoggerTestMockito {

    private final Database database = mock(Database.class);


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

    // To set a controller on a loader (in JavaFX) you CANNOT also define a fx:controller attribute in your fxml file.
    @Start
    private void start(Stage stage) throws IOException {
        LoggerController loggerController = new LoggerController(database);
        FXMLLoader fxmlLoader = new FXMLLoader(LoggerController.class.getResource("/com/application/logger.fxml"));
        fxmlLoader.setController(loggerController);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }


    @DisplayName("Testing that call to database is done when inserted data right")
    @Test void checkThatDatabaseCallIsDone(FxRobot robot){
        when(database.login(new User("lorenzo", "Password9@"))).thenReturn(true);

        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write("lorenzo");
        robot.clickOn(robot.lookup("#passwordInput").queryAs(TextField.class)).write("Password9@");
        robot.clickOn(robot.lookup("#confirmButton").queryButton());

        verify(database, times(1)).login(new User("lorenzo", "Password9@"));
    }


    @DisplayName("Testing that call to database is not done when inserted data is wrong")
    @Test void checkThatDatabaseCallIsNotDone(FxRobot robot){
        robot.clickOn(robot.lookup("#usernameInput").queryAs(TextField.class)).write("lorenzo");
        robot.clickOn(robot.lookup("#passwordInput").queryAs(TextField.class)).write("Password");
        robot.clickOn(robot.lookup("#confirmButton").queryButton());

        verifyNoInteractions(database);
    }
}
