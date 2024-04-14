package exercise.models;

import exercise.helpers.UniqueChecker;
import exercise.interfaces.Verifiable;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SudokuRow implements Verifiable {
    private SudokuField[] fields;

    public SudokuRow() {
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuRow other = (SudokuRow) obj;
        return fields.equals(other.fields);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(fields)
            .toHashCode();
    }
}