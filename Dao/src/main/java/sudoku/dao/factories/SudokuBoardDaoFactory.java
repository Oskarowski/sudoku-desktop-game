package sudoku.dao.factories;

import sudoku.dao.interfaces.Dao;
import sudoku.dao.models.FileSudokuBoardDao;
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

    /**
     * Factory method to create a JDBC SudokuBoardDao instance if JDBC is available.
     *
     * @return the JDBC SudokuBoardDao instance
     * @param dbUrl the URL of the database
     * @throws UnsupportedOperationException if the JdbcSudokuBoardDao reflection is
     *                                       not available
     */
    @SuppressWarnings("unchecked")
    public static Dao<SudokuBoard> createJdbcSudokuBoardDao(String dbUrl) {
        try {
            Class<?> jdbcDaoClass = Class.forName("sudoku.jdbcdao.JdbcSudokuBoardDao");
            var jdbcDao = jdbcDaoClass.getConstructor(String.class).newInstance("jdbc:sqlite:" + dbUrl);
            return (Dao<SudokuBoard>) jdbcDao;

        } catch (Exception e) {
            throw new UnsupportedOperationException("JdbcSudokuBoardDao reflection is not available ", e);
        }
    }
}
