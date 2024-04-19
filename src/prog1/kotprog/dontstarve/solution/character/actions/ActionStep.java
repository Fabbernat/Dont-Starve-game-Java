package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.utility.Direction;

/**
 * A lépés akció leírására szolgáló osztály: a karakter egy lépést tesz balra, jobbra, fel vagy le.
 */
public class ActionStep extends Action {
    /**
     * A mozgás iránya.
     */
    private final Direction direction;

    /**
     * Az akció létrehozására szolgáló konstruktor.
     *
     * @param direction a mozgás iránya
     */
    public ActionStep(Direction direction) {
        super(ActionType.STEP);
        this.direction = direction;
    }

    /**
     * A direction gettere.
     *
     * @return a mozgás iránya
     */
    public Direction getDirection() {
        return direction;
    }

    //    /**
    //     * Mozgás
    //     */
    //    public Position move(PCharacter pCharacter, Direction direction) {
    //        switch (direction) {
    //            case UP -> pCharacter.getCurrentPosition().setY(pCharacter.getCurrentPosition().getY() - pCharacter.getSpeed());
    //            case DOWN -> pCharacter.getCurrentPosition().setY(pCharacter.getCurrentPosition().getY() + pCharacter.getSpeed());
    //            case LEFT -> pCharacter.getCurrentPosition().setX(pCharacter.getCurrentPosition().getX() - pCharacter.getSpeed());
    //            case RIGHT -> pCharacter.getCurrentPosition().setX(pCharacter.getCurrentPosition().getX() + pCharacter.getSpeed());
    //        }
    //        return pCharacter.getCurrentPosition();
    //    }
}
