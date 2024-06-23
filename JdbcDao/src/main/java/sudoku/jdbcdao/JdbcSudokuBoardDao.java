package sudoku.jdbcdao;

import static sudokujdbc.jooq.generated.Tables.SUDOKU_BOARDS;
import static sudokujdbc.jooq.generated.Tables.SUDOKU_FIELDS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import sudoku.dao.interfaces.Dao;
import sudoku.jdbcdao.exceptions.JdbcDaoConnectionException;
import sudoku.jdbcdao.exceptions.JdbcDaoReadException;
import sudoku.jdbcdao.exceptions.JdbcDaoTransactionException;
import sudoku.jdbcdao.exceptions.JdbcDaoWriteException;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String url;
    private Connection connection;
    private DSLContext dsl;

    public JdbcSudokuBoardDao(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        this.url = url;
    }

    private void connect() throws JdbcDaoConnectionException {
        try {
            connection = DriverManager.getConnection(url);
            dsl = DSL.using(connection, SQLDialect.SQLITE);
        } catch (SQLException e) {
            throw new JdbcDaoConnectionException(e);
        }
    }

    @Override
    public void write(String name, SudokuBoard board) throws JdbcDaoWriteException, JdbcDaoTransactionException {
        if (name.isEmpty()) {
            throw new JdbcDaoWriteException();
        }

        try {
            connect();
            connection.setAutoCommit(false);

            var boardId = dsl.insertInto(SUDOKU_BOARDS)
                    .columns(SUDOKU_BOARDS.NAME)
                    .values(name)
                    .returning(SUDOKU_BOARDS.ID)
                    .fetchOne()
                    .getId();

            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = board.getField(row, col).getValue();
                    dsl.insertInto(SUDOKU_FIELDS)
                            .columns(SUDOKU_FIELDS.BOARD_ID, SUDOKU_FIELDS.ROW, SUDOKU_FIELDS.COLUMN, SUDOKU_FIELDS.VALUE)
                            .values(boardId, row, col, value)
                            .execute();
                }
            }

            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                throw new JdbcDaoTransactionException(ex);
            }
            throw new JdbcDaoWriteException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public SudokuBoard read(String name) throws JdbcDaoReadException {
        try {
            connect();

            var boardRecord = dsl.selectFrom(SUDOKU_BOARDS)
                    .where(SUDOKU_BOARDS.NAME.eq(name))
                    .fetchOne();

            if (boardRecord == null) {
                return null;
            }

            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

            dsl.selectFrom(SUDOKU_FIELDS)
                    .where(SUDOKU_FIELDS.BOARD_ID.eq(boardRecord.getId()))
                    .forEach(record -> {
                        int row = record.getRow();
                        int col = record.getColumn();
                        int value = record.getValue();
                        board.setField(row, col, value);
                    });

            return board;
        } catch (Exception e) {
            throw new JdbcDaoReadException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<String> names() throws JdbcDaoReadException {
        try {
            connect();
            return dsl.select(SUDOKU_BOARDS.NAME)
                    .from(SUDOKU_BOARDS)
                    .fetch(SUDOKU_BOARDS.NAME);
        } catch (Exception e) {
            throw new JdbcDaoReadException(e);
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection == null) {
            return;
        }

        connection.close();
    }
}
