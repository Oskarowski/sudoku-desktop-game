package sudoku.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {
    public MainMenuController() {
    }

    private DifficultyEnum selectedGameDifficulty = DifficultyEnum.EASY; // Should be easy because initially easy is selected (or I'm tripping)

    @FXML
    public RadioButton easyDifficultyButton;
    @FXML
    public RadioButton mediumDifficultyButton;
    @FXML
    public RadioButton hardDifficultyButton;

    @FXML
    public Button startGameButton;
    @FXML
    public Button exitGameButton;

    @FXML
    private void handleEasyDifficultyButton(ActionEvent event) {
        setDifficulty(DifficultyEnum.EASY);
    }

    @FXML
    private void handleMediumDifficultyButton(ActionEvent event) {
        setDifficulty(DifficultyEnum.MEDIUM);
    }

    @FXML
    private void handleHardDifficultyButton(ActionEvent event) {
        setDifficulty(DifficultyEnum.HARD);
    }

    private void setDifficulty(DifficultyEnum difficulty) {
        System.out.println("Difficulty set to " + difficulty);
        selectedGameDifficulty = difficulty;
    }

    @FXML
    private void handleStartDifficultyButton(ActionEvent event) {
        startGame();
    }

    @FXML
    private void handleExitDifficultyButton(ActionEvent event) {
        Stage stage = (Stage) exitGameButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Main Menu Controller Initialized");

        easyDifficultyButton.setOnAction(this::handleEasyDifficultyButton);
        mediumDifficultyButton.setOnAction(this::handleMediumDifficultyButton);
        hardDifficultyButton.setOnAction(this::handleHardDifficultyButton);
        startGameButton.setOnAction(this::handleStartDifficultyButton);
        exitGameButton.setOnAction(this::handleExitDifficultyButton);
    }

    public void startGame() {
        System.out.println("Start Game");

        GameController gameController = new GameController(selectedGameDifficulty);

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/sudoku/view/SudokuGame.fxml"));
        loader.setController(gameController);

        try {
            Parent newRoot = loader.load();
            App.setScene(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
