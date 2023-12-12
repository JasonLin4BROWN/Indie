package indie;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
/**
 * the Healthbar class, it creates and add a visual healthbar into the game which allows you to see how much
 * HP the player has.
 */
public class Healthbar {
    private Player player;
    private Rectangle bar;
    private Rectangle barBack;
    private Pane worldpane;


    public Healthbar(Pane worldpane, Player player){
        //instantate the appropriate instance variables
        this.player = player;
        this.worldpane = worldpane;
        this.bar = new Rectangle();
        this.barBack = new Rectangle();

        //spawn the bars in to the game
        this.makeBarBack();
        this.makeBar();


    }
    /**
     * the makeBar method, creates the green bar which indicts current health
     */
    public void makeBar(){
        this.bar.setX(Constants.HB_X);
        this.bar.setY(Constants.HB_Y);
        this.bar.setWidth(Constants.HB_WIDTH * Constants.PLAYER_HP);
        this.bar.setHeight(Constants.HB_HEIGHT);
        this.bar.setFill(Constants.HB_COLOR);

        this.worldpane.getChildren().addAll(this.bar);

    }

    /**
     * the makeBarBack method, creates the gray bar which indicts the maximum health possible
     */
    public void makeBarBack(){
        this.barBack.setX(Constants.HB_X);
        this.barBack.setY(Constants.HB_Y);
        this.barBack.setWidth(Constants.HB_WIDTH * Constants.PLAYER_HP);
        this.barBack.setHeight(Constants.HB_HEIGHT);
        this.barBack.setFill(Constants.HBB_COLOR);

        this.worldpane.getChildren().addAll(this.barBack);


    }

    /**
     * the update method, it updates the healthbar to match the player's current HP
     */
    public void update(){
        this.bar.setWidth(this.player.getHP()* Constants.HB_WIDTH);

    }


}
