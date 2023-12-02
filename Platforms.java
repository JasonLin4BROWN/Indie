package wonders;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Platforms {
    private Rectangle rect;
    private double X;
    private double y;
    public Platforms(Pane worldpane, double X, double y ){
        this.rect = new Rectangle(300, 20);
        this.rect.setFill(Color.DARKBLUE);
        this.rect.setOpacity(0.7);

        this.X = X;
        this.y = y;


        this.rect.setX(this.X);
        this.rect.setY(this.y);
        worldpane.getChildren().addAll(this.rect);

    }

    public Rectangle getRect(){
        return this.rect;
    }

    public double getX(){
        return this.X;
    }

    public double getY(){
        return this.y;
    }
}
