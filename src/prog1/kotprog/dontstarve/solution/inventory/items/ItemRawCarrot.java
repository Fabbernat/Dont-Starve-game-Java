package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A nyers répa item leírására szolgáló osztály.
 */
public class ItemRawCarrot extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemRawCarrot(int amount) {
        super(ItemType.RAW_CARROT, amount);
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
