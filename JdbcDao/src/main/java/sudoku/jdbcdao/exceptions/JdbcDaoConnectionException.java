package sudoku.jdbcdao.exceptions;

/**
 * This exception is thrown when there is an error in establishing or managing a
 * database connection in the JdbcDao module.
 */
public class JdbcDaoConnectionException extends LocalizedException {
    /**
     * This exception is thrown when there is an error in establishing or managing a
     * database connection in the JdbcDao module.
     */
    public JdbcDaoConnectionException() {
        super(JdbcDaoMessageKey.CONNECTION_ERROR);
    }

    /**
     * Constructs a new JdbcDaoConnectionException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public JdbcDaoConnectionException(Throwable cause) {
        super(JdbcDaoMessageKey.CONNECTION_ERROR, cause);
    }
}
