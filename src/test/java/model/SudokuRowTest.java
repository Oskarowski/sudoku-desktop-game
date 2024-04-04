package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exercise.models.SudokuField;
import exercise.models.SudokuRow;

public class SudokuRowTest {
    @Test
    void testVerify_AllUnique() {
        SudokuRow row = new SudokuRow();
        SudokuField[] fields = row.getFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setValue(i + 1);
        }

        assertTrue(row.verify());
    }

    @Test
    void testVerify_NotUnique() {
        SudokuRow row = new SudokuRow();
        SudokuField[] fields = row.getFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setValue(1);
        }

        assertFalse(row.verify());
    }

    @Test
    void testVerify_EmptyRow() {
        SudokuRow row = new SudokuRow();

        assertTrue(row.verify());
    }
}
