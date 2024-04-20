package interfaces;

public interface Dao extends AutoCloseable {
    @Override
    void close() throws Exception;

    Object read(Object obj) throws Exception;
    void write(Object obj) throws Exception;
    String[] names() throws Exception;
}
