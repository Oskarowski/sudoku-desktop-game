package sudoku.model.exceptions;

/**
 * Enumeration representing keys for localized error messages used in the Sudoku
 * model.
 * Each enum constant corresponds to a specific error message key for
 * localization purposes.
 */
public enum ModelMessageKey {
    VALIDATION_ERROR("VALIDATION_ERROR_MESSAGE_EXCEPTION"),
    SUDOKU_FIELD_COMPARISON_ERROR("SUDOKU_FIELD_COMPARISON_ERROR_MESSAGE_EXCEPTION"),
    INVALID_SUDOKU_ERROR("GENERAL_SUDOKU_ERROR_MESSAGE_EXCEPTION"),
    FILLING_BOARD_ERROR("FILLING_BOARD_ERROR_MESSAGE_EXCEPTION");

    private final String key;

    /**
     * Constructs a ModelMessageKey enum constant with the specified error message
     * key.
     *
     * @param key the key representing the error message in localized resources
     */
    ModelMessageKey(String key) {
        this.key = key;
    }

    /**
     * Retrieves the error message key associated with this enum constant.
     *
     * @return the error message key
     */
    public String getKey() {
        return key;
    }
}
