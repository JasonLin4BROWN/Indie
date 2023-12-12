package indie;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
/**
 * the Onigiri class, it is a type of food the player can consume to regain health
 */
public class Onigiri implements Food {
    private Image img;
    private Button button;
    private Pane worldpane;
    private Player player;
    private LinkedList<Food> foodLinkedList;
    private boolean shown;

    public Onigiri(Pane worldpane, Player player, LinkedList<Food> foodLinkedList){
        //define all instance variables
        this.shown = false;
        this.img = new Image("indie/Enemies/Onigiri.png", Constants.ONIGIRI_SIZE, Constants.ONIGIRI_SIZE, false, true);
        this.player = player;
        this.button = new Button();
        this.foodLinkedList = foodLinkedList;
    }

    /**
     * the displayFood method, it is a type of food the player can consume to regain health
     */
    public void displayFood(Pane worldpane, Player player, double X, double Y){
        //make button with the food, click it to eat the food
        this.worldpane = worldpane;
        this.player = player;

        this.button.setTranslateX(X);
        this.button.setTranslateY(Y);

        this.button.setPrefSize(Constants.ONIGIRI_SIZE, Constants.ONIGIRI_SIZE);
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

    /**
     * the eat method, it allows the player to click to eat the onigiri and thus regain hp
     */
    public void eat(){
        if(this.player.getHP()<Constants.PLAYER_HP){
            //when player clicks with less than 5 HP, heal up
            this.player.setHP(this.player.getHP() + Constants.HEAL_HP);

            //then consume the food and remove the from the world
            this.worldpane.getChildren().remove(this.button);
            this.foodLinkedList.remove(this);
        }
    }


    /**
     * the removeButton method, it allows this onigiri to remove itself from the pane, (useful when
     * calling of bodies cannot be done)
     */
    public void removeButton(){
        this.worldpane.getChildren().remove(this.button);
    }

    /**
     * the getShown method, it returns if this food is being shown
     */
    public boolean getShown(){
        return this.shown;
    }

    /**
     * the setShown method, it sets if this food is being shown
     */
    public void setShown(boolean shown){
         this.shown = shown;
    }
}
