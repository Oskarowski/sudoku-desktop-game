package sudoku.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.DirectoryChooser;
import javafx.util.converter.IntegerStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.dao.exceptions.SudokuReadException;
import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.dao.interfaces.Dao;
import sudoku.jdbcdao.JdbcSudokuBoardDao;
import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.models.SudokuField;
import sudoku.model.solver.BacktrackingSudokuSolver;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Button saveGameToFileButton;
    @FXML
    private Button saveGameToDatabaseButton;

    private DifficultyEnum gameDifficulty;
    private int gameBoardSize;
    private SudokuBoard sudokuBoard;
    private static Logger logger = LoggerFactory.getLogger(GameController.class);

    @FXML
    public GridPane sudokuBoardGridPane;

    public GameController(DifficultyEnum initialGameDifficulty, int initialGameBoardSize, SudokuBoard sudokuBoard) {
        this.gameDifficulty = initialGameDifficulty;
        this.gameBoardSize = initialGameBoardSize;
        this.sudokuBoard = sudokuBoard;
    }

    public GameController(DifficultyEnum initialGameDifficulty) {
        this(initialGameDifficulty, 9, null);
    }

    public GameController(DifficultyEnum initialGameDifficulty, SudokuBoard sudokuBoard) {
        this(initialGameDifficulty, 9, sudokuBoard);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Sudoku Game Controller Initialized");

        saveGameToFileButton.setOnAction(event -> saveSudokuGameToFile());
        saveGameToDatabaseButton.setOnAction(event -> saveSudokuGameToDatabase());

        if (sudokuBoard == null) {
            sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

            try {
                sudokuBoard.solveGame();
            } catch (InvalidSudokuException e) {
                e.printStackTrace();
            }

            gameDifficulty.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(sudokuBoard);
        }

        initSudokuBoardGridPane();
    }

    /**
     * Initializes the Sudoku board grid pane.
     * This method sets up the row and columns for the grid pane based on the game
     * board size.
     */
    private void initSudokuBoardGridPane() {
        double textFieldPixelSize = 40;

        for (int i = 0; i < gameBoardSize; i++) {
            RowConstraints row = new RowConstraints(textFieldPixelSize, textFieldPixelSize,
                    textFieldPixelSize, Priority.ALWAYS, VPos.CENTER, true);
            ColumnConstraints column = new ColumnConstraints(textFieldPixelSize, textFieldPixelSize,
                    textFieldPixelSize, Priority.ALWAYS, HPos.CENTER, true);
            sudokuBoardGridPane.getRowConstraints().add(row);
            sudokuBoardGridPane.getColumnConstraints().add(column);

            for (int j = 0; j < gameBoardSize; j++) {
                SudokuField sudokuField = sudokuBoard.getField(j, i);

                TextField textField = createTextField(sudokuField, textFieldPixelSize);

                sudokuBoardGridPane.add(textField, j, i);
            }
        }
    }

    private TextField createTextField(SudokuField sudokuField, double textFieldPixelSize) {
        // Don't show 0s in the text fields (initially)
        String initialValue = sudokuField.getValue() == 0 ? "" : String.valueOf(sudokuField.getValue());
        TextField textField = new TextField(initialValue);
        textField.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        textField.setPrefSize(textFieldPixelSize, textFieldPixelSize);

        // Create a TextFormatter with a custom converter
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter() {
            @Override
            public String toString(Integer value) {
                return value != null && value != 0 ? super.toString(value) : "";
            }
        }, sudokuField.getValue());

        textFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && (newValue < 1 || newValue > 9)) {
                textFormatter.setValue(oldValue); // Revert to old value
            }
        });

        // Bind the TextFormatter to the TextField
        textField.setTextFormatter(textFormatter);

        // Update SudokuField value when TextField text changes
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]?")) {
                textField.setText(oldValue);
            } else {
                logger.info("New Value: " + newValue);
                sudokuField.setValue(newValue.isEmpty() ? 0 : Integer.parseInt(newValue));

                if (sudokuBoard.checkEndGame()) {
                    endGame();
                }
            }
        });

        return textField;
    }

    private void endGame() {
        logger.info("Game Finished!");
        boolean gameFinished = sudokuBoard.checkEndGame();
        for (Node node : sudokuBoardGridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.setStyle("-fx-text-fill: green; -fx-alignment: center; -fx-font-weight: bold;");
                textField.setDisable(gameFinished);

            }
        }
    }

    private void saveSudokuGameToFile() {
        logger.info("Try To Save Sudoku Game");

        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose Where To Save Sudoku Board");

            File selectedDirectory = directoryChooser.showDialog(saveGameToFileButton.getScene().getWindow());

            if (selectedDirectory != null) {
                logger.info("Directory Path: " + selectedDirectory);

                // Prompt the user to enter the filename under which to save the Sudoku board
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Enter File Name");
                dialog.setHeaderText("Please enter the name for the Sudoku board file:");
                dialog.setContentText("File Name:");

                Optional<String> result = dialog.showAndWait();

                if (result.isEmpty()) {
                    return;
                }

                String fileName = result.get();
                logger.info("File Name: " + fileName);

                Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory
                        .createSudokuBoardDao(selectedDirectory.toString());

                sudokuBoardDao.write(fileName, this.sudokuBoard);
            }
        } catch (SudokuReadException e) {
            logger.error("Error occurred while saving sudoku board to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveSudokuGameToDatabase() {
        logger.info("Try To Save Sudoku Game In Database");
    
        try {
            // Prompt the user to enter the name under which to save the Sudoku board
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Game Name");
            dialog.setHeaderText("Please enter the name for the Sudoku board game:");
            dialog.setContentText("Game Name:");
    
            Optional<String> result = dialog.showAndWait();
    
            if (result.isEmpty()) {
                return;
            }
    
            String gameName = result.get();
            logger.info("Game Name: " + gameName);
    
            try (Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.createJdbcSudokuBoardDao()) {
                if(!(sudokuBoardDao instanceof JdbcSudokuBoardDao)){
                    logger.error("sudokuBoardDao is not instance of JdbcSudokuBoardDao");
                    throw new Error("sudokuBoardDao is not instance of JdbcSudokuBoardDao");
                }

                sudokuBoardDao.write(gameName, this.sudokuBoard);
                logger.info("Sudoku board saved to database with name: " + gameName);
            } catch (Exception e) {
                logger.error("Error occurred while saving sudoku board to database", e);
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unexpected error occurred E90235", e);
        }
    }
}
