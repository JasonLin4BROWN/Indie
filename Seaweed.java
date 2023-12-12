package indie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is the Seaweed class, the seaweed is your ranged type enemy;
 */
public class Seaweed implements Enemy{
    private double posX;
    private double posY;
    private Inventory inventory;
    private Image image;
    private ImageView imageView;
    private Rectangle body;
    private Pane worldpane;
    private int eHP;
    private boolean isAlive;
    private double yVel;
    private long lastattack;

    private ArrayList<EnemyProjectile> enemyProjectileArrayList;

    public Seaweed(Pane worldpane, double X, double y,Inventory inventory){
        Random rnd = new Random();
        this.eHP = rnd.nextInt(Constants.SEAWEED_MAX_HP- Constants.SEAWEED_MIN_HP) + Constants.SEAWEED_MIN_HP;
        this.isAlive = true;

        this.posX = X;
        this.posY = y;
        //Inventory
        this.inventory = inventory;


        this.yVel = 0;
        this.lastattack = 0;
        this.worldpane = worldpane;

        this.enemyProjectileArrayList = new ArrayList<EnemyProjectile>();

        //For Seaweed Specifically:
        this.image  = new Image("indie/Seaweed.png",Constants.ENEMY_SIZE,Constants.ENEMY_SIZE,false,true);
        this.imageView = new ImageView();

        this.Spawn(this.worldpane, this.posX, this.posY);
    }

    /**
     * This is Spawn method, its spawns the seaweed in
     */
    @Override
    public void Spawn(Pane worldpane, double X, double y) {
        //we will make a circle like object
        this.body = new Rectangle(Constants.ENEMY_SIZE,Constants.ENEMY_SIZE);
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.TRANSPARENT);

        this.imageView.setImage(this.image);
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);


        this.worldpane.getChildren().addAll(this.body, this.imageView);

    }

    /**
     * This is Update method, its contains the methods we want the seaweed to do when the game updates
     */
    @Override
    public void Update(Player player) {
        if(this.isAlive) {
            this.Sense(player);
            this.Die();
            this.projectileUpdater();

            //the seaweed shoots you every 5 seconds
            long time = System.currentTimeMillis();
            if (time > this.lastattack + Constants.SEAWEED_CD) {
                this.Attack(player);
                this.lastattack = time;
            }

        }

    }

    /**
     * This is Sense method, its contains the methods we want the seaweed does when it detects the player
     * and doesn't detect the player
     */
    @Override
    public void Sense(Player player) {
        if (Math.abs(player.posX - this.body.getX()) <= Constants.SEAWEED_SENSE_RANGE){
            this.antiReactX(player);
        }

        if (Math.abs(player.posX - this.body.getX()) <=  Constants.SEAWEED_SENSE_RANGE){
            if (Math.abs(player.posY - this.body.getY()) <=  Constants.SEAWEED_SENSE_RANGE) {
                this.antiReactY(player);
            }
        }

        else{
            this.DefaultBehavior();
        }

    }

    /**
     * This is DefaultBehavior method, its contains the methods we want the seaweed doesn't detect the player:
     */
    @Override
    public void DefaultBehavior() {
        double behaviorNum = Math.random() * 100;

        //40% time does nothing
        if (0<=behaviorNum && behaviorNum<=40){
            this.body.setX(this.posX);
        }
        //30% time moves right
        else if (40<behaviorNum && behaviorNum<=70){
            if (this.body.getY() > 0) {
                this.MoveLeft();
            }
        }
        else{
            if (this.body.getY() < 1000) {
                this.MoveRight();
            }
        }



    }

    @Override
    /**
     * This is ReactX method, checks where the player is and goes towards them on the x-axis
     */
    public void ReactX(Player player) {
        if (player.posX - this.body.getX() > 0){
            this.MoveRight();
        }

        else if (player.posX - this.body.getX() < 0){
            this.MoveLeft();

        }

        else {
            for(int i = 0; i<5;i++){
                this.antiReactX(player);
            }
        }



    }
    /**
     * This is ReactY method, checks where the player is and goes towards them on the y-axis
     */
    public void ReactY(Player player) {
        if (player.posY - this.body.getY() > 0){
            this.ReactX(player);
        }

        else if (player.posY - this.body.getY() < 0){
            this.Jump();
        }

        else {
            for(int i = 0; i<5;i++){
                this.antiReactX(player);
            }
        }



    }
    /**
     * This is antiReactX method, checks where the player is and moves away from them on x-axis
     */
    public void antiReactX(Player player) {
        if (player.posX - this.body.getX() > 0){
            this.MoveLeft();
        }

        else if (player.posX - this.body.getX() < 0){
            this.MoveRight();
        }

        else {
            this.MoveRight();
        }



    }
    /**
     * This is antiReactY method, checks where the player is and moves away from them on y-axis
     */
    public void antiReactY(Player player) {
        if (player.posY - this.body.getY() > 0){
            this.Jump();
        }

        else if (player.posY - this.body.getY() < 0){
            this.antiReactX(player);

        }

    }
    /**
     * this is the attack method, seaweed will spawn in a projectiles to kill the player
     */
    @Override
    public void Attack(Player player) {
        if (this.isAlive){
            this.enemyProjectileArrayList.add(new EnemyProjectile(this.worldpane, this, player));
        }
    }

    /**
     * this is the projectileUpdater method, it updates the seaweed's projectiles to do their stuff
     */
    public void projectileUpdater(){
        for(int i = 0; i < this.enemyProjectileArrayList.size(); i++){
            this.enemyProjectileArrayList.get(i).hunt();
            this.enemyProjectileArrayList.get(i).deathClock();
        }


    }

    /**
     * this is the MoveLeft method, it moves the seaweed left
     */
    @Override
    public void MoveLeft() {
        this.posX  = this.posX - Constants.SEAWEED_SPEED;
        this.positioning(this.posX, this.posY);

    }

    /**
     * this is the MoveRight method, it moves the seaweed right
     */
    @Override
    public void MoveRight() {
        this.posX  = this.posX + Constants.SEAWEED_SPEED;
        this.positioning(this.posX, this.posY);

    }

    /**
     * this is the Jump method, it allows the seaweed to jump, timeline for animation again
     */
    @Override
    public void Jump() {
        if(this.yVel ==0 && this.isAlive) {
            KeyFrame jumpframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.helperJump());
            Timeline timeline = new Timeline(jumpframe);
            timeline.setCycleCount(20);
            timeline.play();
        }
    }

    /**
     * this is the helperJump method, it allows the seaweed to jump
     */
    public void helperJump(){
        this.posY  = this.posY - Constants.SEAWEED_JUMP;
        this.positioning(this.posX, this.posY);

    }

    /**
     * this is the Fall method, it allows the seaweed to fall
     */
    @Override
    public void Fall(double platY) {
        this.doGravity(platY);
    }


    /**
     * This is the doGravity method, it simulates gravity on Seaweed
     */
    public void doGravity(double platY) {

        //This function simulates the acceleration of gravity over time.
        this.yVel += Constants.GRAVITY * Constants.DURATION;
        platY = platY - Constants.ENEMY_SIZE;

        if (this.posY >= platY) {
            this.yVel = 0;
            this.posY = platY ;
            this.positioning(this.posX, this.posY);


        }
        else {
            this.posY = this.posY+ 2 * this.yVel * Constants.DURATION;
            this.positioning(this.posX, this.posY);


        }
    }

    /**
     * This is the Die method, it tells the seaweed what to do when it dies
     */
    @Override
    public void Die() {
        if(this.isAlive) {
            if (this.eHP <= 0) {
                //remove the projectiles it spawned
                for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
                    this.enemyProjectileArrayList.get(i).despawn();
                }

                //remove it
                this.enemyProjectileArrayList.clear();
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);

                this.isAlive = false;

                //add to inventory a seaweed
                if(this.inventory.getInventory().size()<=Constants.INVENTORY_SIZE) {
                    this.inventory.getInventory().add(new SeaweedING());
                }

            }
            ;

            //same idea with if it falls out of the world
            if (this.getPosY() >= Constants.OUT_OF_BOUNDS) {
                for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
                    this.enemyProjectileArrayList.get(i).despawn();
                }

                this.enemyProjectileArrayList.clear();
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);

                this.isAlive = false;
                if(this.inventory.getInventory().size()<=Constants.INVENTORY_SIZE) {
                    this.inventory.getInventory().add(new SeaweedING());
                }

            }
            ;
        }


    }

    /**
     * This is the getStatus method, it returns Seaweed's alive status
     */
    @Override
    public boolean getStatus() {
        return this.isAlive;
    }

    /**
     * This is the getHP method, it returns Seaweed's HP
     */
    @Override
    public int getHP() {
        return this.eHP;
    }

    /**
     * This is the setHP method, it sets Seaweed's HP
     */
    @Override
    public void setHP(int newHP) {
        this.eHP = newHP;

    }

    /**
     * This is the getBody method, it sets Seaweed's hitbox
     */
    @Override
    public Rectangle getBody(){
        return this.body;
    };

    /**
     * This is the getPosX method, it sets Seaweed's posX
     */
    @Override
    public double getPosX(){return this.posX;};

    /**
     * This is the getPosY method, it sets Seaweed's posY
     */
    @Override
    public double getPosY(){return this.posY;};
    /**
     * This is the setPosX method, it sets Seaweed's posX
     */
    @Override
    public void setPosX(double posX){this.posX = posX;}
    /**
     * This is the positioning method, it sets Seaweed's posX and posY as well as its images
     */
    @Override
    public void positioning(double posX, double posY){
        this.posX = posX;
        this.posY = posY;

        this.body.setX(posX);
        this.body.setY(posY);

        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);
    }




}


