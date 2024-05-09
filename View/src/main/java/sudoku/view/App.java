package sudoku.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.saxon.expr.parser.Loc;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Start Sudoku GUI");

        primaryStage = stage;

        // Locale locale = new Locale.Builder().setLanguage("en").build();
        Locale locale = Locale.getDefault();

        ResourceBundle bundle;
        if (locale.getLanguage().equals("pl")) {
            bundle = ResourceBundle.getBundle("sudoku.view.bundles.pl_PL");
        } else { // Default to English for other languages
            bundle = ResourceBundle.getBundle("sudoku.view.bundles.en_EN");
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sudoku/view/MainMenu.fxml"), bundle);

        MainMenuController mainMenuController = new MainMenuController();
        loader.setController(mainMenuController);

        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Sudoku Game");
        primaryStage.show();
    }

    static void setScene(Parent newRoot) {
        primaryStage.setScene(new Scene(newRoot));
    }

    public static void main(String[] args) {
        launch();
    }

}