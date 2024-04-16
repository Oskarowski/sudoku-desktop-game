package exercise.models;

import exercise.exceptions.InvalidSudokuException;
import exercise.solver.SudokuSolver;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SudokuBoard implements PropertyChangeListener {
    public static final int BOARD_SIZE = 9;
    public static final int BOX_SIZE = 3;

    private SudokuSolver solver;
    List<SudokuRow> rows;
    List<SudokuColumn> columns;
    List<SudokuBox> boxes;

    public SudokuBoard(SudokuSolver solver) {
        // assign the way solver of sudoku
        this.solver = solver;

        this.rows = new ArrayList<>(BOARD_SIZE);
        this.columns = new ArrayList<>(BOARD_SIZE);
        this.boxes = new ArrayList<>(BOARD_SIZE);

        for (int i = 0; i < BOARD_SIZE; i++) {
            rows.add(new SudokuRow());
            columns.add(new SudokuColumn());
            boxes.add(new SudokuBox());
        }

        // Initialize columns based on the fields in rows
        for (int col = 0; col < BOARD_SIZE; col++) {
            SudokuField[] columnFields = new SudokuField[BOARD_SIZE];
            //ArrayList<SudokuField> columnFields = new ArrayList<>(BOARD_SIZE);
            for (int row = 0; row < BOARD_SIZE; row++) {
                columnFields[row] = rows.get(row).getFields()[col];
            }
            //columns[col] = new SudokuColumn(columnFields);
            columns.add(col, new SudokuColumn(columnFields));
        }

        // Initialize boxes based on the fields in rows
        for (int boxRow = 0; boxRow < BOX_SIZE; boxRow++) {
            for (int boxCol = 0; boxCol < BOX_SIZE; boxCol++) {
                for (int i = 0; i < BOX_SIZE; i++) {
                    for (int j = 0; j < BOX_SIZE; j++) {
                        int rowIndex = boxRow * BOX_SIZE + i;
                        int colIndex = boxCol * BOX_SIZE + j;
                        SudokuField field = rows.get(rowIndex).getFields()[colIndex];
                        //boxes[boxRow * BOX_SIZE + boxCol].getFields()[i * BOX_SIZE + j] = field;
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
        SudokuField field = rows.get(y).getFields()[x];
        field.setValue(value);
        try {
            verifyBoard();
        } catch (InvalidSudokuException e) {
            // TODO make desicion what to do when invalid sudoku
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("rows", rows)
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
}
