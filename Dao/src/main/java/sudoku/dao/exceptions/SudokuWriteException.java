package sudoku.dao.exceptions;

public class SudokuWriteException extends RuntimeException {
    public SudokuWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
