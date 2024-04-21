package sudoku.model.models;

import sudoku.model.interfaces.SudokuBaseContainer;

public class SudokuColumn extends SudokuBaseContainer {
    private SudokuField[] fields;

    public SudokuColumn() {
        fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
    }

    public SudokuColumn(SudokuField[] fields) {
        this.fields = fields;
    }
}
