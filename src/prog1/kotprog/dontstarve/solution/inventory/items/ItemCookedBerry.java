package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A főtt bogyó item leírására szolgáló osztály.
 */
public class ItemCookedBerry extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemCookedBerry(int amount) {
        super(ItemType.COOKED_BERRY, amount);
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
