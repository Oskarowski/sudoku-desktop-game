package sudoku.view.bundles;

import java.util.ListResourceBundle;

public class EnglishAuthorsResourceBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                { "university", "Lodz University of Technology" },
                { "247026", "Oskar Kacprzak" },
                { "247027", "Wojciech Kapica" },
        };

    }
}
