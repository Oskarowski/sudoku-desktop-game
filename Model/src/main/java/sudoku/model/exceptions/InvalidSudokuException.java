package sudoku.model.exceptions;

/**
 * Exception thrown when a trouble within the Sudoku board is determined.
 * This exception typically indicates that the Sudoku board does not adhere
 * to the rules of Sudoku, such as having duplicate numbers in rows, columns,
 * or boxes.
 *
 * <p>
 * This exception extends {@link LocalizedModelException}, which provides
 * support
 * for localized error messages based on {@link ModelMessageKey}.
 * </p>
 *
 * <p>
 * The default error message is retrieved using the
 * {@link ModelMessageKey#INVALID_SUDOKU_ERROR}
 * key from the resource bundle.
 * </p>
 *
 * <p>
 * This exception can optionally wrap another throwable as its cause, providing
 * additional context or information about the error.
 * </p>
 *
 * @see LocalizedModelException
 * @see ModelMessageKey
 */
public class InvalidSudokuException extends LocalizedModelException {
    /**
     * Constructs a new InvalidSudokuException with a default error message key.
     * The error message will be retrieved based on
     * {@link ModelMessageKey#INVALID_SUDOKU_ERROR}.
     */
    public InvalidSudokuException() {
        super(ModelMessageKey.INVALID_SUDOKU_ERROR);
    }

    /**
     * Constructs a new InvalidSudokuException with the specified cause and
     * a default error message key.
     *
     * <p>
     * The error message will be retrieved based on
     * {@link ModelMessageKey#INVALID_SUDOKU_ERROR}.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public InvalidSudokuException(Throwable cause) {
        super(ModelMessageKey.INVALID_SUDOKU_ERROR, cause);
    }
}
