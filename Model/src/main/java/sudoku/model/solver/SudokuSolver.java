package sudoku.model.solver;

import sudoku.model.exceptions.FillingBoardSudokuException;
import sudoku.model.models.SudokuBoard;

import java.io.Serializable;

public interface SudokuSolver extends Serializable {

    void fillBoard(SudokuBoard board) throws FillingBoardSudokuException;

    void solve(SudokuBoard board) throws FillingBoardSudokuException;
}
