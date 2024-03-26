package exercise.model;

import exercise.exceptions.InvalidSudokuException;
import exercise.solver.SudokuSolver;

public class SudokuBoard {
    public static final int BOARD_SIZE = 9;

    private SudokuSolver solver;
    private int[][] board;

    public SudokuBoard(SudokuSolver solver) {
        this.solver = solver;
        board = new int[BOARD_SIZE][BOARD_SIZE];
    }

    public int get(int x, int y) {
        return board[y][x];
    }

    public void set(int x, int y, int value) {
        board[y][x] = value;
    }

    public int[][] getBoard() {
        return board;
    }

    public void solveGame() throws InvalidSudokuException {
        solver.solve(this);
    }

    public boolean isValidSudoku() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            boolean[] row = new boolean[BOARD_SIZE];
            boolean[] col = new boolean[BOARD_SIZE];
            boolean[] block = new boolean[BOARD_SIZE];
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != 0) {
                    if (row[board[i][j] - 1]) {
                        return false;
                    }
                    row[board[i][j] - 1] = true;
                }
                if (board[j][i] != 0) {
                    if (col[board[j][i] - 1]) {
                        return false;
                    }
                    col[board[j][i] - 1] = true;
                }
                int blockRowIndex = 3 * (i / 3);
                int blockColIndex = 3 * (i % 3);
                if (board[blockRowIndex + j / 3][blockColIndex + j % 3] != 0) {
                    if (block[board[blockRowIndex + j / 3][blockColIndex + j % 3] - 1]) {
                        return false;
                    }
                    block[board[blockRowIndex + j / 3][blockColIndex + j % 3] - 1] = true;
                }
            }
        }
        return true;
    }
}
