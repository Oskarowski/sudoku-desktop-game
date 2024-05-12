package sudoku.view;

import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
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
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
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
        
        JavaBeanIntegerPropertyBuilder integerPropertyBuilder = JavaBeanIntegerPropertyBuilder.create();
        StringConverter stringConverter = new SudokuFieldStringConverter();
        
        TextField textField = new TextField();
        sudokuTextFieldFormatter(textField);

        try {
            JavaBeanIntegerProperty integerProperty = integerPropertyBuilder
            .bean(sudokuField)
            .name("value")
            .build();
            textField.textProperty().bindBidirectional(integerProperty, stringConverter);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        
        textField.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        textField.setPrefSize(textFieldPixelSize, textFieldPixelSize);
        
        return textField;
    }
    private class SudokuFieldStringConverter extends IntegerStringConverter {
        @Override
        public String toString(Integer value) {
            return value == 0 ? "" : value.toString();
        }

        @Override
        public Integer fromString(String string) {
            return string.isEmpty() ? 0 : Integer.parseInt(string);
        }
    }
    
    private void sudokuTextFieldFormatter(TextField textField) {
        TextFormatter formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();

            if ((newText.isEmpty() && change.isDeleted())) {
                return change;
            }
            else if (!newText.matches("[0-9]")) {
                return null;
            }
            return change;
        });
        textField.setTextFormatter(formatter);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {

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
