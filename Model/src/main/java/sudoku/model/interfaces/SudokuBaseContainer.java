package sudoku.model.interfaces;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sudoku.model.helpers.UniqueChecker;
import sudoku.model.models.SudokuBoard;
import sudoku.model.models.SudokuField;

import java.io.Serializable;

public class SudokuBaseContainer implements Verifiable, Serializable {
    private SudokuField[] fields;

    public SudokuBaseContainer() {
        fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
    }

    public SudokuField[] getFields() {
        return fields;
    }

    @Override
    public boolean verify() {
        return UniqueChecker.checkUnique(fields);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fields", fields)
            .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(fields)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuBaseContainer other = (SudokuBaseContainer) obj;
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            if (getFields()[i].getValue() != other.getFields()[i].getValue()) {
                return false;
            }
        }
        return true;
    }
}
