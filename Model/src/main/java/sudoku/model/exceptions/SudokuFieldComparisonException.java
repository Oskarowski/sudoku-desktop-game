package sudoku.model.exceptions;

/**
 * Exception thrown when there is an error during comparison of Sudoku fields.
 * This exception is typically used within the Sudoku model components to
 * indicate
 * errors encountered while comparing Sudoku fields.
 *
 * <p>
 * This exception extends {@link LocalizedModelException}, which provides
 * support
 * for localized error messages based on {@link ModelMessageKey}.
 * </p>
 *
 * <p>
 * Examples of comparison errors include unexpected null values or
 * inconsistencies
 * during the comparison of Sudoku fields.
 * </p>
 *
 * @see LocalizedModelException
 * @see ModelMessageKey
 */
public class SudokuFieldComparisonException extends LocalizedModelException {
    /**
     * Constructs a new SudokuFieldComparisonException with a default error message
     * key.
     * The error message will be retrieved based on
     * {@link ModelMessageKey#SUDOKU_FIELD_COMPARISON_ERROR}.
     */
    public SudokuFieldComparisonException() {
        super(ModelMessageKey.SUDOKU_FIELD_COMPARISON_ERROR);
    }

    /**
     * Constructs a new SudokuFieldComparisonException with the specified cause and
     * a default error message key.
     *
     * <p>
     * The error message will be retrieved based on
     * {@link ModelMessageKey#SUDOKU_FIELD_COMPARISON_ERROR}.
     * </p>
     *
     * @param cause the cause of the exception
     */
    public SudokuFieldComparisonException(Throwable cause) {
        super(ModelMessageKey.SUDOKU_FIELD_COMPARISON_ERROR, cause);
    }
}
