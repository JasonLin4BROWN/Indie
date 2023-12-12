package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * This is the Tower class, it is a type of wall which is very tall and high and used as a block off essentially
 */
public class Tower extends Wall{

    private Rectangle rect;
    private Rectangle LRhitbox;

    public Tower(Pane worldpane, double X, double y, double width, double height) {
        super(worldpane, X, y, width, height);

    }

    /**
     * This is the Tower class, it is a type of wall which is very tall and high and used as a block off essentially
     */
    @Override
    public void setup(Pane worldpane, double X, double y, double width, double height ){
        //create the full tower itself which is recognized as the top-down collision space
        this.rect = new Rectangle(width, height);
        this.rect.setX(X);
        this.rect.setY(y);

        //create the LRhitbox which is recognized as the left-right collision space
        this.LRhitbox = new Rectangle(width,height- Constants.TOWER_LR_HITBOX_OFFSET);
        this.LRhitbox.setX(X);
        this.LRhitbox.setY(y + Constants.TOWER_LR_HITBOX_OFFSET);

        //tower's image based on dimensions
        Image image = new Image("indie/backgrounds/tilecover2.png",width, height, false, false);
        ImageView IV = new ImageView();
        IV.setImage(image);
        IV.setX(X);
        IV.setY(y);

        worldpane.getChildren().addAll(this.rect,this.LRhitbox, IV);
    }
    /**
     * This is the getRect method, it gets the TDhitbox
     */
    @Override
    public Rectangle getRect(){
        return this.rect;
    }
    /**
     * This is the getLRhitbox method, it gets the lRhitbox
     */
    @Override
    public Rectangle getLRhitbox(){return this.LRhitbox;}





}
