package prog1.kotprog.dontstarve.solution.inventory.items;

import java.util.Arrays;

/**
 * Az alapnyersanyagok kiiratására jó.
 */
public class AlapNyersanyagok {
    static String[] alapnyersanyagokField = {"Fa", "Kő", "Gally", "Bogyó", "Répa"};

    /**
     * Az alapnyersanyagok kiiratására jó.
     */
    public String toString() {
        return "AlapNyersanyagok{" +
                "alapnyersanyagok=" + Arrays.toString(alapnyersanyagokField) +
                '}';
    }
}
