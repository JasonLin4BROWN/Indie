package indie;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.Objects;


public class Recipes {
    private Inventory inventory;
    private Image img;
    private Button button;
    private Pane worldpane;
    private LinkedList<Food> foodLinkedList;
    private Player player;


    public Recipes(Pane worldpane, Inventory inventory, Player player, LinkedList<Food> foodLinkedList) {
        this.player = player;
        this.inventory = inventory;
        this.img = new Image("indie/Enemies/Onigiri.png", 65, 65, false, true);
        this.button = new Button();
        this.worldpane = worldpane;

        this.foodLinkedList = foodLinkedList;


    }

    public void displayRecipes(Image img) {

        this.button.setTranslateX(770);
        this.button.setTranslateY(230);
        this.button.setPrefSize(80, 80);
        ImageView IV = new ImageView();
        IV.setImage(img);

        this.button.setGraphic(IV);
        this.button.setOnAction((event) -> {
            if(this.foodLinkedList.size()<=8) {
                this.cook();
            }

        });

        this.worldpane.getChildren().addAll(this.button);

    }

    public void removeRecipes() {
        this.worldpane.getChildren().remove(this.button);
    }

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

            this.inventory.removeING();
            this.inventory.displayING();
            this.inventory.removeFoods();
            this.inventory.displayFoods();
        }
    }

    public LinkedList<Food> getFoodLinkedList() {
        return this.foodLinkedList;
    }
}
