package sudoku.jdbcdao;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class SchemaInitializer {
    private static final String DB_URL = "jdbc:sqlite:sudoku.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = new String(Files.readAllBytes(Paths.get("src/main/database/schema.sql")));
            stmt.executeUpdate(sql);
            System.out.println("Database schema initialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
