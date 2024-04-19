package prog1.kotprog.dontstarve.solution;

import prog1.kotprog.dontstarve.solution.character.BaseCharacter;
import prog1.kotprog.dontstarve.solution.character.PCharacter;
import prog1.kotprog.dontstarve.solution.character.actions.Action;
import prog1.kotprog.dontstarve.solution.character.actions.ActionAttack;
import prog1.kotprog.dontstarve.solution.character.actions.ActionCollectItem;
import prog1.kotprog.dontstarve.solution.character.actions.ActionCombineItems;
import prog1.kotprog.dontstarve.solution.character.actions.ActionCook;
import prog1.kotprog.dontstarve.solution.character.actions.ActionCraft;
import prog1.kotprog.dontstarve.solution.character.actions.ActionDropItem;
import prog1.kotprog.dontstarve.solution.character.actions.ActionEat;
import prog1.kotprog.dontstarve.solution.character.actions.ActionEquip;
import prog1.kotprog.dontstarve.solution.character.actions.ActionInteract;
import prog1.kotprog.dontstarve.solution.character.actions.ActionMoveItem;
import prog1.kotprog.dontstarve.solution.character.actions.ActionNone;
import prog1.kotprog.dontstarve.solution.character.actions.ActionStep;
import prog1.kotprog.dontstarve.solution.character.actions.ActionStepAndAttack;
import prog1.kotprog.dontstarve.solution.character.actions.ActionSwapItems;
import prog1.kotprog.dontstarve.solution.character.actions.ActionUnequip;
import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemLog;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawBerry;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawCarrot;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemStone;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTwig;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemType;
import prog1.kotprog.dontstarve.solution.level.BaseField;
import prog1.kotprog.dontstarve.solution.level.Field;
import prog1.kotprog.dontstarve.solution.level.Level;
import prog1.kotprog.dontstarve.solution.utility.Direction;
import prog1.kotprog.dontstarve.solution.utility.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A játék működéséért felelős osztály.<br>
 * Az osztály a singleton tervezési mintát valósítja meg.
 */
public final class GameManager {
    /**
     * Az osztályból létrehozott egyetlen példány (nem lehet final).
     */
    //    @SuppressWarnings("Field 'instance' may be 'final'")
    //    köszi intelliJ, hogy mindig átírod final-ra xD a kurv
    private static GameManager instance = new GameManager();
    /**
     * A játékosokat tartalmazó lista (botokkal együtt).
     */
    private static final List<PCharacter> PLAYERS = new ArrayList<>();
    private final List<Action> actions = Arrays.asList(
            new ActionAttack(), new ActionCollectItem(), new ActionCombineItems(0, 1), new ActionCook(0),
            new ActionCraft(ItemType.NONE), new ActionDropItem(0), new ActionEat(0), new ActionEquip(0), new ActionInteract(),
            new ActionMoveItem(0, 1), new ActionNone(), new ActionStep(Direction.RIGHT), new ActionStepAndAttack(Direction.RIGHT),
            new ActionSwapItems(0, 1), new ActionUnequip()
    );
    /**
     * A játékosok kezdőpozíciói.
     */
    private final List<Position> positions = new ArrayList<>();
    /**
     * Random objektum, amit a játék során használni lehet.
     */
    private final Random random = new Random();
    /**
     * Egyéb adattagok.
     */
    private GameMode gameMode = GameMode.NORMAL;
    private GameState gameState = GameState.BEFORESTART;
    private Level level;
    private boolean isLevelLoaded;
    private Field[][] fields;
    private int timePassed;
    private boolean human;
    private boolean firstToJoin = true;
    private PCharacter thePlayer;
    private int threshhold = 50;


    /**
     * Az osztály privát konstruktora.
     */
    private GameManager() {
    }

    /**
     * Az osztályból létrehozott példány elérésére szolgáló metódus.
     *
     * @return az osztályból létrehozott példány
     */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * Joincharacter teszteléséhez.
     *
     * @param args nem kell
     */
    public static void main(String[] args) {
        GameManager.getInstance().loadLevel(new Level("C:\\IdeaProjects\\_723 pont\\src\\level00.png"));
        Position position = GameManager.getInstance().joinCharacter("Sanyi", true);
        System.out.println(PLAYERS);
        System.out.println(PLAYERS.size());
        Position position2 = GameManager.getInstance().joinCharacter("", true);
        System.out.println(PLAYERS);
        System.out.println(PLAYERS.size());
        Position position3 = GameManager.getInstance().joinCharacter(null, true);
        System.out.println(PLAYERS);
        System.out.println(PLAYERS.size());
        Position position4 = GameManager.getInstance().joinCharacter("BOT", false);
        System.out.println(PLAYERS);
        System.out.println(PLAYERS.size());
        Position position5 = GameManager.getInstance().joinCharacter("AI", false);
        System.out.println(PLAYERS);

        System.out.println(position);
        System.out.println(position2);
        System.out.println(position3);
        System.out.println(position4);
        System.out.println(position5);
        Inventory inventory = GameManager.getInstance().randomInventory();
        System.out.println(inventory);
        GameManager.getInstance().tick(new ActionCollectItem());
        System.out.println(PLAYERS.size());
    }

    /**
     * A játékosok listájának gettere.
     *
     * @return a játékosok listája
     */
    public List<PCharacter> getPlayers() {
        return PLAYERS;
    }

    /**
     * A gameMode gettere.
     *
     * @return gameMode-ot
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Megszámolja, hogy hány bot van a játékban.
     *
     * @return a botok száma
     */
    private int nOfAI() {
        int n = 0;
        for (PCharacter player : PLAYERS) {
            if (player.getCharType() == PCharacter.CharType.NORMAL_AI || player.getCharType() == PCharacter.CharType.TUTORIAL_AI) {
                n++;
            }
        }
        return n;
    }

    /**
     * A létrehozott random objektum elérésére szolgáló metódus.
     *
     * @return a létrehozott random objektum
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Egy karakter becsatlakozása a játékba.<br>
     * A becsatlakozásnak számos feltétele van:
     * <ul>
     *     <li>A pálya már be lett töltve</li>
     *     <li>A játék még nem kezdődött el</li>
     *     <li>Csak egyetlen emberi játékos lehet, a többi karaktert a gép irányítja</li>
     *     <li>A névnek egyedinek kell lennie</li>
     * </ul>
     *
     * @param name   a csatlakozni kívánt karakter neve
     * @param player igaz, ha emberi játékosról van szó; hamis egyébként
     * @return a karakter pozíciója a pályán, vagy (Integer.MAX_VALUE, Integer.MAX_VALUE) ha nem sikerült hozzáadni
     */
    public Position joinCharacter(String name, boolean player) {
        if (name == null || PLAYERS.stream().anyMatch(p -> p != null && name.equals(p.getName()))) {
            return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        if (isLevelLoaded && gameState != GameState.RUNNING) {
            Position pos = generateValidPosition();
            if (pos.equals(new Position(Integer.MAX_VALUE, Integer.MAX_VALUE))) {
                return pos;
            }

            PCharacter character;
            if (player) {
                if (human) {
                    PLAYERS.removeIf(pCharacter -> pCharacter.getCharType() == PCharacter.CharType.PLAYER);
                }
                character = new PCharacter(pos, name, 4, true);
                human = true;
                thePlayer = character;
            } else {
                character = new PCharacter(pos, name, gameMode == GameMode.TUTORIAL ? 0 : 4, true);
            }
            PLAYERS.add(character);
            return pos;
        }

        return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private Position generateValidPosition() {
        while (threshhold > 0) {
            for (int x = 0; x < level.getWidth(); x++) {
                for (int y = 0; y < level.getHeight(); y++) {
                    Position newPos = new Position(x, y);
                    int color = level.getColor(Math.round(newPos.getX()), Math.round(newPos.getY()));
                    Field field = new Field(color, new AbstractItem[0]);

                    boolean validPosition;
                    if (firstToJoin && field.isEmpty()) {
                        firstToJoin = false;
                        positions.add(newPos);
                        return newPos;
                    }

                    validPosition = positions.stream()
                            .filter(Objects::nonNull)
                            .mapToDouble(p -> Math.sqrt(Math.pow(p.getX() - newPos.getX(), 2) + Math.pow(p.getY() - newPos.getY(), 2)))
                            .anyMatch(distance -> distance >= threshhold);

                    if (field.isEmpty() && validPosition) {
                        return newPos;
                    }
                }
            }
            threshhold -= 5;
        }
        return new Position(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Egy adott nevű karakter lekérésére szolgáló metódus.<br>
     *
     * @param name A lekérdezni kívánt karakter neve
     * @return Az adott nevű karakter objektum, vagy null, ha már a karakter meghalt vagy nem is létezett
     */
    public BaseCharacter getCharacter(String name) {
        if (name == null) {
            return null;
        }
        for (PCharacter player : PLAYERS) {
            if (player != null && player.getName() != null && name.equals(player.getName()) && player.getHp() > 0) {
                return player;
            }
        }
        return null;
    }

    /**
     * Ezen metódus segítségével lekérdezhető, hogy hány karakter van még életben.
     *
     * @return Az életben lévő karakterek száma
     */
    public int remainingCharacters() {
        int alive = 0;
        for (PCharacter player : PLAYERS) {
            if (player != null && player.isAlive()) {
                alive++;
            }
        }
        return alive;
    }

    /**
     * Ezen metódus segítségével történhet meg a pálya betöltése.<br>
     * A pálya betöltésének azelőtt kell megtörténnie, hogy akár 1 karakter is csatlakozott volna a játékhoz.<br>
     * A pálya egyetlen alkalommal tölthető be, később nem módosítható.
     *
     * @param level a fájlból betöltött pálya
     */
    public void loadLevel(Level level) {
        if (!isLevelLoaded && level != null) {
            gameState = GameState.BEFORESTART;
            int width = level.getWidth();
            int height = level.getHeight();
            if (width > 0 && height > 0) {
                fields = new Field[width][height];
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {

                        fields[x][y] = new Field(level.getColor(x, y), level.getItems());
                    }
                }
                this.level = level;
                isLevelLoaded = true;
            }
        }
    }

    /**
     * A pálya egy adott pozícióján lévő mező lekérdezésére szolgáló metódus.
     *
     * @param x a vízszintes (x) irányú koordináta
     * @param y a függőleges (y) irányú koordináta
     * @return az adott koordinátán lévő mező
     */
    public BaseField getField(int x, int y) {
        if (!isLevelLoaded) {
            loadLevel(level);
        }
        if (level.getWidth() == 0 || level.getHeight() == 0) {
            return null;
        }
        return fields[x][y];
    }

    /**
     * A játék megkezdésére szolgáló metódus.<br>
     * A játék csak akkor kezdhető el, ha legalább 2 karakter már a pályán van,
     * és közülük pontosan az egyik az emberi játékos által irányított karakter.
     *
     * @return igaz, ha sikerült elkezdeni a játékot; hamis egyébként
     */
    public boolean startGame() {
        if (gameState == GameState.BEFORESTART && PLAYERS.size() > 1 && human && nOfAI() > 0) {
            gameState=GameState.RUNNING;
            return true;
        }
        return false;
    }

    /**
     * Ez a metódus jelzi, hogy 1 időegység eltelt.<br>
     * A metódus először lekezeli a felhasználói inputot, majd a gépi ellenfelek cselekvését végzi el,
     * végül eltelik egy időegység.<br>
     * Csak akkor csinál bármit is, ha a játék már elkezdődött, de még nem fejeződött be.
     *
     * @param action az emberi játékos által végrehajtani kívánt akció
     */
    public void tick(Action action) {
        if (gameState==GameState.RUNNING || action == null || PLAYERS == null || PLAYERS.isEmpty() || thePlayer == null) {
            return;
        }
        thePlayer.setLastAction(action);
        while (!isGameEnded() && timePassed < 100) {
            for (PCharacter player : PLAYERS) {
                if (player == null) {
                    continue;
                }
                if (gameMode == GameMode.TUTORIAL && player.getCharType() != PCharacter.CharType.PLAYER) {
                    player.setLastAction(new ActionNone());
                } else {
                    switch (player.getCharType()) {
                        case PLAYER -> player.setLastAction(action);
                        case NORMAL_AI -> {
                            int randomIndex = random.nextInt(actions.size());
                            Action randomAction = actions.get(randomIndex);
                            player.setLastAction(randomAction);
                        }
                    }
                }
            }
            decreaseHunger();
            ++timePassed;
        }
    }

    private void decreaseHunger() {
        for (PCharacter player : PLAYERS) {
            player.setHunger(player.getHunger() - 0.4f);
        }
        thePlayer.setHunger(thePlayer.getHunger() - 0.4f);
    }


    //    private void doAction(PCharacter player, Action randomAction) {
    //        player.setLastAction(randomAction);
    //        if (randomAction.getType() == ActionType.COLLECT_ITEM) {
    //            ActionCollectItem.collect(player, fields
    //                    [(int) player.getCurrentPosition().getNearestWholePosition().getX()]
    //                    [(int) player.getCurrentPosition().getNearestWholePosition().getY()]
    //            );
    //        }
    //    }

    /**
     * Ezen metódus segítségével lekérdezhető az aktuális időpillanat.<br>
     * A játék kezdetekor ez az érték 0 (tehát a legelső időpillanatban az idő 0),
     * majd minden eltelt időpillanat után 1-gyel növelődik.
     *
     * @return az aktuális időpillanat
     */
    public int time() {
        if (gameState == GameState.RUNNING) {
            return timePassed;
        }
        return 0;
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük a játék győztesét.<br>
     * Amennyiben a játéknak még nincs vége (vagy esetleg nincs győztes), akkor null-t ad vissza.
     *
     * @return a győztes karakter vagy null
     */
    public BaseCharacter getWinner() {
        if (gameState != GameState.WIN && gameState != GameState.DEFEAT) {
            return (BaseCharacter) Collections.emptyList();
        } else {
            List<BaseCharacter> winners = new ArrayList<>();
            float highestHealth = Integer.MIN_VALUE;
            for (BaseCharacter player : PLAYERS) {
                float playerHp = player.getHp();

                if (playerHp > highestHealth) {
                    highestHealth = playerHp;
                    winners.clear();
                    winners.add(player);
                }
            }

            return winners.iterator().next();
        }
    }

    /**
     * Ezen metódus segítségével lekérdezhetjük, hogy a játék befejeződött-e már.
     *
     * @return igaz, ha a játék már befejeződött; hamis egyébként
     */
    public boolean isGameEnded() {
        return gameState == GameState.RUNNING && PLAYERS.size() <= 1;
    }

    /**
     * Ezen metódus segítségével beállítható, hogy a játékot tutorial módban szeretnénk-e elindítani.<br>
     * Alapértelmezetten (ha nem mondunk semmit) nem tutorial módban indul el a játék.<br>
     * Tutorial módban a gépi karakterek nem végeznek cselekvést, csak egy helyben állnak.<br>
     * A tutorial mód beállítása még a karakterek csatlakozása előtt történik.
     *
     * @param tutorial igaz, amennyiben tutorial módot szeretnénk; hamis egyébként
     */
    public void setTutorial(boolean tutorial) {
        if (tutorial) {
            this.gameMode = GameMode.TUTORIAL;
        } else {
            this.gameMode = GameMode.NORMAL;
        }
    }

    /**
     * A játékosok elhelyezésekor létrehoz nekik egy inventory-t 4 random itemmel.
     *
     * @return a játékos inventoryját
     */
    public Inventory randomInventory() {
        Inventory inventory = new Inventory();
        //random csinálós rész
        AbstractItem[] items = {new ItemLog(1), new ItemStone(1), new ItemTwig(1), new ItemRawBerry(1), new ItemRawCarrot(1)};
        int[] amounts = new int[5];


        //generálunk 4 random számot GameManager.getInstance().getRandom().nextInt(items.length)-ig.
        // Ez mondja meg, hogy milyen item kerüljön bele
        for (int i = 0; i < 4; i++) {
            int randomIndex = GameManager.getInstance().getRandom().nextInt(items.length);
            amounts[randomIndex]++;
        }
        int j = 0;
        //then iterate through the map and call the public boolean addItem(AbstractItem paramItem) method on them
        for (int i = 0; i < amounts.length; i++) {
            System.out.println("Generated " + items[i].getType() + ": " + amounts[i]);

            if (amounts[i] > 0) {
                inventory.setSlot(j, amounts[i], items[i], items[i].getType()); // AZ INVENTORY J-EDIK ELEMÉRE BERAK amounts[i] mennyiségű items[i]-t
                j++;
            }
        }
        return inventory;
    }

    /**
     * A lehetséges játékmódok enumja.
     */
    public enum GameMode {
        NORMAL, TUTORIAL
    }

    /**
     * A lehetséges játékállapotok enumja.
     */
    public enum GameState {
        BEFORESTART, RUNNING, WIN, DEFEAT
    }
}
