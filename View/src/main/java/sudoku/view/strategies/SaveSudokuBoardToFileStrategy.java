package sudoku.view.strategies;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import sudoku.dao.exceptions.SudokuReadException;
import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;

import java.io.File;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveSudokuBoardToFileStrategy extends SaveSudokuBoardStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SaveSudokuBoardToFileStrategy.class);

    @Override
    public void save(SudokuBoard sudokuBoard, Window ownerWindow) {
        logger.info("Try To Save SudokuBoard to File");

        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Choose Where To Save Sudoku Board");

            File selectedDirectory = directoryChooser.showDialog(ownerWindow);

            if (selectedDirectory == null) {
                return;
            }

            logger.info("Directory Path selected to be saved into: " + selectedDirectory);

            // Prompt the user to enter the filename under which to save the SudokuBoard
            Optional<String> result = this.promptForSudokuBoardName(ownerWindow);

            if (result.isEmpty()) {
                return;
            }

            String fileName = result.get();
            logger.info("File name under which to save Sudoku Board: " + fileName);

            Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory
                    .createSudokuBoardDao(selectedDirectory.toString());

            sudokuBoardDao.write(fileName, sudokuBoard);
        } catch (SudokuReadException e) {
            logger.error("Error occurred while saving Sudoku Board to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
