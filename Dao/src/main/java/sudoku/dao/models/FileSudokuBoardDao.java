package sudoku.dao.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import sudoku.dao.interfaces.Dao;
import sudoku.model.models.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private String directoryPath;

    public FileSudokuBoardDao(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public SudokuBoard read(String name)  {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directoryPath + "/" + name))) {
            return (SudokuBoard) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    @Override
    public void write(String name, SudokuBoard obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directoryPath + "/" + name))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
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
