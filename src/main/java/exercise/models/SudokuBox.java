package exercise.models;

import exercise.interfaces.SudokuBaseContainer;
import org.apache.commons.lang3.StringUtils;

public class SudokuBox extends SudokuBaseContainer{
    private SudokuField[] fields;

    public SudokuBox() {
        fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
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
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            if (getFields()[i].getValue() != other.getFields()[i].getValue()) {
                return false;
            }
        }
        return true;
    }
}
