package sudoku.jdbcdao.exceptions;

import java.util.ResourceBundle;

public class LocalizedException extends Exception {
    private static String getLocalizedMessage(String messageKey) {
        ResourceBundle bundle = ResourceBundle.getBundle("JdbcDaoExceptions"); // remember that resource bundles must be
        return bundle.getString(messageKey);
    }

    public LocalizedException(JdbcDaoMessageKey messenger) {
        super(getLocalizedMessage(messenger.getKey()));
    }

    public LocalizedException(JdbcDaoMessageKey messenger, Throwable cause) {
        super(getLocalizedMessage(messenger.getKey()), cause);
    }
}
