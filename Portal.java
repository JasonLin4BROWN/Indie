package wonders;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import wonders.PaneOrganizer;

/**
 * this is a class which makes a portal to the next world
 */
public class Portal {
    private Rectangle rect;
    private double width;
    private double height;
    private PaneOrganizer paneorganizer;

    public Portal(PaneOrganizer paneorganizer, Pane worldpane, double X, double y){
        this.paneorganizer = paneorganizer;
        this.width = 200;
        this.height = 1200;


        this.setup(worldpane, X, y);



    }
    public void setup(Pane worldpane, double X, double y){
        this.rect = new Rectangle(this.width, this.height);
        this.rect.setFill(Color.PURPLE);
        this.rect.setOpacity(0.7);


        this.rect.setX(X);
        this.rect.setY(y);

        worldpane.getChildren().addAll(this.rect);
    }

    public void sendWorld(){
        this.paneorganizer.nextWorld();
        System.out.println(this.paneorganizer.getWorldCur());
    }

    public Rectangle getRect() {
        return this.rect;
    }
}
