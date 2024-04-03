package exercise.solver;

import exercise.exceptions.InvalidSudokuException;
import exercise.models.SudokuBoard;

public interface SudokuSolver {

    void fillBoard(SudokuBoard board) throws InvalidSudokuException;

    void solve(SudokuBoard board) throws InvalidSudokuException;
}
