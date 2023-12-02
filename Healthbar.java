package wonders;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Healthbar {
    private Player player;
    private Rectangle bar;
    private Rectangle barBack;
    private Pane worldpane;

    public Healthbar(Pane worldpane, Player player){
        this.player = player;
        this.worldpane = worldpane;

        this.bar = new Rectangle();
        this.barBack = new Rectangle();

        this.makeBarBack();
        this.makeBar();


    }

    public void makeBar(){
        this.bar.setX(50);
        this.bar.setY(50);
        this.bar.setWidth(200);
        this.bar.setHeight(50);
        this.bar.setFill(Color.GREEN);

        this.worldpane.getChildren().addAll(this.bar);

    }

    public void makeBarBack(){
        this.barBack.setX(50);
        this.barBack.setY(50);
        this.barBack.setWidth(200);
        this.barBack.setHeight(50);
        this.barBack.setFill(Color.GRAY);

        this.worldpane.getChildren().addAll(this.barBack);


    }

    public void update(){
        this.bar.setWidth(this.player.getHP()*40);

    }


}
