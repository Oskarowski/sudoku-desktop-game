package sudoku.model.exceptions;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a localized exception base class that can be thrown by the Model
 * module.
 * This exception is used to provide localized error messages to the user.
 */
public class LocalizedModelException extends RuntimeException {
    /**
     * Retrieves the localized message for the given message key.
     *
     * @param messageKey the key of the message to retrieve
     * @return the localized message associated with the key
     */
    private static String getLocalizedMessage(String messageKey) {
        ResourceBundle bundle = ResourceBundle.getBundle("ModelExceptions", Locale.getDefault());
        return bundle.getString(messageKey);
    }

    /**
     * Constructs a new LocalizedModelException with the specified messenger.
     *
     * @param messenger the ModelMessageKey representing the localized message
     *                  key.
     */
    public LocalizedModelException(ModelMessageKey messenger) {
        super(getLocalizedMessage(messenger.getKey()));
    }

    /**
     * Constructs a new LocalizedModelException with the specified messenger and
     * cause.
     *
     * @param messenger the ModelMessageKey representing the localized message
     *                  key.
     * @param cause     the Throwable that caused this exception to be thrown.
     */
    public LocalizedModelException(ModelMessageKey messenger, Throwable cause) {
        super(getLocalizedMessage(messenger.getKey()), cause);
    }
}
