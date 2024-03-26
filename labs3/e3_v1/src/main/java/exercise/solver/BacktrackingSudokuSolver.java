package exercise.solver;

import exercise.exceptions.InvalidSudokuException;
import exercise.model.SudokuBoard;

import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {
    final int sudokuBoardSize = SudokuBoard.BOARD_SIZE;

    @Override
    public void solve(SudokuBoard board) throws InvalidSudokuException {
        fillBoard(board.getBoard());
    }

    @Override
    public void fillBoard(int[][] board) throws InvalidSudokuException {
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

    private boolean fillBoardRecursive(int row, int col, int[][] board) {
        // if we have filled all the rows for the current column, move to the next
        // column, if we have filled all the columns, we are done
        if (row == sudokuBoardSize) {
            row = 0;
            if (++col == sudokuBoardSize) {
                return true;
            }
        }

        // if the current cell is already filled, move to the next cell
        if (board[row][col] != 0) {
            return fillBoardRecursive(row + 1, col, board);
        }

        int[] randomNumbers = generateRandomNumbers();
        for (int num : randomNumbers) {
            if (isValidPlacement(row, col, num, board)) {
                board[row][col] = num;
                /*
                 * if the number is valid, number stays in that cell and move to the next cell,
                 * if based on number in current cell, we can't fill the rest of board, we
                 * backtrack and
                 * try a different number for the current cell
                 */
                if (fillBoardRecursive(row + 1, col, board)) {
                    return true;
                }
                board[row][col] = 0;
            }
        }
        return false;
    }

    private boolean isValidPlacement(int row, int col, int num, int[][] board) {
        // checks if the number is already in the row or column
        for (int i = 0; i < sudokuBoardSize; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // checks if the number is already in the 3x3 box
        final int boxRowStart = row - row % 3;
        final int boxColStart = col - col % 3;
        for (int i = boxRowStart; i < boxRowStart + 3; i++) {
            for (int j = boxColStart; j < boxColStart + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

}
