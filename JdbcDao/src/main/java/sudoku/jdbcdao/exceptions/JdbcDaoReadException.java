package sudoku.jdbcdao.exceptions;

public class JdbcDaoReadException extends LocalizedException {
    public JdbcDaoReadException() {
        super(JdbcDaoMessageKey.READ_ERROR);
    }

    public JdbcDaoReadException(Throwable cause) {
        super(JdbcDaoMessageKey.READ_ERROR, cause);
    }
}
