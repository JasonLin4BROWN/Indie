package indie;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public interface Ingredients {
    public void setType(String type);
    public String getType();
    public void showImage(Pane worldpane);
    public void removeImage(Pane worldpane);
    public ImageView getImage();
    public boolean getShown();

}
