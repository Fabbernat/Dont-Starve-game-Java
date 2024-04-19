package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Felvehető / kézbe vehető item leírására szolgáló osztály.
 */
public abstract class EquippableItem extends AbstractItem {
    float percent = 100F;

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param type az item típusa
     */
    public EquippableItem(ItemType type) {
        super(type, 1);
    }

    /**
     * Megadja, hogy milyen állapotban van a tárgy.
     *
     * @return a tárgy használatlansága, %-ban (100%: tökéletes állapot)
     */
    public float percentage() {
        return this.percent;
    }
}
