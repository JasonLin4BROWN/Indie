package indie;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is the EnemyProjectile class, it models the projectiles used specifically by the enemy against
 * the player
 */
public class EnemyProjectile implements Projectile{
    private Pane worldpane;
    private Player player;
    private Rectangle body;
    private int intersectedC;
    private double posX;
    private double posY;

    private double velX;
    private double velY;

    private boolean exists;
    private long spawnTime;

    public EnemyProjectile(Pane worldpane, Enemy enemy, Player player){
        //instantiate all instance variables:
        this.exists = true;
        this.spawnTime = System.currentTimeMillis();
        this.worldpane = worldpane;
        this.player =  player;

        //Make body and hitbox
        this.body = new Rectangle(Constants.PROJECTILE_SIZE,Constants.PROJECTILE_SIZE);

        //this projectile should never intersect more than once with player
        this.intersectedC = 0;

        //projectile should spawn where enemy casting it is located
        this.posX = enemy.getPosX();
        this.posY = enemy.getPosY();

        this.velX = 0;
        this.velY = 0;


        this.spawn();
    }

    /**
     * This is the spawn method, it spawns the projectile in and add it to the worldpane
     */
    public void spawn() {
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.WHITE);
        this.body.setOpacity(0.5);

        this.worldpane.getChildren().addAll(this.body);


    }

    /**
     * This is the hunt method, it allows the enemy projectile to find the player
     */
    public void hunt(){
        if (this.posX - this.player.getHitbox().getX() > 0){
            //enemy projectile is currently right of player, so move left
            this.moveLeft();
            this.kill();
        }

        else if (this.posX - this.player.getHitbox().getX()< 0){
            //enemy projectile is currently left of player, so move right
            this.moveRight();
            this.kill();

        }

        //this checks Y
        if (this.posY - this.player.getHitbox().getY() > 0){
            //enemy projectile is currently below player, so move up
            this.rise();
            this.kill();

        }

        else if (this.posY - this.player.getHitbox().getY() < 0){
            //enemy projectile is currently above player, so move down
            this.fall();
            this.kill();

        }



    }
    /**
     * This is the kill method, it is called whenever the projectile and player intersect and forces the
     * player to take damage
     */
    public void kill(){
        //interesection function
        Bounds ppjectBound = this.body.getBoundsInParent();
        Bounds playerBound = this.player.getHitbox().getBoundsInParent();


        if(ppjectBound.intersects(playerBound) && this.exists){
            //this ensures that every projectile can only intersect with the player once
            if(this.intersectedC < 1) {
                this.player.hurt();
                this.intersectedC = this.intersectedC+1;
                this.despawn();
            }

        }
    }

    /**
     * This is the moveLeft method, it is moves the enemyProjectile left
     */
    public void moveLeft() {
        this.velX = this.velX - Constants.E_PROJECTILE_SPEED;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);

    }
    /**
     * This is the moveRight method, it is moves the enemyProjectile right
     */
    public void moveRight() {
        this.velX = this.velX + Constants.E_PROJECTILE_SPEED;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);
    }

    /**
     * This is the rise method, it is moves the enemyProjectile up
     */
    public void rise() {
        this.velY = this.velY - Constants.E_PROJECTILE_SPEED;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);

    }

    /**
     * This is the fall method, it is moves the enemyProjectile down
     */
    public void fall() {
        this.velY = this.velY + Constants.E_PROJECTILE_SPEED;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);
    }

    /**
     * This is the despawn method, it is removes the enemyProjectile from the game
     */
    public void despawn() {
        this.worldpane.getChildren().remove(this.body);
        this.exists = false;

    }
    /**
     * This is the deathClock method, it is essentially a timer which ensures that the projectile
     * only exists for so long
     */
    public void deathClock(){
        long time = System.currentTimeMillis();
        //projectile lasts for 5 seconds
        long lifeTime = Constants.E_PROJECTILE_LIFESPAN;
        if (time > this.spawnTime + lifeTime && this.exists) {
            this.despawn();
        }

    }
}
