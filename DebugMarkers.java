package wonders;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DebugMarkers {
    private Pane worldpane;
    public DebugMarkers(Pane worldpane){
        this.worldpane = worldpane;
        this.createDebugMarkers();
    }

    public void createDebugMarkers() {
        int i;
        for (i = 0; i <= 20; i++) {
            Rectangle rect = new Rectangle(80, 10);
            rect.setFill(Color.BLACK);
            rect.setOpacity(1);

            double posY = i*50;


            rect.setX(0);
            rect.setY(posY);

            Label label  = new Label(posY+"");
            label.setTranslateX(0);
            label.setTranslateY(posY);
            label.setTextFill(Color.WHITE);
            this.worldpane.getChildren().addAll(rect,label);
        }

        for (i = 0; i <= 38; i++) {
            Rectangle rect = new Rectangle(10, 80);
            rect.setFill(Color.BLACK);
            rect.setOpacity(1);

            double posX = i*50;

            rect.setX(posX);
            rect.setY(0);



            rect.setX(posX);
            rect.setY(0);

            Label label1  = new Label(posX+"");
            label1.setTranslateX(posX);
            label1.setTranslateY(0);
            label1.setTextFill(Color.WHITE);
            this.worldpane.getChildren().addAll(rect,label1);
        }

    }
}
