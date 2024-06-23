package sudoku.model.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sudoku.model.exceptions.FillingBoardSudokuException;
import sudoku.model.exceptions.ValidationSudokuException;
import sudoku.model.solver.BacktrackingSudokuSolver;
import sudoku.model.solver.SudokuSolver;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SudokuBoard implements PropertyChangeListener, Serializable, Cloneable {
    private final Logger logger = LoggerFactory.getLogger(SudokuBoard.class);

    public static final int BOARD_SIZE = 9;
    public static final int BOX_SIZE = 3;

    private SudokuSolver solver;
    SudokuRow[] rowsArray = new SudokuRow[BOARD_SIZE];
    SudokuColumn[] columnsArray = new SudokuColumn[BOARD_SIZE];
    SudokuBox[] boxesArray = new SudokuBox[BOARD_SIZE];

    List<SudokuRow> rows;
    List<SudokuColumn> columns;
    List<SudokuBox> boxes;

    public SudokuBoard(SudokuSolver solver) {
        // assign the way solver of sudoku
        this.solver = solver;

        this.rows = new ArrayList<>(Arrays.asList(rowsArray));
        this.columns = new ArrayList<>(Arrays.asList(columnsArray));
        this.boxes = new ArrayList<>(Arrays.asList(boxesArray));

        for (int i = 0; i < BOARD_SIZE; i++) {
            rows.set(i, new SudokuRow());
            columns.set(i, new SudokuColumn());
            boxes.set(i, new SudokuBox());
        }

        // Initialize columns based on the fields in rows
        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                columns.get(col).getFields()[row] = rows.get(row).getFields()[col];
            }
        }

        // Initialize boxes based on the fields in rows
        for (int boxRow = 0; boxRow < BOX_SIZE; boxRow++) {
            for (int boxCol = 0; boxCol < BOX_SIZE; boxCol++) {
                for (int i = 0; i < BOX_SIZE; i++) {
                    for (int j = 0; j < BOX_SIZE; j++) {
                        int rowIndex = boxRow * BOX_SIZE + i;
                        int colIndex = boxCol * BOX_SIZE + j;
                        SudokuField field = rows.get(rowIndex).getFields()[colIndex];
                        boxes.get(boxRow * BOX_SIZE + boxCol).getFields()[i * BOX_SIZE + j] = field;
                    }
                }
            }
        }

        this.subscribeToFieldPropertyChanges();
    }

    /**
     * Retrieves the SudokuField at the specified coordinates.
     *
     * @param x The column, x-coordinate of the field.
     * @param y The row, y-coordinate of the field.
     * @return The SudokuField at the specified coordinates.
     */
    public SudokuField getField(int x, int y) {
        return rows.get(y).getFields()[x];
    }

    /**
     * Sets the value of a field in the Sudoku board at the specified coordinates.
     *
     * @param x     the column, x-coordinate of the field
     * @param y     the row, y-coordinate of the field
     * @param value the value to be set in the field
     */
    public void setField(int x, int y, int value) {
        try {
            SudokuField field = rows.get(y).getFields()[x];
            field.setValue(value);
            verifyBoard();
        } catch (ValidationSudokuException e) {
            logger.info("Invalid Sudoku board after setting field value");
        }
    }

    public SudokuRow getRow(int index) {
        return rows.get(index);
    }

    public SudokuColumn getColumn(int index) {
        return columns.get(index);
    }

    public SudokuBox getBox(int index) {
        return boxes.get(index);
    }

    /**
     * Returns the size of the Sudoku board.
     *
     * @return the size of the Sudoku board
     */
    public int getSize() {
        return BOARD_SIZE;
    }

    public void solveGame() throws FillingBoardSudokuException {
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

    private void verifyBoard() throws ValidationSudokuException {
        if (!isValidSudoku()) {
            throw new ValidationSudokuException();
        }
    }

    private boolean checkBoardFilled() {
        for (SudokuRow row : rows) {
            for (SudokuField field : row.getFields()) {
                if (field.getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkEndGame() {
        return checkBoardFilled() && isValidSudoku();
    }

    private void subscribeToFieldPropertyChanges() {
        for (SudokuRow row : rows) {
            for (SudokuField field : row.getFields()) {
                field.addPropertyChangeListener(this);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!(evt.getSource() instanceof SudokuField) || !evt.getPropertyName().equals("value-changed")) {
            return;
        }

        isValidSudoku();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("rows", rows.toString())
            .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuBoard other = (SudokuBoard) obj;
        return new EqualsBuilder()
            .append(rows, other.rows)
            .append(columns, other.columns)
            .append(boxes, other.boxes)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(rows)
            .append(columns)
            .append(boxes)
            .toHashCode();
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard clone = (SudokuBoard) super.clone();
        clone.solver = new BacktrackingSudokuSolver();
        clone.rows = new ArrayList<>(Arrays.asList(rowsArray));
        clone.columns = new ArrayList<>(Arrays.asList(columnsArray));
        clone.boxes = new ArrayList<>(Arrays.asList(boxesArray));
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            clone.rows.set(i, (SudokuRow) rows.get(i).clone());
            clone.columns.set(i, (SudokuColumn) columns.get(i).clone());
            clone.boxes.set(i, (SudokuBox) boxes.get(i).clone());
        }
        return clone;
        // deep clone of SudokuBoard using super.clone()
    }
}