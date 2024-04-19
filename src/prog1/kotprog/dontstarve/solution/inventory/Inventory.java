package prog1.kotprog.dontstarve.solution.inventory;


import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.EquippableItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemCookedBerry;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemCookedCarrot;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemPickaxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemSpear;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTorch;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.inventory.items.NoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.COOKED_BERRY;
import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.COOKED_CARROT;
import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.FIRE;
import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.NONE;
import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.RAW_BERRY;
import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.RAW_CARROT;
import static prog1.kotprog.dontstarve.solution.inventory.items.ItemType.getStackLimit;

/**
 * Egy karakter inventory-ját megvalósító class.
 */
public class Inventory implements BaseInventory {

    private AbstractItem hand;
    private final List<AbstractItem> itemsOnField = new ArrayList<>();
    private AbstractItem[] inventoryField;
    private int availableSlots = 10;

    /**
     * Ez a konstruktor létrehozza a character inventory-ját, 10 slottal,
     * majd azokat inicializálja üresre.
     */
    public Inventory() {
        inventoryField = new AbstractItem[10];
        for (AbstractItem item : inventoryField) {
            item = new NoItem();
            item.setAmount(0);
            item.setType(NONE);
        }
        hand = new EquippableItem(NONE) {
            /**
             * Az egyes itemek 1 sloton lévő számának megengedett felső korlátját szabja meg.
             * @return a korlátot
             */
            @Override
            public int getStackLimit(ItemType itemType) {
                return 0;
            }
        };
        hand.setAmount(0);
        for (int i = 0; i < 10; i++) {
            inventoryField[i] = new NoItem();
        }
    }

    public List<AbstractItem> getItemsOnField() {
        return itemsOnField;
    }

    /**
     * Ellenőrzi, hogy érvényes indexet (0-9) adott-e meg.
     */
    private boolean wrongIndex(int index) {
        return index > 9 || index < 0;
    }

    /**
     * METÓDUSOK INNEN:
     * El lehet vele érni az inventoryt külső forrásokból is.
     *
     * @return az inventory teljes tartalmát
     */
    public AbstractItem[] getInventoryField() {
        return Arrays.copyOf(inventoryField, inventoryField.length);
    }

    /**
     * Be lehet vele állítani az inventoryt külső forrásokból is.
     */
    public void setInventoryField(AbstractItem[] inventoryField) {
        this.inventoryField = Arrays.copyOf(inventoryField, inventoryField.length);
    }

    /**
     * Megszámolja, hogy hány szabad slot van.
     */
    public int countAvailableSlots() {
        availableSlots = 0;
        for (AbstractItem item : inventoryField) {
            if (item == null || item.getType() == NONE) {
                availableSlots++;
            }
        }
        return availableSlots;
    }

    /**
     * BÍRÓ METÓDUSOK INNEN
     * Egy item hozzáadása az inventory-hoz.<br>
     * Ha a hozzáadni kívánt tárgy halmozható, akkor a meglévő stack-be kerül (ha még fér, különben új stacket kezd),
     * egyébként a legelső új helyre kerül.<br>
     * Ha egy itemből van több megkezdett stack, akkor az inventory-ban hamarabb következőhöz adjuk hozzá
     * (ha esetleg ott nem fér el mind, akkor azt feltöltjük, és utána folytatjuk a többivel).<br>
     * Ha az adott itemből nem fér el mind az inventory-ban, akkor ami elfér azt adjuk hozzá, a többit pedig nem
     * (ebben az esetben a hívó félnek tudnia kell, hogy mennyit nem sikerült hozzáadni).
     *
     * @param paramItem a hozzáadni kívánt tárgy
     * @return igaz, ha sikerült hozzáadni a tárgyat teljes egészében; hamis egyébként
     */
    @Override
    public boolean addItem(AbstractItem paramItem) {
        if (paramItem == null || paramItem.getAmount() < 1 || paramItem.getType() == FIRE) {
            return false;
        }
        //Ha equippableItem a paramItem, akkor könnyű dolgunk van, mert az első átadásnál kilép a ciklusból
        // Először megnézzük, hogy van-e már megkezdett stack vmelyik sloton és hozzáadjuk
        for (AbstractItem item : inventoryField) {
            if (item == null) {
                item = new NoItem();
            }

            // TESTING
            //            System.out.println(item.getType());
            //            System.out.println(getStackLimit(item.getType()));
            //            System.out.println(paramItem.getType());
            //            System.out.println(getStackLimit(paramItem.getType()));
            if ((item.getType() == paramItem.getType() && item.getAmount() < getStackLimit(paramItem.getType())) && adder(paramItem, item)) {
                return true;
            }
        }

        // Ha nincs megkezdett slot, akkor hozzáadjuk egy újhoz
        if (paramItem.getAmount() > 0) {
            for (AbstractItem item : inventoryField) {
                if (item == null || item.getType() == NONE) {
                    if (item == null) {
                        item = new NoItem();
                    }
                    item.setType(paramItem.getType());

                    if (adder(paramItem, item)) {
                        return true;
                    }
                }
            }
        }

        availableSlots = 0;
        for (AbstractItem item : inventoryField) {
            if (item == null || item.getType() == NONE) {
                availableSlots++;
            }
        }

        return paramItem.getAmount() < 1;
    }//95%-os addItem

    private boolean adder(AbstractItem paramItem, AbstractItem item) {
        int tmp = item.getAmount();
        item.setAmount(Math.min(item.getAmount() + paramItem.getAmount(), getStackLimit(paramItem.getType())));
        paramItem.setAmount(paramItem.getAmount() - Math.abs(tmp - item.getAmount()));
        return paramItem.getAmount() == 0;
    }
    /*    @Override
    public boolean addItem(AbstractItem paramItem) {
        if (paramItem == null || paramItem.getAmount() < 1) {
            return false;
        }
        //Ha equippableItem a paramItem, akkor könnyű dolgunk van, mert az első átadásnál kilép a ciklusból
        if (paramItem instanceof EquippableItem) {
            for (AbstractItem item : inventoryField) {
                if (item == null || item.getType() == NONE) {
                    item = paramItem;
                    item.setType(paramItem.getType());
                    item.setAmount(paramItem.getAmount());
                    return true;
                }
            }
        }


        // Először megnézzük, hogy van-e már megkezdett stack vmelyik sloton és hozzáadjuk
        for (AbstractItem item : inventoryField) {
            if (item != null && item.getType() == paramItem.getType()) {
                item = paramItem;
                item.setType(paramItem.getType());
                if (hozzaad(paramItem, item)) {
                    return true;
                }
            }
        }

        // Majd megnézzük, hogy van-e üres hely
        for (AbstractItem item : inventoryField){
            if(item==null || item.getType()==NONE) {
                item = paramItem;
                item.setType(paramItem.getType());
                item.setAmount(paramItem.getAmount());
                if (hozzaad(paramItem, item)) {
                    return true;
                }
            }
        }

        return paramItem.getAmount() < 1;
    }*/

    /*private boolean hozzaad(AbstractItem paramItem, AbstractItem item) {
        while (paramItem.getAmount() > 0 && item.getAmount() < getStackLimit() && item.getAmount() < getStackLimit()) {
            item.increment();
            paramItem.decrement();
            if (paramItem.getAmount() <= 0) {
                paramItem.setAmount(0);
                return true;
            }
        }
        return false;
    }*/

    /**
     * Egy tárgy kidobása az inventory-ból.
     * Hatására a tárgy eltűnik az inventory-ból.
     *
     * @param index a slot indexe, amelyen lévő itemet szeretnénk eldobni
     * @return az eldobott item
     */
    @Override
    public AbstractItem dropItem(int index) {
        if (wrongIndex(index) || inventoryField[index] == null || inventoryField[index].getType() == NONE) {
            return null; // Hibás index
        }
        AbstractItem item = inventoryField[index];
        inventoryField[index] = null; // Set the item in the slot to null
        return item;
    }

    /**
     * Bizonyos mennyiségű, adott típusú item törlése az inventory-ból. A törölt itemek véglegesen eltűnnek.<br>
     * Ha nincs elegendő mennyiség, akkor nem történik semmi.<br>
     * Az item törlése a legkorábban lévő stackből (stackekből) történik, akkor is, ha van másik megkezdett stack.<br>
     *
     * @param type   a törlendő item típusa
     * @param amount a törlendő item mennyisége
     * @return igaz, amennyiben a törlés sikeres volt
     */
    @Override
    public boolean removeItem(ItemType type, int amount) { // 88%-os

        // Több elemű inventory, túl sok item törlése
        if (amount > getStackLimit(type) * 10) {
            amount = getStackLimit(type) * 10;
        }

        // Végigmegyünk az inventory-n, és ha azonos típusút találunk, akkor törlűnk, amíg tudunk
        for (int i = 0; i < inventoryField.length && amount > 0; i++) {
            AbstractItem item = inventoryField[i];

            //ha egy teljes stacket találunk és az amount >= item.getAmount()
            if (item != null && item.getType() == type) {
                if (item.getAmount() >= getStackLimit(item.getType()) && item.getAmount() <= amount) {
                    inventoryField[i] = null;
                    amount -= item.getAmount();
                    continue;
                }


                while (amount > 0 && item.getAmount() > 0) {
                    item.decrement();
                    amount--;
                    if (item.getAmount() == 0) {
                        inventoryField[i] = null;
                        break;
                    }
                }
            } else {
                for (int j = 0; j < inventoryField.length && amount > 0; j++) {
                    if (inventoryField[i] != null && inventoryField[i].getType() == type && item != null) {
                        item.decrement();
                        amount--;
                        if (item.getAmount() == 0) {
                            inventoryField[i] = null;
                            break;
                        }
                    }
                }
            }
        }
        return amount <= 0;
    }

    /**
     * Két item pozíciójának megcserélése az inventory-ban.<br>
     * Csak akkor használható, ha mind a két pozíción már van item.
     *
     * @param index1 a slot indexe, amelyen az első item található
     * @param index2 a slot indexe, amelyen a második item található
     * @return igaz, ha sikerült megcserélni a két tárgyat; hamis egyébként
     */
    @Override
    public boolean swapItems(int index1, int index2) {
        if (wrongIndex(index1) || wrongIndex(index2) || inventoryField[index1] == null || inventoryField[index1].getType() == NONE || inventoryField[index2] ==
                null || inventoryField[index2].getType() == NONE) {
            return false; // Hibás index
        } else {
            return swapper(index1, index2);
        }
    }

    /**
     * Egy item átmozgatása az inventory egy másik pozíciójára.<br>
     * Csak akkor használható, ha az eredeti indexen van tárgy, az új indexen viszont nincs.
     *
     * @param index    a mozgatni kívánt item pozíciója az inventory-ban
     * @param newIndex az új pozíció, ahova mozgatni szeretnénk az itemet
     * @return igaz, ha sikerült a mozgatás; hamis egyébként
     */
    public boolean moveItem(int index, int newIndex) {
        if (wrongIndex(index) || wrongIndex(newIndex) || index == newIndex || inventoryField[index] == null || inventoryField[index].getType() == NONE ||
                (inventoryField[newIndex] != null && inventoryField[newIndex].getType() != NONE)) {
            return false;
        }
        inventoryField[newIndex] = inventoryField[index];
        inventoryField[index] = null;
        return true;
    }

    /**
     * Két azonos típusú tárgy egyesítése.<br>
     * Csak stackelhető tárgyakra használható. Ha a két stack méretének összege a maximális stack méreten belül van,
     * akkor az egyesítés az első pozíción fog megtörténni. Ha nem, akkor az első pozíción lévő stack maximálisra
     * töltődik, a másikon pedig a maradék marad.<br>
     *
     * @param index1 első item pozíciója az inventory-ban
     * @param index2 második item pozíciója az inventory-ban
     * @return igaz, ha sikerült az egyesítés (változott valami a művelet hatására)
     */
    @Override
    public boolean combineItems(int index1, int index2) {
        // Ha helytelen az index, vagy a második üres, vagy a típusok nem egyeznek, akkor ne történjen semmi
        if (wrongIndex(index1) || wrongIndex(index2) || inventoryField[index2] == null || inventoryField[index2].getType() == NONE || (inventoryField[index1] !=
                null && inventoryField[index2] != null && inventoryField[index1].getType() != inventoryField[index2].getType())) {
            return false;
        }
        AbstractItem item1 = inventoryField[index1];
        AbstractItem item2 = inventoryField[index2];

        //későbbi nemnull item combinálása korábbi null-lal (történik változás)
        if ((item1 == null || item1.getType() == NONE) && item2 != null && item2.getType() != NONE) {
            swapper(index1, index2);
            return true;
        } // Innentől kezdve már az item1 elvileg nem lehet null
        // Ha egyéb okok miatt nem történhet meg az egyesítés
        if ((item1 == null && item2 == null) || Objects.requireNonNull(item1).getType() != Objects.requireNonNull(item2).getType() || item1 instanceof
                EquippableItem || item2 instanceof EquippableItem || getStackLimit(item1.getType()) < 2 || getStackLimit(item2.getType()) < 2) {
            return false;
        }

        if (item1.getType() == item2.getType()) {
            // Ha a második teljes stack
            if (item2.getAmount() >= getStackLimit(item2.getType())) {
                // Ha az első is teljes stack
                if (item1.getAmount() >= getStackLimit(item1.getType())) {
                    return false;
                } else {
                    swapItems(index1, index2);
                    return true;
                }
            }
            int sum = item1.getAmount() + item2.getAmount();
            int max = getStackLimit(item1.getType());

            if (sum < max) {
                inventoryField[index1].setAmount(sum);
                inventoryField[index2] = null;
            } else {
                int remainder = sum - max;
                inventoryField[index1].setAmount(max);
                inventoryField[index2].setAmount(Math.min(max, remainder));
            }
            return true;
        }
        return false;
    }

    /**
     * swapitems+combineitems segédmetódus.
     *
     * @param index1 első index
     * @param index2 második index
     * @return sikeres volt-e a csere
     */
    private boolean swapper(int index1, int index2) {
        AbstractItem tmp = getItem(index1);
        inventoryField[index1] = inventoryField[index2];
        if (inventoryField[index1] != null && inventoryField[index2] != null) {
            inventoryField[index1].setType(inventoryField[index2].getType());
        }
        inventoryField[index2] = tmp;
        if (inventoryField[index2] != null) {
            inventoryField[index2].setType(tmp.getType());
        }
        return true;
    }

    /**
     * Egy item felvétele a karakter kezébe.<br>
     * Csak felvehető tárgyra használható. Ilyenkor az adott item a karakter kezébe kerül.
     * Ha a karakternek már tele volt a keze, akkor a kezében lévő item a most felvett item helyére kerül
     * (tehát gyakorlatilag helyet cserélnek).
     *
     * @param index a kézbe venni kívánt tárgy pozíciója az inventory-ban
     * @return igaz, amennyiben az itemet sikerült a kezünkbe venni
     */
    @Override
    public boolean equipItem(int index) {
        if (wrongIndex(index) || inventoryField[index] == null || inventoryField[index].getType() == NONE) {
            return false;
        }
        switch (inventoryField[index].getType()) {
            case SPEAR -> {
                inventoryField[index] = hand;
                hand = new ItemSpear();
            }
            case AXE -> {
                inventoryField[index] = hand;
                hand = new ItemAxe();
            }
            case PICKAXE -> {
                inventoryField[index] = hand;
                hand = new ItemPickaxe();
            }
            case TORCH -> {
                inventoryField[index] = hand;
                hand = new ItemTorch();
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    /**
     * A karakter kezében lévő tárgy inventory-ba helyezése.<br>
     * A karakter kezében lévő item az inventory első szabad pozíciójára kerül.
     * Ha a karakternek üres volt a keze, akkor nem történik semmi.<br>
     * Ha nincs az inventory-ban hely, akkor a levett item a pálya azon mezőjére kerül, ahol a karakter állt.
     *
     * @return a levetett item, amennyiben az nem fért el az inventory-ban; null egyébként
     */
    @Override
    public EquippableItem unequipItem() {
        if (hand == null || hand.getType() == NONE) {
            return null;
        }

        if (hand instanceof EquippableItem) {
            if (countAvailableSlots() > 0) {
                EquippableItem item = (EquippableItem) hand;
                hand = null;
                addItem(item);
                return item;
            } else {
                itemsOnField.add(hand);
                return (EquippableItem) hand;
            }
        } else {
            switch (hand.getType()) {
                case SPEAR -> {
                    hand = null;
                    addItem(new ItemSpear());
                }
                case AXE -> {
                    hand = null;
                    addItem(new ItemAxe());
                }
                case PICKAXE -> {
                    hand = null;
                    addItem(new ItemPickaxe());
                }
                case TORCH -> {
                    hand = null;
                    addItem(new ItemTorch());
                }
                default -> {
                }
            }
        }
        return null;
    }

    /**
     * Egy item megfőzése.<br>
     * Csak nyers étel főzhető meg. Hatására az inventory adott pozíciójáról 1 egységnyi eltűnik.
     *
     * @param index A megfőzni kívánt item pozíciója az inventory-ban
     * @return A megfőzni kívánt item típusa
     */
    @Override
    public ItemType cookItem(int index) {
        if (wrongIndex(index) || inventoryField[index] == null) {
            return null;
        }
        switch (inventoryField[index].getType()) {
            case RAW_BERRY -> {
                inventoryField[index].decrement();
                if (inventoryField[index].getAmount() == 0) {
                    inventoryField[index] = null;
                }
                addItem(new ItemCookedBerry(1));
                return RAW_BERRY;
            }
            case RAW_CARROT -> {
                inventoryField[index].decrement();
                if (inventoryField[index].getAmount() == 0) {
                    inventoryField[index] = null;
                }


                addItem(new ItemCookedCarrot(1));
                return RAW_CARROT;
            }
            default -> {
                return null;
            }

        }
    }

    /**
     * Egy item elfogyasztása.<br>
     * Csak ételek ehetők meg. Hatására az inventory adott pozíciójáról 1 egységnyi eltűnik.
     *
     * @param index A megenni kívánt item pozíciója az inventory-ban
     * @return A megenni kívánt item típusa
     */
    @Override
    public ItemType eatItem(int index) {
        if (wrongIndex(index)) {
            return null;
        }

        AbstractItem item = inventoryField[index];

        if (item == null) {
            return null;
        }

        ItemType type = item.getType();

        if (type == RAW_BERRY || type == RAW_CARROT || type == COOKED_BERRY || type == COOKED_CARROT) {
            item.decrement();
            if (item.getAmount() == 0) {
                inventoryField[index] = null;
            }
            return type;
        }

        return null;
    }

    /**
     * A rendelkezésre álló üres inventory slotok számának lekérdezése.
     *
     * @return az üres inventory slotok száma
     */
    @Override
    public int emptySlots() {
        int db = 0;
        for (AbstractItem item : inventoryField) {
            if (item == null || item.getAmount() == 0) {
                db++;
            }
        }
        return db;
    }

    /**
     * Adott inventory sloton lévő tárgy lekérdezése.<br>
     * Az inventory-ban lévő legelső item indexe 0, a következőé 1, és így tovább.<br>
     * Ha az adott pozíció üres, akkor null.<br>
     *
     * @param index a lekérdezni kívánt pozíció
     * @return az adott sloton lévő tárgy
     */
    @Override
    public AbstractItem getItem(int index) {
        if (wrongIndex(index) || inventoryField[index] == null || inventoryField[index].getType() == NONE) {
            return null;
        } else {
            return inventoryField[index];
        }
    }

    /**
     * Az aktuálisan viselt tárgy lekérdezése.<br>
     * Ha a karakter jelenleg egy tárgyat sem visel, akkor null.<br>
     *
     * @return Az aktuálisan viselt tárgy
     */
    @Override
    public EquippableItem equippedItem() {
        if (hand != null && hand.getType() != NONE) {
            return (EquippableItem) hand;
        }
        return null;
    }

    /**
     * Kiírja a kéz tartalmát, majd az inventory teljes tartalmát.
     *
     * @return az előálított szöveget
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hand: \n");
        if (hand == null) {
            sb.append("NULL").append("\t").append(0).append("\n");
        } else {
            sb.append(hand.getType()).append("\t").append(hand.getAmount());
        }
        sb.append("\nInventory: \n");
        for (AbstractItem item : inventoryField) {
            if (item == null) {
                sb.append("NULL").append("\t").append(0).append("\n");
            } else {
                sb.append(item.getType()).append("\t").append(item.getAmount()).append("\n");
            }
        }
        sb.append("Available slots: ").append(countAvailableSlots());
        return sb.toString();
    }

    /**
     * AZ INVENTORY J-EDIK INDEXŰ ELEMÉRE BERAK amounts[i] mennyiségű items[i]-t.
     *
     * @param j    AZ INVENTORY J-EDIK INDEXŰ ELEMÉRE
     * @param item BERAK amounts[i] mennyiségű items[i]
     */
    public void setSlot(int j, int amount, AbstractItem item, ItemType itemType) {
        item.setAmount(amount);
        item.setType(itemType);
        inventoryField[j] = item;
        inventoryField[j].setType(itemType);
    }
}
