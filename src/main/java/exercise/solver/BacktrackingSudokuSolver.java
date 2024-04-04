package exercise.solver;

import exercise.exceptions.InvalidSudokuException;
import exercise.models.SudokuBoard;

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {
    final int sudokuBoardSize = SudokuBoard.BOARD_SIZE;

    @Override
    public void solve(SudokuBoard board) throws InvalidSudokuException {
        fillBoard(board);
    }

    @Override
    public void fillBoard(SudokuBoard board) throws InvalidSudokuException {
        if (!fillBoardRecursive(0, 0, board)) {
            throw new InvalidSudokuException("Not able to fill the Sudoku board");
        }
    }

    private int[] generateRandomNumbers() {
        int[] nums = new int[sudokuBoardSize];
        for (int i = 0; i < sudokuBoardSize; i++) {
            nums[i] = i + 1;
        }

        Random random = new Random();
        // shuffle the array
        for (int i = sudokuBoardSize - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
        return nums;
    }

    private boolean fillBoardRecursive(int row, int col, SudokuBoard board) {
        // if we have filled all the rows for the current column, move to the next
        // column, if we have filled all the columns, we are done
        if (row == sudokuBoardSize) {
            row = 0;
            if (++col == sudokuBoardSize) {
                return true;
            }
        }

        // if the current cell is already filled, move to the next cell
        if (board.getField(col, row).getValue() != 0) {
            return fillBoardRecursive(row + 1, col, board);
        }

        int[] randomNumbers = generateRandomNumbers();
        for (int num : randomNumbers) {
            if (isValidPlacement(row, col, num, board)) {
                board.getField(col, row).setValue(num);
                /*
                 * if the number is valid, number stays in that cell and move to the next cell,
                 * if based on number in current cell, we can't fill the rest of board, we
                 * backtrack and
                 * try a different number for the current cell
                 */
                if (fillBoardRecursive(row + 1, col, board)) {
                    return true;
                }
                board.getField(col, row).setValue(0);
            }
        }
        return false;
    }

    private boolean isValidPlacement(int row, int col, int num, SudokuBoard board) {
        // when dividing on ints, we truncate the result
        final int boxIndex = (col / 3) + (row / 3) * 3;

        int previousValue = board.getField(col, row).getValue();
        board.getField(col, row).setValue(num);

        boolean validPlacement = board.getRow(row).verify()
                && board.getColumn(col).verify()
                && board.getBox(boxIndex).verify();

        // bring back the value
        board.getField(col, row).setValue(previousValue);

        return validPlacement;
    }

}
