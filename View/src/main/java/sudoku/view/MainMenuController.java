package sudoku.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.model.models.SudokuBoard;
import sudoku.dao.factories.SudokuBoardDaoFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    public MainMenuController() {
    }

    private DifficultyEnum selectedGameDifficulty = DifficultyEnum.EASY;

    @FXML
    private Button loadGameButton;

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
        loadGameButton.setOnAction(event -> loadSavedSudokuGameFromFile());
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

    private void loadSavedSudokuGameFromFile() {
        System.out.println("Try To Load Saved Sudoku Game");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Sudoku Board To Be Loaded");

        File selectedFile = fileChooser.showOpenDialog(loadGameButton.getScene().getWindow());

        if (selectedFile != null) {
            String directoryPath = selectedFile.getParent();
            System.out.println("Directory Path: " + directoryPath);

            String fileName = selectedFile.getName();
            System.out.println("File Name: " + fileName);

            try {
                SudokuBoard sudokuBoard = SudokuBoardDaoFactory.createSudokuBoardDao(directoryPath).read(fileName);
                GameController gameController = new GameController(selectedGameDifficulty,
                        sudokuBoard);

                FXMLLoader loader = new FXMLLoader(App.class.getResource("/sudoku/view/SudokuGame.fxml"));
                loader.setController(gameController);

                Parent newRoot = loader.load();
                App.setScene(newRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
