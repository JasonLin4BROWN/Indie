package indie;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class Onigiri implements Food {
    private Inventory inventory;
    private Image img;
    private Button button;
    private Pane worldpane;
    private Player player;
    private LinkedList<Food> foodLinkedList;
    private boolean shown;

    public Onigiri(Pane worldpane, Inventory inventory, Player player, LinkedList<Food> foodLinkedList){
        this.inventory = inventory;
        this.shown = false;

        this.img = new Image("indie/Enemies/Onigiri.png", 80, 80, false, true);
        this.player = player;

        this.button = new Button();
        this.foodLinkedList = foodLinkedList;
    }

    public void displayFood(Pane worldpane, Player player, double X, double Y){
        //make button with the food
        this.worldpane = worldpane;
        this.player = player;

        this.button.setTranslateX(X);
        this.button.setTranslateY(Y);

        this.button.setPrefSize(80, 80);
        ImageView IV = new ImageView();
        IV.setImage(this.img);

        this.button.setGraphic(IV);
        this.button.setOnAction((event) -> {
            this.eat();

        });
        this.button.setBackground(null);


        this.worldpane.getChildren().addAll(this.button);
        this.shown =  true;
    }

    public void eat(){
        System.out.println("pressed");
        System.out.println("player hp " + this.player);

        if(this.player.getHP()<5){
            this.player.setHP(this.player.getHP() + 1);
            System.out.println(this.player.getHP());



            this.worldpane.getChildren().remove(this.button);
            this.foodLinkedList.remove(this);
        }
    }

    public void removeButton(){
        this.worldpane.getChildren().remove(this.button);
    }

    public boolean getShown(){
        return this.shown;
    }



    public void setShown(boolean shown){
         this.shown = shown;
    }
}
