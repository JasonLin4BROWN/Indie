package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.LinkedList;
/**
 * the Inventory class, it models the inventory which the player has and can pull up whenever they
 * press E;
 */
public class Inventory {
    private ImageView viewer;
    private Boolean InvDisplayed;
    private Pane worldpane;
    private LinkedList<Ingredients> ingredients;
    private LinkedList<Food> foodLinkedList;
    private Recipes recipes;
    private Player player;
    public Inventory(Pane worldpane, Player player){
        //instantiate all instance variables
        this.InvDisplayed = false;
        this.worldpane = worldpane;
        this.viewer = new ImageView();
        this.ingredients = new LinkedList<Ingredients>();
        this.foodLinkedList = new LinkedList<Food>();
        this.player = player;
        this.recipes = new Recipes(this.worldpane, this, this.player, this.foodLinkedList);
    }

    /**
     * the displayInv method, it allows the player to display their inventory and close it
     */
    public void displayInv(){
        Image inventoryImage = new Image("indie/PixelInventory.png",Constants.INVENTORY_WIDTH, Constants.INVENTORY_HEIGHT, false, true);
        Image onigiriImage = new Image("indie/Onigiri.png", Constants.ICON_SIZE, Constants.ICON_SIZE, false, true);

        //if inventory is not displayed
        if (!this.InvDisplayed){
            //show the inventory
            this.viewer.setImage(inventoryImage);
            this.viewer.setX(0);
            this.viewer.setY(0);
            this.worldpane.getChildren().add(this.viewer);

            //show the recipes:
            this.recipes = new Recipes(this.worldpane, this, this.player, this.foodLinkedList);
            this.recipes.displayRecipes(onigiriImage);

            this.InvDisplayed = true;

        }

        //if inventory is  displayed
        else{
            //remove everything from the screen
            this.worldpane.getChildren().remove(this.viewer);
            this.recipes.removeRecipes();
            this.InvDisplayed = false;

        }
    }

    /**
     * the displayING method, it allows the player to display the ingredients in their inventory
     * this method is called seperately for organization and such that it is updated each time the
     * player cooks food
     */
    public void displayING() {

        //we want a 5 by 4 grid for the ingredient display
        int row = 0;
        int col = 0;

        if (this.InvDisplayed) {
            for (int i = 0; i < this.ingredients.size(); i++) {
                ImageView curIV = this.ingredients.get(i).getImage();
                //at every 4th food it will move down to the next row
                if (row > 3 * Constants.WIDTH_BTW_ICONS) {
                    row = 0;
                    col = col + Constants.HEIGHT_BTW_ICONS;

                    curIV.setX(row + Constants.INGREDIENT_POSX_CORRECTION);
                    curIV.setY(col + Constants.INGREDIENT_POSY_CORRECTION);

                    //show this ingredient
                    if(!this.ingredients.get(i).getShown()) {
                        this.ingredients.get(i).showImage(this.worldpane);
                    }

                    row = row + Constants.WIDTH_BTW_ICONS;
                }

                //otherwise just go to the next column
                else {
                    curIV.setX(row + Constants.INGREDIENT_POSX_CORRECTION);
                    curIV.setY(col  + Constants.INGREDIENT_POSY_CORRECTION);
                    if(!this.ingredients.get(i).getShown()) {
                        this.ingredients.get(i).showImage(this.worldpane);
                    }

                    row = row + Constants.WIDTH_BTW_ICONS;

                }
            }
        }

        //if we aren't displaying the inventory then remove the ingredients from view
        else {
            this.removeING();
            }
    }

    /**
     * the removeING method, it removes ingredients from view when called
     */
    public void removeING() {
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (this.ingredients.get(i).getImage() != null) {
                this.ingredients.get(i).removeImage(this.worldpane);

            }
        }
    }
    /**
     * the displayFoods method, it displays the foods when in inventory is visible
     */
    public void displayFoods(){
        LinkedList<Food> foodLinkedlist = this.recipes.getFoodLinkedList();
        //we want a 2 by 4 grid for food
        int row = 0;
        int col = 0;

        if (this.InvDisplayed) {
            for (int i = 0; i < foodLinkedlist.size(); i++) {
                //when row is filled up go to the next row
                if (row>3*Constants.WIDTH_BTW_ICONS) {
                    row = 0;
                    col = col + Constants.HEIGHT_BTW_ICONS;

                    if (!foodLinkedlist.get(i).getShown()) {
                        foodLinkedlist.get(i).displayFood(this.worldpane, this.player, Constants.FOOD_POSX_CORRECTION, Constants.FOOD_POSY_CORRECTION + col);
                    }
                }

                //otherwise fill up row
                else{
                    if (!foodLinkedlist.get(i).getShown()) {
                        foodLinkedlist.get(i).displayFood(this.worldpane, this.player, Constants.FOOD_POSX_CORRECTION + row, Constants.FOOD_POSY_CORRECTION + col);
                    }
                    row = row + Constants.WIDTH_BTW_ICONS;
                }
            }
            }
        //when inventory is not displayed remove foods from view
        else {
            this.removeFoods();
            }

    }

    /**
     * the removeFoods method, it removes food from view
     */
    public void removeFoods(){
        LinkedList<Food> foodLinkedlist = this.recipes.getFoodLinkedList();

        for (int i = 0; i < foodLinkedlist.size(); i++) {
            foodLinkedlist.get(i).removeButton();
            foodLinkedlist.get(i).setShown(false);

        }
    }

    /**
     * the getInventory method, it returns the ingredients in the inventory
     */
    public LinkedList<Ingredients> getInventory(){
        return this.ingredients;
    }

    /**
     * the changePane method, it moves the inventory into the next pane when necessary
     */
    public void changePane(Pane worldpane){
        this.worldpane = worldpane;
    }

    /**
     * the changePlayer method, it updates the player that has the inventory
     */
    public void changePlayer(Player player){
        this.player = player;
    }
}
