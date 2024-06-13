package sudoku.jdbcdao.exceptions;

public class JdbcDaoWriteException extends LocalizedException {
    public JdbcDaoWriteException() {
        super(JdbcDaoMessageKey.WRITE_ERROR);
    }

    public JdbcDaoWriteException(Throwable cause) {
        super(JdbcDaoMessageKey.WRITE_ERROR, cause);
    }
}
