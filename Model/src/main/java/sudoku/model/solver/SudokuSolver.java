package sudoku.model.solver;

import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;

public interface SudokuSolver {

    void fillBoard(SudokuBoard board) throws InvalidSudokuException;

    void solve(SudokuBoard board) throws InvalidSudokuException;
}
