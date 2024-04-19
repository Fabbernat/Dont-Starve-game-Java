package prog1.kotprog.dontstarve.solution.level;

import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;

import java.util.Arrays;

/**
 * A pálya egy mezejét megvalósító osztály.
 */
public class Field implements BaseField {
    private final AbstractItem[] itemsField;
    private final int color;
    /*private List<AbstractItem> abstractItems;
    private int treeDurability = 4;
    private int stoneDurability = 5;
    private int twigDurability = 2;*/

    /**
     * A levelen belül.
     * Egy field létrehozására való konstruktor.
     *
     * @param color a mező színe
     * @param itemsField a mezőn található item
     */
    public Field(int color, AbstractItem[] itemsField) {
        this.color = color;
        this.itemsField = itemsField == null ? new AbstractItem[0] : Arrays.copyOf(itemsField, itemsField.length);
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mező járható-e.
     *
     * @return igaz, amennyiben a mező járható; hamis egyébként
     */
    @Override
    public boolean isWalkable() {
        return this.color != MapColors.WATER;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e fa.
     *
     * @return igaz, amennyiben van fa; hamis egyébként
     */
    @Override
    public boolean hasTree() {
        return this.color == MapColors.TREE;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e kő.
     *
     * @return igaz, amennyiben van kő; hamis egyébként
     */
    @Override
    public boolean hasStone() {
        return this.color == MapColors.STONE;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e gally.
     *
     * @return igaz, amennyiben van gally; hamis egyébként
     */
    @Override
    public boolean hasTwig() {
        return this.color == MapColors.TWIG;
    }

    /**
     * Ezen metódus segítségével lekérdezheő, hogy a mezőn van-e bogyó.
     *
     * @return igaz, amennyiben van bogyó; hamis egyébként
     */
    @Override
    public boolean hasBerry() {
        return this.color == MapColors.BERRY;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e répa.
     *
     * @return igaz, amennyiben van répa; hamis egyébként
     */
    @Override
    public boolean hasCarrot() {
        return this.color == MapColors.CARROT;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy a mezőn van-e tűz rakva.
     *
     * @return igaz, amennyiben van tűz; hamis egyébként
     */
    @Override
    public boolean hasFire() {
        for (AbstractItem item : itemsField
        ) {
            if (item.getType() == ItemType.FIRE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy egy mező üres-e.
     *
     * @return igaz, amennyiben üres, hamis egyébként
     */
    @Override
    public boolean isEmpty() {
        return this.color == MapColors.EMPTY;
    }

    /**
     * Létrehozza az item tömböt. Listával jobban megvalósítható
     */
    @Override
    public AbstractItem[] items() {
        return Arrays.copyOf(itemsField, itemsField.length);
    }

    //    /**
    //     * Tree csökkentése.
    //
    //    public void decrementTreeDurability() {
    //        treeDurability--;
    //    }
    //
    //    *
    //     * Stone csökkentése.
    //
    //    public void decrementStoneDurability() {
    //        stoneDurability--;
    //    }
    //
    //    *
    //     * Twig csökkentése.
    //
    //    public void decrementTwigDurability() {
    //        twigDurability--;
    //    }
    //
    //    *
    //     * Tree gettere.
    //     *
    //     * @return Tree tartóssága
    //
    //    public int getTreeDurability() {
    //        return treeDurability;
    //    }
    //
    //    *
    //     * Stone gettere.
    //     *
    //     * @return Stone tartóssága
    //
    //    public int getStoneDurability() {
    //        return stoneDurability;
    //    }
    //
    //    *
    //     * Twig gettere.
    //     *
    //     * @return twig tartóssága
    //
    //    public int getTwigDurability() {
    //        return twigDurability;
    //    }
    //
    //    *
    //     * Az additemből kimaradt itemek ebbe a listába kerülnek.
    //     *
    //     * @param item az additem hívása utáni maradék
    //
    //    public void add(AbstractItem item) {
    //        this.abstractItems.add(item);
    //    }*/
}
