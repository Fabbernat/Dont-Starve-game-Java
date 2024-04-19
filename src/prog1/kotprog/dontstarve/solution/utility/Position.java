package prog1.kotprog.dontstarve.solution.utility;

/**
 * Egy 2 dimenziós (x, y) koordinátát leíró osztály.
 */
public class Position {
    /**
     * vízszintes (x) irányú koordináta.
     */
    private float x;

    /**
     * függőleges (y) irányú koordináta.
     */
    private float y;

    /**
     * Két paraméteres konstruktor, amely segítségével egy új pozíciót lehet létrehozni.
     *
     * @param x vízszintes (x) irányú koordináta
     * @param y függőleges (y) irányú koordináta
     */
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Az aktuális koordinátához legközelebbi, csak egész értékű komponensekből álló koordináta kiszámítása.<br>
     * A kerekítés a matematika szabályainak megfelelően történik.
     *
     * @return a kiszámolt pozíció
     */
    public Position getNearestWholePosition() {
        int roundedX = Math.round(x);
        int roundedY = Math.round(y);

        if (Math.abs(x - roundedX) < 0.0001f) {
            roundedX = (int) x;
        }

        if (Math.abs(y - roundedY) < 0.0001f) {
            roundedY = (int) y;
        }

        return new Position(roundedX, roundedY);
    }

    /**
     * x koordináta gettere.
     *
     * @return x koordináta
     */
    public float getX() {
        return x;
    }

    /**
     * x koordináta settere.
     *
     * @param x új x érték
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * y koordináta gettere.
     *
     * @return y koordináta
     */
    public float getY() {
        return y;
    }

    /**
     * y koordináta settere.
     *
     * @param y új y érték
     */
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
