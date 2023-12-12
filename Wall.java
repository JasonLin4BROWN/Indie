package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * This is the Wall class, you cannot move through a wall, but you can jump on top of it like a platform
 */
public class Wall {
    private Rectangle rect;
    private Rectangle LRhitbox;
    public Wall(Pane worldpane, double X, double y, double width, double height ){
        this.setup(worldpane, X, y, width, height);

    }
    /**
     * This is the setup method, it creates the wall and adds it to the world
     */

    public void setup(Pane worldpane, double X, double y, double width, double height ){
        this.rect = new Rectangle(width, height);
        this.LRhitbox = new Rectangle(width,Constants.WALL_LR_HITBOX_OFFSET);


        this.rect.setX(X);
        this.rect.setY(y);

        this.LRhitbox.setX(X);
        this.LRhitbox.setY(y + height - Constants.WALL_LR_HITBOX_OFFSET);


        Image image = new Image("indie/tilecover2.png",width, height, false, false);
        ImageView IV = new ImageView();
        IV.setImage(image);
        IV.setX(X);
        IV.setY(y);


        worldpane.getChildren().addAll(this.rect,this.LRhitbox, IV);
    }

    /**
     * This is the getRect method, it gets the TDhitbox
     */
    public Rectangle getRect(){
        return this.rect;
    }

    /**
     * This is the getLRhitbox method, it gets the lRhitbox
     */
    public Rectangle getLRhitbox(){return this.LRhitbox;}

}
