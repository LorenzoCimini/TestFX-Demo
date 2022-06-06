package com.application;

import com.application.controllers.LoggerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.DebugUtils.saveWindow;

import java.io.IOException;
import java.nio.file.Path;


@ExtendWith(ApplicationExtension.class)
class LoggerTest {
    private static final String PATH = "build/reports/tests/test";

    @Start
    private void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoggerController.class.getResource("/com/application/logger.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @Disabled
    @Test void checkThatCancelButtonWorks(FxRobot robot){
        robot.clickOn(robot.lookup("#cancelButton").queryButton());

    }

    @DisplayName("Test for checking correct initialization of the nodes inside the login window")
    @Test void initializationTest(FxRobot robot){
        verifyThat("#cancelButton", Node::isVisible);
        verifyThat("#confirmButton", Node::isVisible);
        verifyThat("#usernameLabel", Node::isVisible);
        verifyThat("#usernameInput", Node::isVisible);
        verifyThat("#passwordLabel", Node::isVisible);
        verifyThat("#passwordInput", Node::isVisible);
    }


    /*
        saveWindow genera una immagine nel caso il test fallisce. Ho creato quindi un task gradle da eseguire alla fine di
        ogni build per assicurarmi che non siano rimaste immagini provenienti da test falliti precedentemenete.
     */
    @DisplayName("Failing test that show the screenshot feature ")
    @Test void exampleOfFailingTestWithScreenshot(TestInfo testInfo){
        verifyThat("#cancelButton", Node::isDisabled, saveWindow((() -> Path.of(PATH, testInfo.getDisplayName() + ".png")), ""));
    }

}