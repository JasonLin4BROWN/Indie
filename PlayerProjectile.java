package indie;


import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/**
 * the PlayerProjectile class, it implements the projectile and is the one the player can summon
 */
public class PlayerProjectile implements Projectile{
    private Pane worldpane;
    private Player player;
    private ArrayList<Enemy> enemyList;
    private Rectangle body;
    private int intersectedC;
    private Enemy closestE;

    private double posX;
    private double posY;

    private double velX;
    private double velY;

    private boolean exists;
    private long spawnTime;

    public PlayerProjectile(Pane worldpane, Player player, ArrayList<Enemy> enemyList){
        //set up all instance variables
        this.exists = true;
        this.spawnTime = System.currentTimeMillis();

        this.worldpane = worldpane;
        this.player =  player;
        this.enemyList = enemyList;
        this.closestE = this.enemyList.get(0);


        this.body = new Rectangle(Constants.PROJECTILE_SIZE,Constants.PROJECTILE_SIZE);

        //this projectile should never intersect more than once
        this.intersectedC = 0;

        this.posX = this.player.getHitbox().getX();
        this.posY = this.player.getHitbox().getY();

        this.velX = 0;
        this.velY = 0;


        this.spawn();
    }

    /**
     * the spawn method, it spawns the projectile in and makes it white
     */
    public void spawn() {
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Constants.PLAYER_PROJECTILE_COLOR);
        this.body.setOpacity(0.5);

        this.worldpane.getChildren().addAll(this.body);


    }
    /**
     * the sense method, it finds the closest enemy
     */
    public Enemy sense() {
        //set the closest enemy to be some absurd distance
        double closestEDis = 10000000;

        for(int i = 0; i < this.enemyList.size(); i++){
            if(this.enemyList.get(i).getStatus() && this.exists) {
                //find distance of enemy from projectile
                double enemyX = this.enemyList.get(i).getBody().getX();
                double enemyY = this.enemyList.get(i).getBody().getY();

                double projectileX = this.body.getX();
                double projectileY = this.body.getY();

                double distance = Math.sqrt(Math.pow(enemyX - projectileX, 2) + Math.pow(enemyY - projectileY, 2));

                //find the closest enemy
                if (distance <= closestEDis) {
                    closestEDis = distance;
                    this.closestE = this.enemyList.get(i);

                } else {
                    closestEDis = closestEDis;
                }
            }

        }
        return closestE;

    }

    /**
     * the hunt method, it moves the projectile towards the closest enemy at a rapid rate
     */
    public void hunt(){

        //if right of enemy, move left
        if (this.posX - this.closestE.getBody().getX() > 0){
            this.moveLeft();
            this.kill();
        }
        //if left of enemy, move right
        else if (this.posX - this.closestE.getBody().getX()< 0){
            this.moveRight();
            this.kill();

        }

        //if below enemy rise
        if (this.posY - this.closestE.getBody().getY() > 0){
            //PProj is below enemy
            this.rise();
            this.kill();

        }
        //if above enemy fall
        else if (this.posY - this.closestE.getBody().getY() < 0){
            //PProj is above enemy
            this.fall();
            this.kill();

        }



    }
    /**
     * the kill method, it hurts the enemy when it intersects with them
     */
    public void kill(){
        //interesection function
        Bounds ppjectBound = this.body.getBoundsInParent();
        Bounds enemyBound = this.closestE.getBody().getBoundsInParent();


        if(ppjectBound.intersects(enemyBound)){
            if(this.intersectedC < 1) {
                this.closestE.setHP(this.closestE.getHP() - 1);
                this.intersectedC = this.intersectedC + 1;
                this.despawn();
            }

        }
    }

    /**
     * the moveLeft method, moves it left
     */
    public void moveLeft() {
        this.velX = this.velX - Constants.PLAYER_PROJECTILE_SPEED;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);

    }


    /**
     * the moveRight method, moves it right
     */
    public void moveRight() {
        this.velX = this.velX + Constants.PLAYER_PROJECTILE_SPEED;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);
    }

    /**
     * the rise method, moves it up
     */
    public void rise() {
        this.velY = this.velY - Constants.PLAYER_PROJECTILE_SPEED;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);

    }

    /**
     * the fall method, moves it down
     */
    public void fall() {
        this.velY = this.velY + Constants.PLAYER_PROJECTILE_SPEED;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);
    }

    /**
     * the despawn method, removes the projectile from the game
     */
    public void despawn() {
        this.worldpane.getChildren().remove(this.body);
        this.exists = false;

    }

    /**
     * the deathClock method, removes the projectile from the game after its lifespan is up
     */
    public void deathClock(){
        long time = System.currentTimeMillis();
        long lifeTime = Constants.PLAYER_PROJECTILE_LIFESPAN;
        if (time > this.spawnTime + lifeTime && this.exists) {
            this.despawn();
        }

    }
}
