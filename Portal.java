package indie;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import indie.PaneOrganizer;

/**
 * this is a class which makes a portal to the next world
 */
public class Portal {
    private Rectangle rect;
    private PaneOrganizer paneorganizer;

    public Portal(PaneOrganizer paneorganizer, Pane worldpane, double X, double y){
        this.paneorganizer = paneorganizer;
        this.setup(worldpane, X, y);



    }
    /**
     * this is the setup method, it sets up the portal into the world
     */
    public void setup(Pane worldpane, double X, double y){
        this.rect = new Rectangle(Constants.PORTAL_WIDTH, Constants.PORTAL_HEIGHT);
        this.rect.setFill(Color.TRANSPARENT);

        this.rect.setX(X);
        this.rect.setY(y);

        worldpane.getChildren().addAll(this.rect);
    }

    /**
     * this is the sendWorld method, if the player intersects with the portal it will send them to the next world
     */
    public void sendWorld(){
        this.paneorganizer.nextWorld();
    }

    /**
     * this is the getRect method, it gets the hitbox of the portal
     */
    public Rectangle getRect() {
        return this.rect;
    }
}
