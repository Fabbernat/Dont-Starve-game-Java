package prog1.kotprog.dontstarve.solution.character.actions;

/**
 * Az item begyűjtése akció leírására szolgáló osztály: egy item begyűjtése az aktuális mezőről.
 */
public class ActionCollectItem extends Action {

    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionCollectItem() {
        super(ActionType.COLLECT_ITEM);
    }
    /*
    /**
     * Egyelőre ki kell kommentelni, mert sokat levon érte a bíró.
     *
     * @param character játékos
     * @param field     mező
     * @return sikeres volt-e a begyűjtés


    public boolean collect(PCharacter character, Field field) {

        // NULLcheckek
        if (character == null || character.getInventory() == null || field == null) {
            return false;
        }

         A karakter akt pozija
        Position pos = character.getCurrentPosition();

        //Ha TREE van a fielden és AXE van a karakter kezében, akkor lemegy
        AbstractItem equippedItem = character.getInventory().equippedItem();
        if (field.hasTree() && equippedItem != null && equippedItem.getType() == ItemType.AXE) {
            if (field.getTreeDurability() > 1) {
                field.decrementTreeDurability();
            } else {
                AbstractItem log = new ItemLog(2);
                character.getInventory().addItem(log);
                if (log.getAmount() > 0) {
                    field.add(log);
                }
            }
            return true;

            //STONE
        } else if (field.hasStone() && equippedItem != null && equippedItem.getType() == ItemType.PICKAXE) {
            if (field.getStoneDurability() > 1) {
                field.decrementStoneDurability();
            } else {
                AbstractItem stone = new ItemStone(3);
                character.getInventory().addItem(stone);
                if (stone.getAmount() > 0) {
                    field.add(stone);
                }

            }
            return true;

            // TWIG
        } else if (field.hasTwig()) {
            if (field.getTwigDurability() > 1) {
                field.decrementTwigDurability();

            } else {
                AbstractItem twig = new ItemTwig(1);
                character.getInventory().addItem(twig);
                if (twig.getAmount() > 0) {
                    field.add(twig);
                }
            }
            return true;

            // RAWBERRY
        } else if (field.hasBerry()) {

            AbstractItem berry = new ItemRawBerry(1);
            character.getInventory().addItem(berry);
            if (berry.getAmount() > 0) {
                field.add(berry);
            }
            return true;


            // RAWCARROT
        } else if (field.hasCarrot()) {

            AbstractItem carrot = new ItemRawCarrot(1);
            character.getInventory().addItem(carrot);
            if (carrot.getAmount() > 0) {
                field.add(carrot);
            }
            return true;


        }

        return false;
    }
    */
}
