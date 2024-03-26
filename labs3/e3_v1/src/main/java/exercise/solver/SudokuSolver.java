package exercise.solver;

import exercise.exceptions.InvalidSudokuException;
import exercise.model.SudokuBoard;

public interface SudokuSolver {

    void fillBoard(int[][] board) throws InvalidSudokuException;

    void solve(SudokuBoard board) throws InvalidSudokuException;
}
