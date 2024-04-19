package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * Az étel elfogyasztása akció leírására szolgáló osztály: egy étel elfogyasztása az inventory-ból.
 */
public class ActionEat extends Action {
    /**
     * A megenni kívánt étel pozíciója az inventory-ban.
     */
    private final int index;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param index a megenni kívánt étel pozíciója az inventory-ban
     */
    public ActionEat(int index) {
        super(ActionType.EAT);
        this.index = index;
    }

    /**
     * Az index gettere.
     *
     * @return a megenni kívánt étel pozíciója az inventory-ban
     */
    public int getIndex() {
        return index;
    }

    //    public void eat(PCharacter character){
    //        BaseInventory inventory = character.getInventory();
    //        ItemType itemType = inventory.eatItem(index);
    //        switch (itemType){
    //            case RAW_BERRY -> {
    //                character.setHp(character.getHp() - 5);
    //                inventory.eatItem(index);
    //                character.setHunger(character.getHunger() + 20);
    //            }
    //            case RAW_CARROT -> {
    //                inventory.eatItem(index);
    //                character.setHp(character.getHp() + 1);
    //                character.setHunger(character.getHunger() + 12);
    //            }
    //            case COOKED_BERRY -> {
    //                inventory.eatItem(index);
    //                character.setHp(character.getHp() + 1);
    //                character.setHunger(character.getHunger() + 10);
    //            }
    //            case COOKED_CARROT -> {
    //                inventory.eatItem(index);
    //                character.setHp(character.getHp() + 3);
    //                character.setHunger(character.getHunger() + 10);
    //            }
    //        }
    //    }
}
