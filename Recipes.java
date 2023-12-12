package indie;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.Objects;

/**
 * This is the Recipes class, it defines what recipes are and the ability to use it to cook
 */
public class Recipes {
    private Inventory inventory;
    private Button button;
    private Pane worldpane;
    private LinkedList<Food> foodLinkedList;
    private Player player;


    public Recipes(Pane worldpane, Inventory inventory, Player player, LinkedList<Food> foodLinkedList) {
        this.player = player;
        this.inventory = inventory;
        this.button = new Button();
        this.worldpane = worldpane;
        this.foodLinkedList = foodLinkedList;


    }
    /**
     * This is the displayRecipes method, it creates a Recipes button which can be pressed to cook food,
     * in this case Onigiri
     */
    public void displayRecipes(Image img) {

        this.button.setTranslateX(Constants.RECIPE_X);
        this.button.setTranslateY(Constants.RECIPE_Y);
        this.button.setPrefSize(Constants.RECIPE_SIZE, Constants.RECIPE_SIZE);

        ImageView IV = new ImageView();
        IV.setImage(img);
        this.button.setGraphic(IV);

        this.button.setOnAction((event) -> {
            if(this.foodLinkedList.size()<= Constants.MAX_FOOD) {
                this.cook();
            }

        });

        this.worldpane.getChildren().addAll(this.button);

    }
    /**
     * This is the removeRecipes method,it removes the button from existence
     */
    public void removeRecipes() {
        this.worldpane.getChildren().remove(this.button);
    }


    /**
     * This is the cook method, it allows the play to cook;
     */
    public void cook() {
        LinkedList<Ingredients> inven = this.inventory.getInventory();

        //saving the positions of the rice and seaweed
        int riceI = -1;
        int seaweedI = -1;

        for (int i = 0; i < inven.size(); i++) {
            //essetinally we check: Rice + Seaweed and then Seaweed + Rice and then remove the first two
            if (Objects.equals(inven.get(i).getType(), "rice")) {
                riceI = i;
                for (int j = 0; j < inven.size(); j++) {
                    if (Objects.equals(inven.get(j).getType(), "seaweed")) {
                        seaweedI = j;

                        //if this combination exists then remove both ingredients and cook
                        inven.get(seaweedI).removeImage(this.worldpane);
                        inven.remove(seaweedI);

                        inven.get(riceI).removeImage(this.worldpane);
                        inven.remove(riceI);


                        this.foodLinkedList.add(new Onigiri(this.worldpane, this.player, this.foodLinkedList));

                        break;
                    }
                }
                break;
            } else if (Objects.equals(inven.get(i).getType(), "seaweed")) {
                seaweedI = i;
                for (int j = 0; j < inven.size(); j++) {
                    if (Objects.equals(inven.get(j).getType(), "rice")) {
                        riceI = j;

                        //if this combination exists then remove both ingredients and cook
                        //note to remove the one after first, then the one before or else we get indexbound errors

                        inven.get(riceI).removeImage(this.worldpane);
                        inven.remove(riceI);

                        inven.get(seaweedI).removeImage(this.worldpane);
                        inven.remove(seaweedI);


                        this.foodLinkedList.add(new Onigiri(this.worldpane, this.player, this.foodLinkedList));

                        break;
                    }
                }
                break;
            }

            //this resets the inventories images to update with new positions
            this.inventory.removeING();
            this.inventory.displayING();
            this.inventory.removeFoods();
            this.inventory.displayFoods();
        }
    }

    /**
     * This is the getFoodLinkedList method, it gets the food list that have been cooked;
     */
    public LinkedList<Food> getFoodLinkedList() {
        return this.foodLinkedList;
    }
}
