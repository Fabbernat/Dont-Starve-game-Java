package prog1.kotprog.dontstarve.solution.character;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.character.actions.ActionNone;
import prog1.kotprog.dontstarve.solution.inventory.BaseInventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.NoItem;
import prog1.kotprog.dontstarve.solution.utility.Position;

/**
 * Egy egyszerű karakter leírására szolgáló osztály.
 * Létrehoz egy új karaktert a megadott névvel.
 */
public class PCharacter implements BaseCharacter {
    CharType charType;


    private Action lastAction;
    private final Position currentPosition;
    private BaseInventory inventory;
    private float speed = 1f;
    private float hunger = 100f;
    private float hp = 100f;
    private final AbstractItem hand;
    private final String name;
    private final int damage;
    private boolean alive;

    /**
     * Általános karakter létrhozása.
     *
     * @param name a karakter neve
     */
    public PCharacter(Position currentPosition, String name, int damage, boolean player) {
        this.lastAction = new ActionNone();
        this.currentPosition = currentPosition;
        this.inventory = GameManager.getInstance().randomInventory();
        this.hand = new NoItem();
        this.name = name;
        this.damage = damage;
        this.alive = true;
        if (player) {
            charType = CharType.PLAYER;
        } else if (GameManager.getInstance().getGameMode() == GameManager.GameMode.TUTORIAL) {
            charType = CharType.TUTORIAL_AI;
        } else {
            charType = CharType.NORMAL_AI;
        }
    }

    /**
     * Visszaadja a karakter jelenlegi sebességét, ami a HP- és éhség-értékektől függ. Ez egy lekérdező metódus.
     *
     * @return speed
     */
    @Override
    public float getSpeed() {
        speed = 1;
        // hp multiplier
        setSpeed();

        return speed;
    }

    /**
     * A játékos sebessége.
     */
    private void setSpeed() {
        float speed = 1;
        // hp multiplier
        if (hp < 50) {
            speed *= 0.9;
            if (hp < 30) {
                speed *= 0.75 / 0.9;
                if (hp < 10) {
                    speed *= 0.6 / 0.75;
                }
            }
        }

        // hunger multiplier
        if (hp < 50) {
            speed *= 0.9;
            if (hp < 20) {
                speed *= 0.8 / 0.9;
                if (hp == 0) {
                    speed *= 0.5 / 0.8;
                }
            }
        }
        this.speed = speed;
    }

    /**
     * Visszaadja a karakter jelenlegi éhségét százalékban kifejezve. Ha az éhség értéke 0, akkor az életerő csökken.<br>
     * Ez egy lekérdező metódus.
     *
     * @return éhség
     */
    @Override
    public float getHunger() {
        hunger = Math.max(hunger, 0f);
        if (hunger == 0f) {
            hp -= 5f;
        }
        return Math.min(hunger, 100f);
    }

    /**
     * Beállítja a karakter éhségét az adott értékre, de maximum 100 lehet. Ha az éhség értéke 0, akkor az életerő csökken.
     * Ez egy beállító metódus.
     *
     * @param hunger éhség
     */
    public void setHunger(float hunger) {
        hunger = Math.max(hunger, 0f);
        if (hunger == 0f) {
            hp -= 5f;
        }
        this.hunger = Math.min(hunger, 100f);
    }

    /**
     * Visszaadja a karakter jelenlegi életerejét százalékban kifejezve. Ha az életerő értéke 0, akkor a karakter halott. Ez egy lekérdező metódus.
     *
     * @return A hp-t
     */
    @Override
    public float getHp() {
        hp = Math.max(hp, 0);
        if (hp == 0) {
            alive = false;
        }
        return Math.min(hp, 100f);
    }

    /**
     * Setter a karakter életerejéhez.
     *
     * @param hp a játékos életereje
     */
    public void setHp(float hp) {
        hp = Math.max(hp, 0);
        if (hp == 0) {
            alive = false;
        }
        this.hp = Math.min(100f, hp);
    }

    /**
     * Visszaadja, hogy a karakter él-e még.
     *
     * @return él-e
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * Getter az inventory-hoz.
     *
     * @return inventory-t
     */
    @Override
    public BaseInventory getInventory() {
        return inventory;
    }

    /**
     * Setter az inventory-hoz.
     *
     * @param inventory egy teljes inventory
     */
    public void setInventory(BaseInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Getter a karakter pozíciójához.
     *
     * @return az aktuális pozícióját
     */
    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Getter az utolsó akcióhoz.
     *
     * @return az utolsó akciót
     */
    @Override
    public Action getLastAction() {
        return lastAction;
    }

    /**
     * Új értéket állít be az utolsó akciónak.
     *
     * @param lastAction az utolsó akció
     */
    public void setLastAction(Action lastAction) {
        this.lastAction = lastAction;
    }

    /**
     * Getter a karakter nevéhez.
     *
     * @return a karakter nevét
     */
    @Override
    public String getName() {
        return name;
    }

    //    /**
    //     * Getter a karakter által kézben tartott tárgyhoz.
    //     *
    //     * @return a kéz tartalma
    //     */
    //    public AbstractItem getHand() {
    //        return hand;
    //    }
    //
    //    /**
    //     * Setter a karakter által kézben tartott tárgyhoz.
    //     *
    //     * @param hand a kéz tartalma
    //     */
    //    public void setHand(AbstractItem hand) {
    //        this.hand = hand;
    //    }

    /**
     * : Getter a karakter sebzéséhez.
     *
     * @return a karakter sebzését
     */
    public float getDamage() {
        return damage;
    }

    //    /**
    //     * Táblázat a sebzéshez.
    //     * Eszköz   Sebzés   Mellékhatás
    //     * Lándzsa   19      lándzsa élettartama 1-gyel csökken
    //     * Fejsze    8       fejsze élettartama 1-gyel csökken
    //     * Csákány   8       csákány élettartama 1-gyel csökken
    //     * Fáklya    6       -
    //     * Kéz       4       -
    //     */
    //    public void setDamage() {
    //        if (hand != null) {
    //            if (hand.getType().equals(ItemType.SPEAR)) {
    //                damage = 19;
    //            } else if (hand.getType().equals(ItemType.AXE) || hand.getType().equals(ItemType.PICKAXE)) {
    //                damage = 8;
    //            } else if (hand.getType().equals(ItemType.TORCH)) {
    //                damage = 6;
    //            }
    //        } else damage = 4;
    //    }

    /**
     * Játékos/normal bot / tutorial bot.
     *
     * @return Játékos/normal bot / tutorial bot.
     */
    public CharType getCharType() {
        return charType;
    }

    @Override
    public String toString() {
        return "PCharacter{" + "lastAction=" + lastAction + ", currentPosition=" + currentPosition + ", speed=" + speed + ", hunger=" + hunger + ", hp=" + hp +
                "," + " inventory:\n" + inventory + "\n, hand=" + hand + ", name='" + name + '\'' + ", damage=" + damage + ", alive=" + alive + '}';
    }

    public enum CharType {
        PLAYER, NORMAL_AI, TUTORIAL_AI
    }
}
