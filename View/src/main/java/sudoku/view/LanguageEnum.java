package sudoku.view;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LanguageEnum {
    ENGLISH("English"),
    POLISH("Polski");

    private final String displayName;
    private static LanguageEnum selectedLanguage = null;

    LanguageEnum(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static LanguageEnum getSelectedLanguage() {
        if (selectedLanguage != null) {
            return selectedLanguage;
        }

        Locale locale = Locale.getDefault();

        if (locale.getLanguage().equals("pl")) {
            return POLISH;
        } else {
            return ENGLISH;
        }
    }

    public static void setSelectedLanguage(LanguageEnum language) {
        selectedLanguage = language;
    }

    public static ResourceBundle getResourceBundle() {
        LanguageEnum selectedLanguage = getSelectedLanguage();

        if (selectedLanguage != null) {
            switch (selectedLanguage) {
                case ENGLISH:
                    return ResourceBundle.getBundle("sudoku.view.bundles.en_EN");
                case POLISH:
                    return ResourceBundle.getBundle("sudoku.view.bundles.pl_PL");
                default:
                    return null;
            }
        } else {
            // If no language is selected, use the default locale
            Locale locale = Locale.getDefault();
            if (locale.getLanguage().equals("pl")) {
                return ResourceBundle.getBundle("sudoku.view.bundles.pl_PL");
            } else {
                return ResourceBundle.getBundle("sudoku.view.bundles.en_EN");
            }
        }
    }

    public static ResourceBundle getAuthorsResourceBundle() {

        switch (getSelectedLanguage()) {
            case POLISH:
                return ResourceBundle.getBundle("sudoku.view.bundles.PolishAuthorsResourceBundle");
            default:
                return ResourceBundle.getBundle("sudoku.view.bundles.EnglishAuthorsResourceBundle");
        }
    }
}
