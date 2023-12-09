package indie;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Recipes {
    private Inventory inventory;
    private Image img;
    private Button button;
    private Pane worldpane;
    public Recipes(Pane worldpane, Inventory inventory){
        this.inventory = inventory;
        this.img = new Image("indie/Enemies/Onigiri.png", 65, 65, false, true);
        this.button = new Button();
        this.worldpane = worldpane;



    }

    public void displayRecipes(Image img){

        this.button.setTranslateX(770);
        this.button.setTranslateY(230);
        this.button.setPrefSize(80, 80);
        ImageView IV =  new ImageView();
        IV.setImage(img);

        this.button.setGraphic(IV);
        this.worldpane.getChildren().addAll(this.button);

    }

    public void removeRecipes(){
        this.worldpane.getChildren().remove(this.button);
    }
}
