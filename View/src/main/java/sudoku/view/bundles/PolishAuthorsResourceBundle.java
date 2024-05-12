package sudoku.view.bundles;

import java.util.ListResourceBundle;

public class PolishAuthorsResourceBundle extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                { "university", "Politechnika Łódzka" },
                { "247026", "Oskar Kacprzak" },
                { "247027", "Wojciech Kapica" },
        };

    }
}
