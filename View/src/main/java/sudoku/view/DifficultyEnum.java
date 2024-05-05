package sudoku.view;

import java.util.Random;

import sudoku.model.models.SudokuBoard;
import sudoku.model.models.SudokuField;

public enum DifficultyEnum {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    @SuppressWarnings("unused")
    private int value;

    private DifficultyEnum(int newVal) {
        this.value = newVal;
    }

    /**
     * Clears the Sudoku fields from the Sudoku board based on the difficulty.
     * 
     * @param sudokuBoard the Sudoku board to clear fields from
     */
    public void clearSudokuFieldsFromSudokuBoardBasedOnDifficulty(SudokuBoard sudokuBoard) {
        // TODO - think about the size of the board

        int numberOfFieldsToClear = 0;
        switch (this) {
            case EASY:
                numberOfFieldsToClear = 30;
                break;
            case MEDIUM:
                numberOfFieldsToClear = 50;
                break;
            case HARD:
                numberOfFieldsToClear = 70;
                break;
        }

        Random random = new Random();
        int fieldsCleared = 0;
        // int boardSize = sudokuBoard.getSize();
        int boardSize = 9;

        while (fieldsCleared < numberOfFieldsToClear) {
            int x = random.nextInt(boardSize);
            int y = random.nextInt(boardSize);
            SudokuField field = sudokuBoard.getField(x, y);

            if (field.getValue() != 0) {
                field.setValue(0);
                fieldsCleared++;
            }
        }

    }
}
