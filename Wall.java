package wonders;

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

        this.LRhitbox = new Rectangle(width,1);


        this.rect.setX(X);
        this.rect.setY(y);

        this.LRhitbox.setX(X);
        this.LRhitbox.setY(y + height - 1);

        this.LRhitbox.setFill(Color.DARKVIOLET);


        worldpane.getChildren().addAll(this.rect,this.LRhitbox);
    }

    public Rectangle getRect(){
        return this.rect;
    }
    public Rectangle getLRhitbox(){return this.LRhitbox;}

}
