package exercise.models;

import exercise.helpers.UniqueChecker;
import exercise.interfaces.Verifiable;

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
}