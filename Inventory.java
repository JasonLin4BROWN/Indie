package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class Inventory {
    private ImageView viewer;
    private Boolean InvDisplayed;
    private Pane worldpane;
    private LinkedList<Ingredients> ingredients;
    private LinkedList<Food> foodLinkedList;

    private Recipes recipes;
    private Player player;
    public Inventory(Pane worldpane, Player player){
        this.InvDisplayed = false;
        this.worldpane = worldpane;
        this.viewer = new ImageView();
        this.ingredients = new LinkedList<Ingredients>();
        this.foodLinkedList = new LinkedList<Food>();
        this.player = player;

        this.recipes = new Recipes(this.worldpane, this, this.player, this.foodLinkedList);



    }

    public void displayInv(){
        Image inventoryImage = new Image("indie/backgrounds/PixelInventory.png",1450, 800, false, true);
        Image onigiriImage = new Image("indie/Enemies/Onigiri.png", 65, 65, false, true);
        if (!this.InvDisplayed){
            this.viewer.setImage(inventoryImage);
            this.viewer.setX(0);
            this.viewer.setY(0);

            this.worldpane.getChildren().add(this.viewer);

            this.recipes = new Recipes(this.worldpane, this, this.player, this.foodLinkedList);
            this.recipes.displayRecipes(onigiriImage);

            this.InvDisplayed = true;

        }

        else{
            this.worldpane.getChildren().remove(this.viewer);
            this.recipes.removeRecipes();
            this.InvDisplayed = false;

        }
    }

    public void displayING() {

        int row = 0;
        int col = 0;

        if (this.InvDisplayed) {
            for (int i = 0; i < this.ingredients.size(); i++) {
                ImageView curIV = this.ingredients.get(i).getImage();
                if (row > 3 * 117) {
                    row = 0;
                    col = col + 100;

                    curIV.setX(row + 207);
                    curIV.setY(col + 225);

                    if(!this.ingredients.get(i).getShown()) {
                        this.ingredients.get(i).showImage(this.worldpane);
                    }

                    row = row + 117;

                } else {
                    curIV.setX(row + 207);
                    curIV.setY(col  + 225);
                    if(!this.ingredients.get(i).getShown()) {
                        this.ingredients.get(i).showImage(this.worldpane);
                    }

                    row = row + 117;

                }
            }
        }
        else {

            for (int i = 0; i < this.ingredients.size(); i++) {
                if (this.ingredients.get(i).getImage() != null) {
                    this.ingredients.get(i).removeImage(this.worldpane);

                }

            }
        }
    }

    public void removeING() {
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (this.ingredients.get(i).getImage() != null) {
                this.ingredients.get(i).removeImage(this.worldpane);

            }
        }
    }
    public void displayFoods(){

        LinkedList<Food> foodLinkedlist = this.recipes.getFoodLinkedList();
        int row = 0;
        int col = 0;
        if (this.InvDisplayed) {
            for (int i = 0; i < foodLinkedlist.size(); i++) {
                if (row>3*117) {
                    row = 0;
                    col = col + 100;

                    if (!foodLinkedlist.get(i).getShown()) {
                        foodLinkedlist.get(i).displayFood(this.worldpane, this.player, 755 + 117 * row, 525 + col);
                    }
                }
                else{
                    if (!foodLinkedlist.get(i).getShown()) {
                        foodLinkedlist.get(i).displayFood(this.worldpane, this.player, 755 + row, 525 + col);
                    }
                    row = row + 117;
                }
            }
            }
        else {
            for (int i = 0; i < foodLinkedlist.size(); i++) {
                foodLinkedlist.get(i).removeButton();
                foodLinkedlist.get(i).setShown(false);

                }

            }

    }

    public void removeFoods(){
        LinkedList<Food> foodLinkedlist = this.recipes.getFoodLinkedList();

        for (int i = 0; i < foodLinkedlist.size(); i++) {
            foodLinkedlist.get(i).removeButton();
            foodLinkedlist.get(i).setShown(false);

        }
    }


    public LinkedList<Ingredients> getInventory(){
        return this.ingredients;
    }

    public void changePane(Pane worldpane){
        this.worldpane = worldpane;
    }

    public void changePlayer(Player player){
        this.player = player;
    }
}
