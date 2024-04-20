package helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import exercise.helpers.UniqueChecker;
import exercise.models.SudokuField;

public class UniqueCheckerTest {
    @Test
    void testCheckUnique_AllUnique() {
        SudokuField[] fields = {
                new SudokuField(1), new SudokuField(2), new SudokuField(3),
                new SudokuField(4), new SudokuField(5), new SudokuField(6),
                new SudokuField(7), new SudokuField(8), new SudokuField(9)
        };

        assertTrue(UniqueChecker.checkUnique(fields));
    }

    @Test
    void testCheckUnique_NotUnique() {
        // Create an array with duplicate SudokuFields
        SudokuField[] fields = {
                new SudokuField(1), new SudokuField(2), new SudokuField(3),
                new SudokuField(4), new SudokuField(5), new SudokuField(6),
                new SudokuField(7), new SudokuField(8), new SudokuField(1)
        };

        assertFalse(UniqueChecker.checkUnique(fields));
    }

    @Test
    void testCheckUnique_EmptyFields() {
        SudokuField[] fields = {
                new SudokuField(), new SudokuField(), new SudokuField(),
                new SudokuField(), new SudokuField(), new SudokuField(),
                new SudokuField(), new SudokuField(), new SudokuField()
        };

        assertTrue(UniqueChecker.checkUnique(fields));
    }
}
