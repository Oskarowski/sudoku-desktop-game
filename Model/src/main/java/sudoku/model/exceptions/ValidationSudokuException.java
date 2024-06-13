package sudoku.model.exceptions;

/**
 * Exception thrown when there is an error during validation of a Sudoku board.
 * This exception is typically used within the Sudoku model components to
 * indicate
 * validation errors.
 *
 * <p>
 * This exception extends {@link LocalizedModelException}, which provides
 * support
 * for localized error messages based on {@link ModelMessageKey}.
 * </p>
 *
 * <p>
 * Examples of validation errors include incorrect Sudoku board configurations
 * or
 * failed validations during board operations.
 * </p>
 *
 * @see LocalizedModelException
 * @see ModelMessageKey
 */
public class ValidationSudokuException extends LocalizedModelException {
    /**
     * Constructs a new ValidationSudokuException with a default error message key.
     * The error message will be retrieved based on
     * {@link ModelMessageKey#VALIDATION_ERROR}.
     */
    public ValidationSudokuException() {
        super(ModelMessageKey.VALIDATION_ERROR);
    }

    /**
     * Constructs a new ValidationSudokuException with the specified cause and
     * a default error message key.
     *
     * <p>
     * The error message will be retrieved based on
     * {@link ModelMessageKey#VALIDATION_ERROR}.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public ValidationSudokuException(Throwable cause) {
        super(ModelMessageKey.VALIDATION_ERROR, cause);
    }
}
