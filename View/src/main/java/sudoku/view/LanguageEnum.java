package sudoku.view;

public enum LanguageEnum {
    ENGLISH("English"),
    POLISH("Polski");

    private final String displayName;

    LanguageEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
