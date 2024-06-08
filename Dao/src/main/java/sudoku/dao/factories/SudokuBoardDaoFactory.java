package sudoku.dao.factories;

import sudoku.dao.interfaces.Dao;
import sudoku.dao.models.FileSudokuBoardDao;
import sudoku.jdbcdao.JdbcSudokuBoardDao;
import sudoku.model.models.SudokuBoard;

public class SudokuBoardDaoFactory {

    /**
     * The `SudokuBoardDaoFactory` class is a factory class that provides methods
     * for creating instances of `FileSudokuBoardDao`.
     * It shouldn't be possible to create an instance of this class.
     */
    private SudokuBoardDaoFactory() {
    }

    /**
     * Factory method to create Dao class for SudokuBoard.
     *
     * @param dirPath path to directory where files are stored
     * @return instance of Dao class
     */
    public static Dao<SudokuBoard> createSudokuBoardDao(String dirPath) {
        return new FileSudokuBoardDao(dirPath);
    }

    public static Dao<SudokuBoard> createJdbcSudokuBoardDao(){
        // load from .env file
        return new JdbcSudokuBoardDao("jdbc:sqlite:sudoku.db");
    }
}
