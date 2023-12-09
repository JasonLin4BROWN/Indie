package wonders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class Inventory {
    private ImageView viewer;
    private Boolean InvDisplayed;
    private Pane worldpane;
    private LinkedList<Ingredients> ingredients;
    private Recipes recipes;
    public Inventory(Pane worldpane){
        this.InvDisplayed = false;
        this.worldpane = worldpane;
        this.viewer = new ImageView();
        this.ingredients = new LinkedList<Ingredients>();
        this.recipes = new Recipes(worldpane, this);


    }

    public void displayInv(){
        Image inventoryImage = new Image("wonders/backgrounds/PixelInventory.png",1450, 800, false, true);
        Image onigiriImage = new Image("wonders/Enemies/Onigiri.png", 65, 65, false, true);
        if (!this.InvDisplayed){
            this.viewer.setImage(inventoryImage);
            this.viewer.setX(0);
            this.viewer.setY(0);

            this.worldpane.getChildren().add(this.viewer);
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
        System.out.println(this.ingredients.size());

        int row = 0;
        int col = 0;

        if (this.InvDisplayed) {
            System.out.println(this.ingredients.size());
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
                } else {
                    curIV.setX(row + 207);
                    curIV.setY(col  + 225);
                    if(!this.ingredients.get(i).getShown()) {
                        this.ingredients.get(i).showImage(this.worldpane);
                    }

                    row = row + 117;

                }
            }
        } else {
            System.out.println("removing");

            for (int i = 0; i < this.ingredients.size(); i++) {
                if (this.ingredients.get(i).getImage() != null) {
                    this.ingredients.get(i).removeImage(this.worldpane);

                }

            }
        }
    }

    public LinkedList<Ingredients> getInventory(){
        return this.ingredients;
    }

    public void changePane(Pane worldpane){
        this.worldpane = worldpane;
    }
}
