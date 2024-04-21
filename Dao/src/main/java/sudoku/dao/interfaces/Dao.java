package sudoku.dao.interfaces;

import java.util.List;

public interface Dao<T> extends AutoCloseable {

    T read(String name);

    void write(String name, T obj);
    
    List<String> names();

    @Override
    void close() throws Exception;
}