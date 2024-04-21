package sudoku.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;

public class SudokuBoardDaoFactoryTest {

    @Test
    public void testCreateSudokuBoardDao(){
        final Dao<SudokuBoard> dao = SudokuBoardDaoFactory.createSudokuBoardDao("test_dir");
        Assertions.assertNotNull(dao);
    }
}
