package indie;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * This is the Enemy interface, essentially we want call all enemies at the same time so we
 * use this for polymorphism
 */
public interface Enemy{
    public void Spawn(Pane worldpane, double X, double y);
    public void Update(Player player);
    public void Sense(Player player);
    public void DefaultBehavior();
    public void ReactX(Player player);
    public void ReactY(Player player);

    public void antiReactX(Player player);
    public void antiReactY(Player player);


    public void Attack(Player player);
    public void attackHelper(Player player);

    public void MoveLeft();
    public void MoveRight();
    public void Jump();
    public void helperJump();
    public void Fall(double platY);
    public void doGravity(double platY);

    public void Die();
    public boolean getStatus();
    public int getHP();
    public void setHP(int newHP);
    public double getPosX();
    public double getPosY();
    public void setPosX(double PosX);
    public void setPosY(double PosY);
    public void positioning(double PosX, double PosY);

    public Rectangle getBody();


}
