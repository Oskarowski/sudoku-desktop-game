package sudoku.view.strategies;

import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.stage.Window;
import sudoku.dao.factories.SudokuBoardDaoFactory;
import sudoku.dao.interfaces.Dao;
import sudoku.jdbcdao.JdbcSudokuBoardDao;
import sudoku.model.models.SudokuBoard;

public class SaveSudokuBoardToDatabaseStrategy extends SaveSudokuBoardStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SaveSudokuBoardToDatabaseStrategy.class);

    @Override
    public void save(SudokuBoard sudokuBoard, Window ownerWindow) {
        logger.info("Try To Save Sudoku Game to Database");

        try {
            // Prompt the user to enter the filename under which to save the SudokuBoard
            Optional<String> result = this.promptForSudokuBoardName(ownerWindow);

            if (result.isEmpty()) {
                return;
            }

            // Path to the database file in JdbcDaoProject directory
            String jdbcDaoProjectPath = Paths.get("..", "JdbcDao", "sudoku.db").toString();
            String databaseFilePath = Paths.get(jdbcDaoProjectPath).toAbsolutePath().toString();

            String gameName = result.get();
            logger.info("Game Name: " + gameName);

            try (Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.createJdbcSudokuBoardDao(databaseFilePath)) {
                if (!(sudokuBoardDao instanceof JdbcSudokuBoardDao)) {
                    logger.error("sudokuBoardDao is not instance of JdbcSudokuBoardDao");
                    throw new Error("sudokuBoardDao is not instance of JdbcSudokuBoardDao");
                }

                sudokuBoardDao.write(gameName, sudokuBoard);
                logger.info("Sudoku Board saved to DB with name: " + gameName);
            } catch (Exception e) {
                logger.error("Error occurred while saving sudoku board to DB", e);
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Unexpected error occurred E90235", e);
        }
    }
}
