package prog1.kotprog.dontstarve.solution.inventory.items;

/**
 * A tárgy típusok enumja.
 */
public enum ItemType {
    /**
     * fejsze.
     */
    AXE,

    /**
     * csákány.
     */
    PICKAXE,

    /**
     * lándzsa.
     */
    SPEAR,

    /**
     * fáklya.
     */
    TORCH,

    /**
     * farönk.
     */
    LOG,

    /**
     * kő.
     */
    STONE,

    /**
     * gally.
     */
    TWIG,

    /**
     * nyers bogyó.
     */
    RAW_BERRY,

    /**
     * nyers répa.
     */
    RAW_CARROT,

    /**
     * főtt bogyó.
     */
    COOKED_BERRY,

    /**
     * főtt répa.
     */
    COOKED_CARROT,

    /**
     * tábortűz (inventory-ban nem tárolható!).
     */
    FIRE,
    /**
     * semmilyen item.
     */
    NONE;

    /**
     * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
     *
     * @return a korlátot
     */
    public static int getStackLimit(ItemType itemType) {
        return switch (itemType) {
            case AXE, PICKAXE, TORCH, SPEAR -> 1;
            case STONE, RAW_BERRY, RAW_CARROT, COOKED_BERRY, COOKED_CARROT -> 10;
            case TWIG -> 20;
            case FIRE, NONE -> 0;
            case LOG -> 15;
        };
    }
}
