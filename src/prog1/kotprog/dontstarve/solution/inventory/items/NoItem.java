package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Üres slot leírására szolgáló osztály.
 */
public class NoItem extends AbstractItem {

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     */
    public NoItem() {
        super(ItemType.NONE, 0);
    }

    /**
     * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
     *
     * @return a korlátot
     */

    @Override
    public int getStackLimit(ItemType itemType) {
        return 0;
    }
}
