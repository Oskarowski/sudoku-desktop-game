package exercise.models;

import exercise.helpers.UniqueChecker;
import exercise.interfaces.Verifiable;

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
}
