package sudoku.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.DirectoryChooser;
import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.dao.interfaces.Dao;
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
    private Button saveGameButton;

    private DifficultyEnum gameDifficulty;
    private int gameBoardSize;
    private SudokuBoard sudokuBoard;

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
        System.out.println("Sudoku Game Controller Initialized");

        saveGameButton.setOnAction(event -> saveSudokuGameToFile());

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

        textField.setOnKeyTyped(event -> {
            String character = event.getCharacter();

            if (textField.getText().length() > 1 || character.length() > 1 || !character.matches("[0-9]")) {
                textField.deletePreviousChar();
                event.consume();
                return;
            }


    

            // Update the board when a key is typed
            updateSudokuBoard();

            // Check if the board is valid
            if (!sudokuBoard.isValidSudoku()) {
                textField.setStyle("-fx-text-fill: red; -fx-alignment: center; -fx-font-weight: bold;");
            } else {
                textField.setStyle("-fx-text-fill: black; -fx-alignment: center; -fx-font-weight: bold;");
            }

            // Check if the game is over
            if (sudokuBoard.checkEndGame()) {
                endGame();
            }
        });

        return textField;
    }

    private void updateSudokuBoard() {
        for (Node node : sudokuBoardGridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                int value = textField.getText().isEmpty() ? 0
                        : Integer.parseInt(textField.getText());

                // Get the row and column index of the text field
                int rowIndex = GridPane.getRowIndex(node);
                int colIndex = GridPane.getColumnIndex(node);
                sudokuBoard.getField(colIndex, rowIndex).setValue(value);
            }
        }
    }

    private void endGame() {
        for (Node node : sudokuBoardGridPane.getChildren()) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.setStyle("-fx-text-fill: green; -fx-alignment: center; -fx-font-weight: bold;");
            }
        }
    }

    private void saveSudokuGameToFile() {
        System.out.println("Try To Save Sudoku Game");

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Where To Save Sudoku Board");

        File selectedDirectory = directoryChooser.showDialog(saveGameButton.getScene().getWindow());

        if (selectedDirectory != null) {
            System.out.println("Directory Path: " + selectedDirectory);

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
            System.out.println("File Name: " + fileName);

            Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory
                    .createSudokuBoardDao(selectedDirectory.toString());

            sudokuBoardDao.write(fileName, this.sudokuBoard);
        }
    }
}
