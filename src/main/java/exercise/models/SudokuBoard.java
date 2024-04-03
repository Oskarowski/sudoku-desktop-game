package exercise.models;

import exercise.exceptions.InvalidSudokuException;
import exercise.solver.SudokuSolver;

public class SudokuBoard {
    public static final int BOARD_SIZE = 9;
    public static final int BOX_SIZE = 3;

    private SudokuSolver solver;
    private SudokuRow[] rows;
    private SudokuColumn[] columns;
    private SudokuBox[] boxes;

    public SudokuBoard(SudokuSolver solver) {
        // assign the way solver of sudoku
        this.solver = solver;

        this.rows = new SudokuRow[BOARD_SIZE];
        this.columns = new SudokuColumn[BOARD_SIZE];
        this.boxes = new SudokuBox[BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            rows[i] = new SudokuRow();
            columns[i] = new SudokuColumn();
            boxes[i] = new SudokuBox();
        }

        for (int boxRow = 0; boxRow < SudokuBoard.BOX_SIZE; boxRow++) {
            for (int boxCol = 0; boxCol < SudokuBoard.BOX_SIZE; boxCol++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int rowIndex = boxRow * 3 + i;
                        int colIndex = boxCol * 3 + j;
                        SudokuField field = rows[rowIndex].getFields()[colIndex];
                        boxes[boxRow * 3 + boxCol].getFields()[i * 3 + j] = field;
                    }
                }
            }
        }
    }

    public SudokuField getField(int x, int y) {
        return rows[y].getFields()[x];
    }

    public void setField(int x, int y, int value) {
        SudokuField field = rows[y].getFields()[x];
        field.setValue(value);
        try {
            verifyBoard();
        } catch (InvalidSudokuException e) {
            // TODO make desicion what to do when invalid sudoku
        }
    }

    public SudokuRow getRow(int index) {
        return rows[index];
    }

    public SudokuColumn getColumn(int index) {
        return columns[index];
    }

    public SudokuBox getBox(int index) {
        return boxes[index];
    }

    public void solveGame() throws InvalidSudokuException {
        solver.solve(this);
    }

    public boolean isValidSudoku() {
        for (SudokuRow row : rows) {
            if (!row.verify()) {
                return false;
            }
        }
        for (SudokuColumn column : columns) {
            if (!column.verify()) {
                return false;
            }
        }
        for (SudokuBox box : boxes) {
            if (!box.verify()) {
                return false;
            }
        }
        return true;
    }

    private void verifyBoard() throws InvalidSudokuException {
        if (!isValidSudoku()) {
            throw new InvalidSudokuException("Invalid Sudoku");
        }
    }

}
