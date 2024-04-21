package sudoku.model.models;

import sudoku.model.interfaces.SudokuBaseContainer;

public class SudokuRow extends SudokuBaseContainer {
    private SudokuField[] fields;

    public SudokuRow() {
        fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
    }
}