import prog1.kotprog.dontstarve.solution.inventory.Inventory;
import prog1.kotprog.dontstarve.solution.inventory.items.AbstractItem;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemAxe;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemRawBerry;
import prog1.kotprog.dontstarve.solution.inventory.items.ItemTwig;


public class Main {

    static void kiir(Inventory inventory1) {
        for (int i = 0; i < 10; i++) {
            if (inventory1.getItem(i) == null) {
                System.out.println("NULL");
            } else {
                System.out.println(inventory1.getItem(i).getType());
            }
        }
    }

    /**
     * Az inventory testere.
     *
     * @param args nem kell nekünk
     */
    public static void main(String[] args) {
        Inventory inventory1 = new Inventory();

        AbstractItem axe = new ItemAxe();
        AbstractItem twig = new ItemTwig(50);
        AbstractItem rawBerry = new ItemRawBerry(1);
//        AbstractItem rawCarrot = new ItemRawCarrot(5);
//            inventory1.addItem(rawCarrot);
//        System.out.println(inventory1);
        inventory1.addItem(new ItemRawBerry(1000));
        System.out.println(inventory1);
        inventory1.cookItem(0);
        System.out.println(inventory1);
//        inventory1.addItem(new ItemAxe());
//        System.out.println(inventory1);
//        for (int i = 0; i < 10; i++) {
//            inventory1.equipItem(i);
//        }
//        System.out.println(inventory1);
//        inventory1.unequipItem();
//        System.out.println(inventory1);
//        PCharacter character = new PCharacter("Sanyi");
//        Position position = GameManager.getInstance().joinCharacter("Józsi",true);
//        System.out.println(character);
//        System.out.println(position);
    }
}