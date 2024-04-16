package exercise.interfaces;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import exercise.helpers.UniqueChecker;
import exercise.models.SudokuBoard;
import exercise.models.SudokuField;

public class SudokuBaseContainer implements Verifiable{
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
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(fields)
            .toHashCode();
    }
}
