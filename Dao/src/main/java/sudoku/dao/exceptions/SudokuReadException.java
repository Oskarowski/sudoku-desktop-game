package sudoku.dao.exceptions;

public class SudokuReadException extends RuntimeException {
    public SudokuReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
