package wonders;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class EnemyProjectile implements Projectile{
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

    public EnemyProjectile(Pane worldpane, Enemy enemy, Player player){
        this.exists = true;
        this.spawnTime = System.currentTimeMillis();

        this.worldpane = worldpane;
        this.player =  player;

        this.body = new Rectangle(20,20);

        //this projectile should never intersect more than once
        this.intersectedC = 0;

        this.posX = enemy.getPosX();
        this.posY = enemy.getPosY();

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

    public void hunt(){

        //this checks X
        if (this.posX - this.player.getHitbox().getX() > 0){
            //player is currently Right of enemy
            this.moveLeft();
            this.kill();
        }

        else if (this.posX - this.player.getHitbox().getX()< 0){
            //player is currently Left of enemy
            this.moveRight();
            this.kill();

        }

        //this checks Y
        if (this.posY - this.player.getHitbox().getY() > 0){
            //PProj is below enemy
            this.rise();
            this.kill();

        }

        else if (this.posY - this.player.getHitbox().getY() < 0){
            //PProj is above enemy
            this.fall();
            this.kill();

        }



    }

    public void kill(){
        //interesection function
        Bounds ppjectBound = this.body.getBoundsInParent();
        Bounds playerBound = this.player.getHitbox().getBoundsInParent();


        if(ppjectBound.intersects(playerBound)){
            if(this.intersectedC < 1) {
                this.player.hurt();
                this.intersectedC = this.intersectedC+1;
                this.despawn();
            }

        }
    }

    public void moveLeft() {
        this.helperLeft();


    }

    public void helperLeft(){
        this.velX = this.velX - 0.2;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);

    }

    public void moveRight() {
        this.helperRight();

    }

    public void helperRight(){
        this.velX = this.velX + 0.2;
        this.posX = this.posX + this.velX;
        this.body.setX(this.posX);

    }
    public void rise() {
        this.helperRise();


    }

    public void helperRise(){
        this.velY = this.velY - 0.2;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);

    }

    public void fall() {
        this.helperFall();

    }

    public void helperFall(){
        this.velY = this.velY + 0.2;
        this.posY = this.posY + this.velY;
        this.body.setY(this.posY);

    }

    public void despawn() {
        this.worldpane.getChildren().remove(this.body);
        this.exists = false;

    }

    public void deathClock(){
        long time = System.currentTimeMillis();
        long lifeTime = 1000;
        if (time > this.spawnTime + lifeTime && this.exists) {
            this.despawn();
        }

    }
}
