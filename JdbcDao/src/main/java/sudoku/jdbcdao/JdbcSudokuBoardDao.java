package sudoku.jdbcdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

import static sudokujdbc.jooq.generated.Tables.SUDOKU_BOARD;
import static sudokujdbc.jooq.generated.Tables.SUDOKU_FIELD;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String url;
    private Connection connection;
    private DSLContext dsl;

    public JdbcSudokuBoardDao(String url) {
        this.url = url;
    }

    private void connect() throws Exception {
        connection = DriverManager.getConnection(url);
        dsl = DSL.using(connection, SQLDialect.SQLITE);
    }

    @Override
    public void write(String name, SudokuBoard board) {
        try {
            connect();
            connection.setAutoCommit(false); // Start transaction

            var boardId = dsl.insertInto(SUDOKU_BOARD)
                    .columns(SUDOKU_BOARD.NAME)
                    .values(name)
                    .returning(SUDOKU_BOARD.ID)
                    .fetchOne()
                    .getId();

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = board.getField(row, col).getValue();
                    dsl.insertInto(SUDOKU_FIELD)
                            .columns(SUDOKU_FIELD.BOARD_ID, SUDOKU_FIELD.ROW, SUDOKU_FIELD.COLUMN, SUDOKU_FIELD.VALUE)
                            .values(boardId, row, col, value)
                            .execute();
                }
            }

            connection.commit(); // Commit transaction
        } catch (Exception e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public SudokuBoard read(String name) {
        try {
            connect();

            var boardRecord = dsl.selectFrom(SUDOKU_BOARD)
                    .where(SUDOKU_BOARD.NAME.eq(name))
                    .fetchOne();

            if (boardRecord == null) {
                return null;
            }

            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

            dsl.selectFrom(SUDOKU_FIELD)
                    .where(SUDOKU_FIELD.BOARD_ID.eq(boardRecord.getId()))
                    .forEach(record -> {
                        int row = record.getRow();
                        int col = record.getColumn();
                        int value = record.getValue();
                        board.setField(row, col, value);
                    });

            return board;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<String> names() {
        try {
            connect();
            return dsl.select(SUDOKU_BOARD.NAME)
                    .from(SUDOKU_BOARD)
                    .fetch(SUDOKU_BOARD.NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
