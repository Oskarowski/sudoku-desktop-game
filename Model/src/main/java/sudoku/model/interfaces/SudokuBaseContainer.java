package sudoku.model.interfaces;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.model.helpers.UniqueChecker;
import sudoku.model.models.SudokuBoard;
import sudoku.model.models.SudokuField;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuBaseContainer implements Verifiable, Serializable, Cloneable {
    private SudokuField[] fields;
    private final Logger logger = LoggerFactory.getLogger(SudokuBaseContainer.class);
    private Locale locale = Locale.getDefault();
    private transient ResourceBundle exceptionsBundle = ResourceBundle
            .getBundle("sudoku.model.bundles.exceptions", locale);

    public SudokuBaseContainer() {
        fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            fields[i] = new SudokuField();
        }
    }

    public SudokuField[] getFields() {
        return fields;
    }

    @Override
    public boolean verify() {
        try {
        return UniqueChecker.checkUnique(fields);
        } catch (IllegalArgumentException e) {
            logger.error(exceptionsBundle.getString("error.Verify"), e);
            return false;
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("fields", fields)
            .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(fields)
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuBaseContainer other = (SudokuBaseContainer) obj;
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            if (getFields()[i].getValue() != other.getFields()[i].getValue()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public SudokuBaseContainer clone() throws CloneNotSupportedException {
        SudokuBaseContainer clone = (SudokuBaseContainer) super.clone();
        clone.fields = new SudokuField[SudokuBoard.BOARD_SIZE];
        
        for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
            clone.fields[i] = fields[i].clone();
        }
        return clone;
        // deep clone of SudokuBaseContainer using super.clone()
        // when using the clone method, cast the result to prefered implementation
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        exceptionsBundle = ResourceBundle.getBundle("sudoku.model.bundles.exceptions", locale);
        for (SudokuField field : fields) {
            field.setLocale(locale);
        }
    }
}
