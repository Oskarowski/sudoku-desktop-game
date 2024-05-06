package sudoku.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.models.SudokuField;
import sudoku.model.solver.BacktrackingSudokuSolver;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private DifficultyEnum gameDifficulty;
    private int gameBoardSize;
    private SudokuBoard sudokuBoard;

    @FXML
    public GridPane sudokuBoardGridPane;

    public GameController(DifficultyEnum initialGameDifficulty, int initialGameBoardSize) {
        this.gameDifficulty = initialGameDifficulty;
        this.gameBoardSize = initialGameBoardSize;
    }

    public GameController(DifficultyEnum initialGameDifficulty) {
        this(initialGameDifficulty, 9);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Sudoku Game Controller Initialized");
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        // TODO - remove this solving from here
        try {
            sudokuBoard.solveGame();
        } catch (InvalidSudokuException e) {
            e.printStackTrace();
        }

        gameDifficulty.clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(sudokuBoard);

        initSudokuBoardGridPane();
    }


    /**
     * Initializes the Sudoku board grid pane.
     * This method sets up the row and columns for the grid pane based on the game board size.
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
            // Limit the text field to only one character
            if (textField.getText().length() > 1) {
                textField.deletePreviousChar();
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
}
