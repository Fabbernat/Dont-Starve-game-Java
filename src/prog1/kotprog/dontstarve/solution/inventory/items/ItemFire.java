package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A fire item leírására szolgáló osztály.
 */
public class ItemFire extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     */
    public ItemFire() {
        super(ItemType.FIRE, 1);
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
