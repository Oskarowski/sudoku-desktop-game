package models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sudoku.model.models.SudokuField;
import sudoku.model.models.SudokuRow;

public class SudokuBaseContainerTest {
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

    @Test
    void testEquals_SameObject() {
        SudokuRow row = new SudokuRow();

        assertTrue(row.equals(row));
        assertEquals(row.hashCode(), row.hashCode());
    }

    @Test
    void testEquals_NullObject() {
        SudokuRow row = new SudokuRow();

        assertFalse(row.equals(null));
    }

    @Test
    void testEquals_DifferentClass() {
        SudokuRow row = new SudokuRow();

        assertFalse(row.equals(new Object()));
    }

    @Test
    void testEquals_SameFields() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();
        row1.getFields()[0].setValue(1);
        row2.getFields()[0].setValue(1);

        assertTrue(row1.equals(row2));
        assertEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    void testEquals_DifferentFields() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();
        row1.getFields()[5].setValue(2);
        row2.getFields()[5].setValue(1);

        assertFalse(row1.equals(row2));
        assertNotEquals(row1.hashCode(), row2.hashCode());
    }

    @Test
    void testHashCode_SameFields() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();

        assertTrue(row1.hashCode() == row2.hashCode());
    }

    @Test
    void testHashCode_DifferentFields() {
        SudokuRow row1 = new SudokuRow();
        SudokuRow row2 = new SudokuRow();
        row2.getFields()[0].setValue(1);

        assertFalse(row1.hashCode() == row2.hashCode());
    }

    @Test
    void testToString() {
        SudokuRow row = new SudokuRow();
        assertTrue(row.toString().contains("{0,0,0,0,0,0,0,0,0}"));
    }
}
