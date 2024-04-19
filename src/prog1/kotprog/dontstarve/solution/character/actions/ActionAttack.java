package prog1.kotprog.dontstarve.solution.character.actions;

import prog1.kotprog.dontstarve.solution.GameManager;
import prog1.kotprog.dontstarve.solution.character.PCharacter;

/**
 * A támadás akció leírására szolgáló osztály: a legközelebbi karakter megtámadása.
 */
public class ActionAttack extends Action {
    /**
     * Az akció létrehozására szolgáló konstruktor.
     */
    public ActionAttack() {
        super(ActionType.ATTACK);
    }

    /**
     * Végrehajtja az akciót: megtámadja a legközelebbi karaktert.
     *
     * @param tamado a karakter, aki az akciót végrehajtja
     */
    public void execute(PCharacter tamado) {
        // A játékos helyzetének lekérése

        // A legközelebbi karakter kiválasztása
        PCharacter target = null;
        float legkozelebbi = Integer.MAX_VALUE;
        for (PCharacter vedo : GameManager.getInstance().getPlayers()) {
            if (vedo != tamado) {
                float tav = (float) (Math.sqrt(Math.pow(vedo.getCurrentPosition().getX() - tamado.getCurrentPosition().getX(),2)) + vedo.getCurrentPosition()
                        .getY() - Math.pow(tamado.getCurrentPosition().getY(),2));
                if (tav < legkozelebbi) {
                    target = vedo;
                    legkozelebbi = tav;
                }
            }
        }

        // Ha van célpont, megtámadjuk
        if (target != null) {
            target.setHp(target.getHp() - tamado.getDamage());
        }
    }

}

