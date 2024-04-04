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

        // Initialize columns based on the fields in rows
        for (int col = 0; col < BOARD_SIZE; col++) {
            SudokuField[] columnFields = new SudokuField[BOARD_SIZE];
            for (int row = 0; row < BOARD_SIZE; row++) {
                columnFields[row] = rows[row].getFields()[col];
            }
            columns[col] = new SudokuColumn(columnFields);
        }

        // Initialize boxes based on the fields in rows
        for (int boxRow = 0; boxRow < BOX_SIZE; boxRow++) {
            for (int boxCol = 0; boxCol < BOX_SIZE; boxCol++) {
                for (int i = 0; i < BOX_SIZE; i++) {
                    for (int j = 0; j < BOX_SIZE; j++) {
                        int rowIndex = boxRow * BOX_SIZE + i;
                        int colIndex = boxCol * BOX_SIZE + j;
                        SudokuField field = rows[rowIndex].getFields()[colIndex];
                        boxes[boxRow * BOX_SIZE + boxCol].getFields()[i * BOX_SIZE + j] = field;
                    }
                }
            }
        }
    }

    /**
     * Retrieves the SudokuField at the specified coordinates.
     *
     * @param x The column, x-coordinate of the field.
     * @param y The row, y-coordinate of the field.
     * @return The SudokuField at the specified coordinates.
     */
    public SudokuField getField(int x, int y) {
        return rows[y].getFields()[x];
    }

    /**
     * Sets the value of a field in the Sudoku board at the specified coordinates.
     *
     * @param x     the column, x-coordinate of the field
     * @param y     the row, y-coordinate of the field
     * @param value the value to be set in the field
     */
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
