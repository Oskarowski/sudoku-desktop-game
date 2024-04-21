package sudoku.model.solver;

import java.io.Serializable;

import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;

public interface SudokuSolver extends Serializable {

    void fillBoard(SudokuBoard board) throws InvalidSudokuException;

    void solve(SudokuBoard board) throws InvalidSudokuException;
}
