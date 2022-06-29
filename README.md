# TestFX-Demo

### A dummy project to learn how to use TestFX to test JavaFX GUIs using TestFX.

This project uses TestFX with JUnit5/Mockito/Assertj in order to test a dummy login GUI. The goal is to create a rather small suite of tests able to test the GUI using an end-to-end test approach as well as mocking some external dependencies (database) using Mockito.

In order to run the suite in headless mode run: `gradle test` else `gradle test -PnoHeadless`
