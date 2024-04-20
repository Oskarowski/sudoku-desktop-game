package exercise.models;

import exercise.interfaces.SudokuBaseContainer;

public class SudokuBox extends SudokuBaseContainer {
    private SudokuField[] fields;

    public SudokuBox() {
        fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
    }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj) {
    //         return true;
    //     }
    //     if (obj == null || getClass() != obj.getClass()) {
    //         return false;
    //     }
    //     SudokuBox other = (SudokuBox) obj;
    //     for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
    //         if (getFields()[i].getValue() != other.getFields()[i].getValue()) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }
}
