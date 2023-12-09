package indie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlayerProjectile implements Projectile{
    private Pane worldpane;
    private Player player;
    private ArrayList<Enemy> enemyList;
    private Rectangle body;
    private int intersectedC;
    private double closestEDis;

    private double posX;
    private double posY;

    private double velX;
    private double velY;

    private boolean exists;
    private long spawnTime;

    public PlayerProjectile(Pane worldpane, Player player, ArrayList<Enemy> enemyList){
        this.exists = true;
        this.spawnTime = System.currentTimeMillis();

        this.worldpane = worldpane;
        this.player =  player;
        this.enemyList = enemyList;

        this.body = new Rectangle(20,20);

        //this projectile should never intersect more than once
        this.intersectedC = 0;

        this.posX = this.player.getHitbox().getX();
        this.posY = this.player.getHitbox().getY();

        this.velX = 0;
        this.velY = 0;


        this.spawn();
    }
    public void spawn() {
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.WHITE);
        this.body.setOpacity(0.5);

        this.worldpane.getChildren().addAll(this.body);


    }

    public Enemy sense() {
        Enemy closestE = this.enemyList.get(0);
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
                    closestE = this.enemyList.get(i);

                } else {
                    closestEDis = closestEDis;
                }
            }

        }
        return closestE;

    }

    public void hunt(Enemy closestE){

        //this checks X
        if (this.posX - closestE.getBody().getX() > 0){
            //player is currently Right of enemy
            this.moveLeft();
            this.kill(closestE);
        }

        else if (this.posX - closestE.getBody().getX()< 0){
            //player is currently Left of enemy
            this.moveRight();
            this.kill(closestE);

        }

        //this checks Y
        if (this.posY - closestE.getBody().getY() > 0){
            //PProj is below enemy
            this.rise();
            this.kill(closestE);

        }

        else if (this.posY - closestE.getBody().getY() < 0){
            //PProj is above enemy
            this.fall();
            this.kill(closestE);

        }



    }

    public void kill(Enemy closestE){
        //interesection function
        Bounds ppjectBound = this.body.getBoundsInParent();
        Bounds enemyBound = closestE.getBody().getBoundsInParent();


        if(ppjectBound.intersects(enemyBound)){
            if(this.intersectedC < 1) {
                closestE.setHP(closestE.getHP() - 1);
                this.intersectedC = this.intersectedC+1;
                this.despawn();
            }

        }
    }

    public void moveLeft() {
        this.helperLeft();


    }

    public void helperLeft(){
        this.velX = this.velX - 2;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);

    }

    public void moveRight() {
        this.helperRight();

    }

    public void helperRight(){
        this.velX = this.velX + 2;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);

    }
    public void rise() {
        this.helperRise();


    }

    public void helperRise(){
        this.velY = this.velY - 1;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);

    }

    public void fall() {
        this.helperFall();

    }

    public void helperFall(){
        this.velY = this.velY + 1;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);

    }

    public void despawn() {
        this.worldpane.getChildren().remove(this.body);
        this.exists = false;

    }

    public void deathClock(){
        long time = System.currentTimeMillis();
        long lifeTime = 5000;
        if (time > this.spawnTime + lifeTime && this.exists) {
            this.despawn();
        }

    }
}
