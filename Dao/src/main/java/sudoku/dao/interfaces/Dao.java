package sudoku.dao.interfaces;

import java.util.List;

public interface Dao<T> extends AutoCloseable {

    T read(String name) throws Exception;

    void write(String name, T obj) throws Exception;
    
    List<String> names() throws Exception;

    @Override
    void close() throws Exception;
}
