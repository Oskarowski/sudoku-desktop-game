package sudoku.jdbcdao;

import org.junit.jupiter.api.*;
import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcSudokuBoardDaoTest {

    private static final String TEST_DB_URL = "jdbc:sqlite:test_sudoku.db";
    private Connection connection;

    @BeforeAll
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection(TEST_DB_URL);
        try (var statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS SudokuBoard (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT UNIQUE)");
            statement.execute("CREATE TABLE IF NOT EXISTS SudokuField (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "board_id INTEGER, " +
                    "row INTEGER, " +
                    "column INTEGER, " +
                    "value INTEGER, " +
                    "FOREIGN KEY(board_id) REFERENCES SudokuBoard(id))");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("DELETE FROM SudokuField");
            statement.execute("DELETE FROM SudokuBoard");
        }
    }

    @AfterAll
    public void cleanUp() throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute("DROP TABLE SudokuField");
            statement.execute("DROP TABLE SudokuBoard");
        }
        connection.close();
    }

    @Test
    public void testWriteAndRead() {
        try (Dao<SudokuBoard> dao = new JdbcSudokuBoardDao(TEST_DB_URL)) {
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            board.setField(0, 0, 5);
            board.setField(1, 1, 3);
            dao.write("testBoard", board);

            SudokuBoard readBoard = dao.read("testBoard");

            assertEquals(5, readBoard.getField(0, 0).getValue());
            assertEquals(3, readBoard.getField(1, 1).getValue());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testNames() {
        try (Dao<SudokuBoard> dao = new JdbcSudokuBoardDao(TEST_DB_URL)) {
            dao.write("testBoard1", new SudokuBoard(new BacktrackingSudokuSolver()));
            dao.write("testBoard2", new SudokuBoard(new BacktrackingSudokuSolver()));

            var names = dao.names();

            assertTrue(names.contains("testBoard1"));
            assertTrue(names.contains("testBoard2"));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testReadNonExistentBoard() {
        try (Dao<SudokuBoard> dao = new JdbcSudokuBoardDao(TEST_DB_URL)) {
            assertNull(dao.read("nonExistentBoard"));
        } catch (Exception e) {
            fail(e);
        }
    }
}
