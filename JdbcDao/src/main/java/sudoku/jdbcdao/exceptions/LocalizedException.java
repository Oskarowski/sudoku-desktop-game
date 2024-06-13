package sudoku.jdbcdao.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a localized exception base class that can be thrown by the JdbcDao
 * module.
 * This exception is used to provide localized error messages to the user.
 */
public class LocalizedException extends Exception {
    /**
     * Retrieves the localized message for the given message key.
     *
     * @param messageKey the key of the message to retrieve
     * @return the localized message associated with the key
     */
    private static String getLocalizedMessage(String messageKey) {
        ResourceBundle bundle = ResourceBundle.getBundle("JdbcDaoExceptions", Locale.getDefault());
        return bundle.getString(messageKey);
    }

    /**
     * Constructs a new LocalizedException with the specified messenger.
     *
     * @param messenger the JdbcDaoMessageKey representing the localized message
     *                  key.
     */
    public LocalizedException(JdbcDaoMessageKey messenger) {
        super(getLocalizedMessage(messenger.getKey()));
    }

    /**
     * Constructs a new LocalizedException with the specified messenger and cause.
     *
     * @param messenger the JdbcDaoMessageKey representing the localized message
     *                  key.
     * @param cause     the Throwable that caused this exception to be thrown.
     */
    public LocalizedException(JdbcDaoMessageKey messenger, Throwable cause) {
        super(getLocalizedMessage(messenger.getKey()), cause);
    }
}
