package sudoku.jdbcdao.exceptions;

public class JdbcDaoTransactionException extends LocalizedException{
    public JdbcDaoTransactionException() {
        super(JdbcDaoMessageKey.TRANSACTION_ERROR);
    }

    public JdbcDaoTransactionException(Throwable cause) {
        super(JdbcDaoMessageKey.TRANSACTION_ERROR, cause);
    }
}
