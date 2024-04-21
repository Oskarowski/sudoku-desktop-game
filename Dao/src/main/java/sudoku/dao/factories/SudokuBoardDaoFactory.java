package sudoku.dao.factories;

import sudoku.dao.interfaces.Dao;
import sudoku.dao.models.FileSudokuBoardDao;
import sudoku.model.models.SudokuBoard;

public class SudokuBoardDaoFactory {

    /**
     * Factory method to create Dao class for SudokuBoard.
     *
     * @param dirPath path to directory where files are stored
     * @return instance of Dao class
     */
    public static Dao<SudokuBoard> createSudokuBoardDao(String dirPath) {
        return new FileSudokuBoardDao(dirPath);
    }
}
