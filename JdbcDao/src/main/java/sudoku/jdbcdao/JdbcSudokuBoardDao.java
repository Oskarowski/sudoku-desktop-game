package sudoku.jdbcdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;
import sudoku.model.solver.BacktrackingSudokuSolver;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private static final String DB_URL = "jdbc:sqlite:db/sudoku.db";
    private Connection connection;

    public JdbcSudokuBoardDao() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        createTablesIfNotExist();
    }

    public JdbcSudokuBoardDao(String connectionString) throws SQLException {
        connection = DriverManager.getConnection(connectionString);
        createTablesIfNotExist();
    }

    private void createTablesIfNotExist() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS SudokuBoard (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT UNIQUE)");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS SudokuField (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "board_id INTEGER, " +
                    "row INTEGER, " +
                    "column INTEGER, " +
                    "value INTEGER, " +
                    "FOREIGN KEY(board_id) REFERENCES SudokuBoard(id))");
        }
    }

    @Override
    public void write(String name, SudokuBoard obj) {
        try {
            connection.setAutoCommit(false); // Start transaction

            // Insert the board
            try (PreparedStatement insertBoardStmt = connection.prepareStatement(
                    "INSERT INTO SudokuBoard (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {

                insertBoardStmt.setString(1, name);
                insertBoardStmt.executeUpdate();

                int boardId;
                try (ResultSet generatedKeys = insertBoardStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        boardId = generatedKeys.getInt(1);
                    } else {
                        connection.rollback();
                        throw new SQLException("Creating board failed, no ID obtained.");
                    }
                }

                // Insert fields
                try (PreparedStatement insertFieldStmt = connection.prepareStatement(
                        "INSERT INTO SudokuField (board_id, row, column, value) VALUES (?, ?, ?, ?)")) {

                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            insertFieldStmt.setInt(1, boardId);
                            insertFieldStmt.setInt(2, row);
                            insertFieldStmt.setInt(3, col);
                            insertFieldStmt.setInt(4, obj.getField(row, col).getValue());
                            insertFieldStmt.addBatch();
                        }
                    }
                    insertFieldStmt.executeBatch();
                }

                connection.commit(); // Commit transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction on error
                throw e;
            } finally {
                connection.setAutoCommit(true); // Restore auto-commit mode
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SudokuBoard read(String name) {
        SudokuBoard board = null;
        try (PreparedStatement selectBoardStmt = connection.prepareStatement(
                "SELECT id FROM SudokuBoard WHERE name = ?");
                PreparedStatement selectFieldsStmt = connection.prepareStatement(
                        "SELECT row, column, value FROM SudokuField WHERE board_id = ?")) {

            selectBoardStmt.setString(1, name);
            try (ResultSet boardRs = selectBoardStmt.executeQuery()) {
                if (boardRs.next()) {
                    int boardId = boardRs.getInt("id");

                    board = new SudokuBoard(new BacktrackingSudokuSolver());

                    selectFieldsStmt.setInt(1, boardId);
                    try (ResultSet fieldsRs = selectFieldsStmt.executeQuery()) {
                        while (fieldsRs.next()) {
                            int row = fieldsRs.getInt("row");
                            int col = fieldsRs.getInt("column");
                            int value = fieldsRs.getInt("value");
                            board.setField(row, col, value);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return board;
    }

    @Override
    public List<String> names() {
        List<String> names = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT name FROM SudokuBoard")) {

            while (rs.next()) {
                names.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
