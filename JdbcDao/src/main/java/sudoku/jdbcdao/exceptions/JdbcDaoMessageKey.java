package sudoku.jdbcdao.exceptions;

public enum JdbcDaoMessageKey {
    CONNECTION_ERROR("CONNECTION_MESSAGE_EXCEPTION"),
    READ_ERROR("READ_MESSAGE_EXCEPTION"),
    WRITE_ERROR("WRITE_MESSAGE_EXCEPTION"),
    TRANSACTION_ERROR("TRANSACTION_MESSAGE_EXCEPTION");

    private final String key;

    JdbcDaoMessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
