package sudoku.view.strategies;

import java.util.Optional;

import javafx.scene.control.TextInputDialog;
import javafx.stage.Window;

import sudoku.model.models.SudokuBoard;

public abstract class SaveSudokuBoardStrategy {
    /**
     * Perform a save operation for the given Sudoku board.
     *
     * @param sudokuBoard the SudokuBoard to be saved
     * @param ownerWindow the owner window of the save operation
     */
    public abstract void save(SudokuBoard sudokuBoard, Window ownerWindow);

    /**
     * Prompts the user to enter a name for the SudokuBoard game.
     *
     * @param ownerWindow the owner window of the prompt
     * @return an Optional containing the entered name, or an empty Optional if the
     *         user cancels the prompt, or don't provide a name
     */
    protected Optional<String> promptForSudokuBoardName(Window ownerWindow) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter Sudoku Board Name");
        dialog.setHeaderText("Please enter the name for the SudokuBoard game:");
        dialog.setContentText("Game name:");

        return dialog.showAndWait();
    }
}
