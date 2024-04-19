package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A gally item leírására szolgáló osztály.
 */
public class ItemTwig extends AbstractItem {
    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param amount az item mennyisége
     */
    public ItemTwig(int amount) {
        super(ItemType.TWIG, amount);
    }

    /**
     * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
     *
     * @return a korlátot
     */
    @Override
    public int getStackLimit(ItemType itemType) {
        return 20;
    }
}
