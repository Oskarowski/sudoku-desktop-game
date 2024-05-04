package sudoku.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

public class SudokuController implements Initializable{
    // Żeby odpalić to coś, trzeba wejść w ViewProject -> plugins -> javafx -> run
    // Trzeba zmienić pom czy coś aby można było to odpalić w SudokuGameProject?

    @FXML
    private ListView<Integer> selectableNumbersListView;
    Integer[] selectableNumbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Integer currentNumber;

    @FXML
    private Label numberLabel;
    
    @FXML
    private GridPane sudokuGridPane;
    private int[][] sudokuArray = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectableNumbersListView.getItems().addAll(selectableNumbers);

        // change the number size etc
        selectableNumbersListView.setCellFactory(new Callback<ListView<Integer>, ListCell<Integer>>() {
            @Override
            public ListCell<Integer> call(ListView<Integer> param) {
                return new ListCell<Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            if (item == 0) {
                                setText("Clear Cell");
                                setStyle("-fx-font-weight: bold;"); // Make text bold
                                setAlignment(Pos.CENTER); // Center align the text
                                setPrefHeight(50); // Set your desired height here
                            } else {
                                setText(item.toString());
                                setStyle("-fx-font-weight: bold;"); // Make text bold
                                setAlignment(Pos.CENTER); // Center align the text
                                setPrefHeight(50); // Set your desired height here
                            }
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        // add listeners for the numbers
        selectableNumbersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                currentNumber = selectableNumbersListView.getSelectionModel().getSelectedItem();
                if (currentNumber != 0) {
                    numberLabel.setText("Current Number: " + currentNumber);
                } else {
                    numberLabel.setText("Clear Cell");
                }
                selectableNumbersListView.getSelectionModel().clearSelection();
            }
        });

        // add labels to the grid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Label label = new Label(Integer.toString(sudokuArray[i][j]));
                label.setAlignment(Pos.CENTER); // Center align the label's content
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Ensure label occupies entire cell
                GridPane.setHalignment(label, javafx.geometry.HPos.CENTER); // Center align the label within its cell
                GridPane.setValignment(label, javafx.geometry.VPos.CENTER); // Center align the label vertically within its cell
                sudokuGridPane.add(label, j, i);
            }
        }
    }
    // end of initialize

    @FXML
    private void handleExit() {
        Platform.exit();
    }

    @FXML
    private void handleEasyGame() throws CloneNotSupportedException {
        try {
            board = board.getGameBoard(0);
            updateGridWithBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMediumGame() throws CloneNotSupportedException {
        try {
            board = board.getGameBoard(1);
            updateGridWithBoard();
        } catch (InvalidSudokuException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleHardGame() throws CloneNotSupportedException {
        try {
            board = board.getGameBoard(2);
            updateGridWithBoard();
        } catch (InvalidSudokuException e) {
            e.printStackTrace();
        }
    }

    private void updateGridWithBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Label label = getLabelAt(i, j);
                label.setText(Integer.toString(board.getField(i, j).getValue()));
            }
        }
    }

    private Label getLabelAt(int row, int column) {
        for (int i = 0; i < sudokuGridPane.getChildren().size(); i++) {
            if (GridPane.getRowIndex(sudokuGridPane.getChildren().get(i)) == row
                    && GridPane.getColumnIndex(sudokuGridPane.getChildren().get(i)) == column
                    && sudokuGridPane.getChildren().get(i) instanceof Label) {
                return (Label) sudokuGridPane.getChildren().get(i);
            }
        }
        return null;
    }
}