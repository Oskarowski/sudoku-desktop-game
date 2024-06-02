package sudoku.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SkinBase;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.model.models.SudokuBoard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuController implements Initializable {
    public MainMenuController() {
    }

    private final Logger logger = LoggerFactory.getLogger(MainMenuController.class);

    private DifficultyEnum selectedGameDifficulty = DifficultyEnum.EASY;

    @FXML
    private ChoiceBox<LanguageEnum> languageChoiceBox;

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
        logger.info("Difficulty set to " + difficulty);
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
        logger.info("Main Menu Controller Initialized");

        easyDifficultyButton.setOnAction(this::handleEasyDifficultyButton);
        mediumDifficultyButton.setOnAction(this::handleMediumDifficultyButton);
        hardDifficultyButton.setOnAction(this::handleHardDifficultyButton);
        startGameButton.setOnAction(this::handleStartDifficultyButton);
        exitGameButton.setOnAction(this::handleExitDifficultyButton);
        loadGameButton.setOnAction(event -> loadSavedSudokuGameFromFile());

        languageChoiceBox.setItems(FXCollections.observableArrayList(LanguageEnum.values()));
        languageChoiceBox.setValue(LanguageEnum.getSelectedLanguage());
        languageChoiceBox.setOnAction(event -> handleChangeLanguage(event));

        displayAuthors();
    }

    public void startGame() {
        logger.info("Start Game");

        GameController gameController = new GameController(selectedGameDifficulty);
        ResourceBundle resourceBundle = LanguageEnum.getResourceBundle();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/sudoku/view/SudokuGame.fxml"), resourceBundle);
        loader.setController(gameController);

        try {
            Parent newRoot = loader.load();
            App.setScene(newRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSavedSudokuGameFromFile() {
        logger.info("Load Saved Sudoku Game");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Sudoku Board To Be Loaded");

        File selectedFile = fileChooser.showOpenDialog(loadGameButton.getScene().getWindow());

        if (selectedFile != null) {
            String directoryPath = selectedFile.getParent();
            logger.info("Directory Path: " + directoryPath);

            String fileName = selectedFile.getName();
            logger.info("File Name: " + fileName);

            try {
                SudokuBoard sudokuBoard = SudokuBoardDaoFactory.createSudokuBoardDao(directoryPath).read(fileName);
                GameController gameController = new GameController(selectedGameDifficulty,
                        sudokuBoard);

                FXMLLoader loader = new FXMLLoader(App.class.getResource("/sudoku/view/SudokuGame.fxml"),
                        LanguageEnum.getResourceBundle());
                loader.setController(gameController);

                Parent newRoot = loader.load();
                App.setScene(newRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleChangeLanguage(ActionEvent event) {
        LanguageEnum selectedLanguage = languageChoiceBox.getValue();

        if (selectedLanguage == null) {
            return;
        }

        LanguageEnum.setSelectedLanguage(selectedLanguage);

        ResourceBundle resourceBundle = LanguageEnum.getResourceBundle();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sudoku/view/MainMenu.fxml"), resourceBundle);

        loader.setController(this);
        Parent root;
        try {
            root = loader.load();
            App.setScene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // <3
        // https://stackoverflow.com/questions/60756577/choicebox-in-javafx-default-title
        Platform.runLater(() -> {
            @SuppressWarnings("unchecked")
            SkinBase<ChoiceBox<String>> skin = (SkinBase<ChoiceBox<String>>) languageChoiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText(selectedLanguage.toString());
                    }
                    return;
                }
            }
        });
    }

    @FXML
    public Label author1Label;
    @FXML
    public Label author2Label;
    @FXML
    public Label universityLabel;

    private void displayAuthors() {
        ResourceBundle authorsResourceBundle = LanguageEnum.getAuthorsResourceBundle();

        String university = authorsResourceBundle.getString("university");
        universityLabel.setText(university);

        String author1 = authorsResourceBundle.getString("247026");
        author1Label.setText(author1);

        String author2 = authorsResourceBundle.getString("247027");
        author2Label.setText(author2);
    }
}