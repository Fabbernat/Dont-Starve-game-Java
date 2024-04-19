package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A nyers bogyó item leírására szolgáló osztály.
 */
public class ItemRawBerry extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemRawBerry(int amount) {
        super(ItemType.RAW_BERRY, amount);
    }

    /**
     * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
     *
     * @return a korlátot
     */
    @Override
    public int getStackLimit(ItemType itemType) {
        return 10;
    }
}
