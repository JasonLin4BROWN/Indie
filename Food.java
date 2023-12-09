package indie;

import javafx.scene.layout.Pane;

public interface Food {
    public void displayFood(Pane worldpane, Player player, double X, double Y);
    public void eat();
    public void removeButton();
    public boolean getShown();
    public void setShown(boolean shown);

}
