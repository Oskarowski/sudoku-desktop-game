package sudoku.model.exceptions;

/**
 * Exception thrown when there is an error during the process of filling a
 * Sudoku board.
 * This exception typically indicates issues encountered while populating the
 * board with values.
 *
 * <p>
 * This exception extends {@link LocalizedModelException}, which provides
 * support
 * for localized error messages based on {@link ModelMessageKey}.
 * </p>
 *
 * <p>
 * The default error message is retrieved using the
 * {@link ModelMessageKey#FILLING_BOARD_ERROR}
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
public class FillingBoardSudokuException extends LocalizedModelException {
    /**
     * Constructs a new FillingBoardSudokuException with a default error message
     * key.
     * The error message will be retrieved based on
     * {@link ModelMessageKey#FILLING_BOARD_ERROR}.
     */
    public FillingBoardSudokuException() {
        super(ModelMessageKey.FILLING_BOARD_ERROR);
    }

    /**
     * Constructs a new FillingBoardSudokuException with the specified cause and
     * a default error message key.
     *
     * <p>
     * The error message will be retrieved based on
     * {@link ModelMessageKey#FILLING_BOARD_ERROR}.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public FillingBoardSudokuException(Throwable cause) {
        super(ModelMessageKey.FILLING_BOARD_ERROR, cause);
    }
}
