package sudoku.model.solver;

import sudoku.model.exceptions.InvalidSudokuException;
import sudoku.model.models.SudokuBoard;

import java.io.Serializable;

public interface SudokuSolver extends Serializable {

    void fillBoard(SudokuBoard board) throws InvalidSudokuException;

    void solve(SudokuBoard board) throws InvalidSudokuException;
}
