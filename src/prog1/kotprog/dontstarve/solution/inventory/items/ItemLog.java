package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A fa item leírására szolgáló osztály.
 */
public class ItemLog extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemLog(int amount) {
        super(ItemType.LOG, amount);
    }

    /**
     * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
     *
     * @return a korlátot
     */
    @Override
    public int getStackLimit(ItemType itemType) {
        return 15;
    }

}
