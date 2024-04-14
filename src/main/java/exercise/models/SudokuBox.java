package exercise.models;

import exercise.helpers.UniqueChecker;
import exercise.interfaces.Verifiable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SudokuBox implements Verifiable {
    private SudokuField[] fields;

    public SudokuBox() {
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            sb.append(fields[i].getValue());
            if ((i + 1) % 3 == 0) {
                sb.append(StringUtils.LF);
            } else {
                sb.append(StringUtils.SPACE);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuBox other = (SudokuBox) obj;
        return fields.equals(other.fields);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(fields)
            .toHashCode();
    }
}
