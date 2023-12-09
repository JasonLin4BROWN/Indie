package indie;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class IngredientInventory {
    private Rectangle body;
    private Pane worldpane;

    public IngredientInventory(Pane worldpane){
        this.body = new Rectangle();

    }

    public void setInvenVis(){
        this.body.setX(200);
        this.body.setY(200);


    }


}
