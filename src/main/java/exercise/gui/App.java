package exercise.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("./resources/primary.fxml"));
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Hello World");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}