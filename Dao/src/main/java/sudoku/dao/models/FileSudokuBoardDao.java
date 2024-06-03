package sudoku.dao.models;

import sudoku.dao.exceptions.SudokuReadException;
import sudoku.dao.exceptions.SudokuWriteException;
import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private String directoryPath;
    Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);

    public FileSudokuBoardDao(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public SudokuBoard read(String name) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Paths.get(directoryPath, name).toString()))) {
            logger.info("Sudoku board read from file: " + name);
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SudokuReadException("Error occurred while reading sudoku board from file: " + name, e);
        }
    }

    @Override
    public void write(String name, SudokuBoard obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Paths.get(directoryPath, name).toString()))) {
            oos.writeObject(obj);
            logger.info("Sudoku board saved to file: " + name);
        } catch (IOException e) {
            throw new SudokuWriteException("Error occurred while saving sudoku board to file: " + name, e);
        }
    }

    @Override
    public List<String> names() {
        List<String> fileNames = new ArrayList<String>();

        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }

    /**
     * Implementation of AutoCloseable.close() method.
     * 
     * @throws Exception if error occurred when closing resources
     */
    @Override
    public void close() throws Exception {

    }
}
