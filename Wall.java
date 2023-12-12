package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall {
    //this class is a wall, you cannot move through a wall, but you can jump on top of it like a platform
    private Rectangle rect;
    private Rectangle LRhitbox;
    public Wall(Pane worldpane, double X, double y, double width, double height ){
        this.setup(worldpane, X, y, width, height);

    }
    public void setup(Pane worldpane, double X, double y, double width, double height ){
        this.rect = new Rectangle(width, height);
        this.rect.setFill(Color.ORANGE);
        this.rect.setOpacity(0.7);

        this.LRhitbox = new Rectangle(width,40);


        this.rect.setX(X);
        this.rect.setY(y);

        this.LRhitbox.setX(X);
        this.LRhitbox.setY(y + height - 40);

        this.LRhitbox.setFill(Color.DARKVIOLET);

        Image image = new Image("indie/backgrounds/tilecover2.png",width, height, false, false);
        ImageView IV = new ImageView();
        IV.setImage(image);
        IV.setX(X);
        IV.setY(y);



        worldpane.getChildren().addAll(this.rect,this.LRhitbox, IV);
    }

    public Rectangle getRect(){
        return this.rect;
    }
    public Rectangle getLRhitbox(){return this.LRhitbox;}

}
