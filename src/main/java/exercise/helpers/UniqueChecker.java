package exercise.helpers;

import exercise.models.SudokuField;
import java.util.HashSet;
import java.util.Set;

public class UniqueChecker {
    public static boolean checkUnique(SudokuField[] fields) {
        Set<Integer> values = new HashSet<Integer>();
        for (SudokuField field : fields) {
            int value = field.getValue();
            if (value != 0) {
                if (values.contains(value)) {
                    return false;
                }
                values.add(value);
            }
        }
        return true;
    }
}
