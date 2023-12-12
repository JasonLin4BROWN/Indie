package indie;

import javafx.scene.layout.Pane;

/**
 * This is the Food interface, it enables polymorphism of our foods and standardizes their methods
 */
public interface Food {
    public void displayFood(Pane worldpane, Player player, double X, double Y);
    public void eat();
    public void removeButton();
    public boolean getShown();
    public void setShown(boolean shown);

}
