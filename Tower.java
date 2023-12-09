package indie;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tower extends Wall{

    private Rectangle rect;
    private Rectangle LRhitbox;

    public Tower(Pane worldpane, double X, double y, double width, double height) {
        super(worldpane, X, y, width, height);

    }

    public void setup(Pane worldpane, double X, double y, double width, double height ){
        this.rect = new Rectangle(width, height);
        this.rect.setFill(Color.ORANGE);
        this.rect.setOpacity(0.7);

        this.LRhitbox = new Rectangle(width,height-100);


        this.rect.setX(X);
        this.rect.setY(y);

        this.LRhitbox.setX(X);
        this.LRhitbox.setY(y + 100);

        this.LRhitbox.setFill(Color.DARKVIOLET);


        worldpane.getChildren().addAll(this.rect,this.LRhitbox);
    }

    public Rectangle getRect(){
        return this.rect;
    }
    public Rectangle getLRhitbox(){return this.LRhitbox;}





}
