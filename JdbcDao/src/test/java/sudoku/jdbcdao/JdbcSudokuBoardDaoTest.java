package sudoku.jdbcdao;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.*;
import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static sudokujdbc.jooq.generated.Tables.SUDOKU_BOARD;
import static sudokujdbc.jooq.generated.Tables.SUDOKU_FIELD;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcSudokuBoardDaoTest {

    private static final String TEST_DB_URL = "jdbc:sqlite:target/test_sudoku.db";
    private Connection connection;
        private DSLContext dsl;


        @BeforeAll
        public void setUp() throws SQLException {
            connection = DriverManager.getConnection(TEST_DB_URL);
            dsl = DSL.using(connection, SQLDialect.SQLITE);
    
            dsl.createTableIfNotExists(SUDOKU_BOARD)
                    .columns(SUDOKU_BOARD.fields())
                    .execute();
    
            dsl.createTableIfNotExists(SUDOKU_FIELD)
                    .columns(SUDOKU_FIELD.fields())
                    .execute();
        }


    @AfterEach
    public void tearDown() throws SQLException {
        dsl.deleteFrom(SUDOKU_FIELD).execute();
        dsl.deleteFrom(SUDOKU_BOARD).execute();
    }

    @AfterAll
    public void cleanUp() throws SQLException {
        dsl.dropTableIfExists(SUDOKU_FIELD).execute();
        dsl.dropTableIfExists(SUDOKU_BOARD).execute();
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