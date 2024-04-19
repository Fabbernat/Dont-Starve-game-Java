package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * Egy általános itemet leíró osztály.
 */
public abstract class AbstractItem {
    /**
     * Az item típusa.
     *
     * @see ItemType
     */
    private ItemType type;
    /**
     * Az item mennyisége.
     */
    private int amount;

    /**
     * Konstruktor, amellyel a tárgy létrehozható.
     *
     * @param type   az item típusa
     * @param amount az item mennyisége
     */
    public AbstractItem(ItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    /**
     * A type gettere.
     *
     * @return a tárgy típusa
     */
    public ItemType getType() {
        return type;
    }

    /**
     * A type settere.
     */
    public void setType(ItemType type) {
        this.type = type;
    }

    /**
     * Az amount gettere.
     *
     * @return a tárgy mennyisége
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Az amount settere.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }


    /**
     * Hasznos műveleti metódus:
     * Inkrementálás.
     */
    public void increment() {
        amount++;
    }

    /**
     * Hasznos műveleti metódus:
     * Dekrementálás.
     */
    public void decrement() {
        amount--;
    }

    /**
     * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
     *
     * @return a korlátot
     */
    public int getStackLimit(ItemType itemType) {
        return switch (itemType) {
            case AXE, PICKAXE, LOG, TORCH, SPEAR -> 1;
            case STONE, RAW_BERRY, RAW_CARROT, COOKED_BERRY, COOKED_CARROT -> 10;
            case TWIG -> 20;
            case FIRE, NONE -> 0;
        };
    }
}
