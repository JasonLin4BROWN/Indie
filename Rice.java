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

import java.util.Random;
/**
 * This is the Rice class, the rice is your melee type enemy;
 */
public class Rice implements Enemy {
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

    public Rice(Pane worldpane, double X, double y,Inventory inventory){
        Random rnd = new Random();
        //give a random HP to the Rice
        this.eHP = rnd.nextInt(Constants.RICE_MAX_HP - Constants.RICE_MIN_HP) + Constants.RICE_MIN_HP;
        this.isAlive = true;

        this.posX = X;
        this.posY = y;

        //Inventory
        this.inventory = inventory;


        this.yVel = 0;
        this.lastattack = 0;
        this.worldpane = worldpane;

        //For Rice Specifically:
        this.image  = new Image("indie/Enemies/white-rice-in-bow-free-png.png",Constants.ENEMY_SIZE,Constants.ENEMY_SIZE,false,true);
        this.imageView = new ImageView();


        this.Spawn(this.worldpane, this.posX, this.posY);
    }

    /**
     * This is Spawn method, its spawns the rice in
     */
    @Override
    public void Spawn(Pane worldpane, double X, double y) {
        //we will make a circle like object
        this.body = new Rectangle(Constants.ENEMY_SIZE,Constants.ENEMY_SIZE);
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.TRANSPARENT);

        //make the image
        this.imageView.setImage(this.image);
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY);

        this.worldpane.getChildren().addAll(this.body, this.imageView);

    }

    /**
     * This is Update method, its contains the methods we want the Rice to do when the game updates
     */
    @Override
    public void Update(Player player) {
        if(this.isAlive) {
            this.Sense(player);
            this.Die();

            //there is cooldown on its attack cycle to every 0.5 seconds, that way it doesn't instantly kill the player
            long time = System.currentTimeMillis();
            if (time > this.lastattack + Constants.RICE_CD) {
                this.Attack(player);
                this.lastattack = time;
            }

        }

    }
    /**
     * This is Sense method, it allows the enemy to react to the player depending on if they are in range or not
     */
    @Override
    public void Sense(Player player) {
        if (Math.abs(player.posX - this.body.getX()) <= Constants.RICE_SENSE_RANGE){
            this.ReactX(player);
        }

        if (Math.abs(player.posX - this.body.getX()) <= Constants.RICE_SENSE_RANGE){
            if (Math.abs(player.posY - this.body.getY()) <= Constants.RICE_SENSE_RANGE) {
                this.ReactY(player);
            }
        }

        else{
            this.DefaultBehavior();
        }

    }
    /**
     * This is DefaultBehavior method, has the default behavior of Rice when it doesn't sense the player
     */
    @Override
    public void DefaultBehavior() {
        double behaviorNum = Math.random() * 100;
        //40% of the time it will do nothing
        if (0<=behaviorNum && behaviorNum<=40){
            this.body.setX(this.posX);
        }

        //30% of the time it will move left
        else if (40<behaviorNum && behaviorNum<=70){
            if (this.body.getY() > 0) {
                this.MoveLeft();
            }
        }

        //otherwise it will move right
        else{
            if (this.body.getY() < 1000) {
                this.MoveRight();
            }
        }



    }

    /**
     * This is ReactX method, checks where the player is and goes towards them on the x-axis
     */
    @Override
    public void ReactX(Player player) {
        //checks where the player is and goes towards them on the x-axis
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
    @Override
    public void ReactY(Player player) {
        //if below move on X towards them
        if (player.posY - this.body.getY() > 0){
            this.ReactX(player);
        }

        //if above jump towards them
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
    @Override
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
    @Override
    public void antiReactY(Player player) {
        if (player.posY - this.body.getY() > 0){
            this.Jump();
        }

        else if (player.posY - this.body.getY() < 0){
            this.antiReactX(player);

        }
    }

    /**
     * This is Attack method, when the player intersects with rice, they are hurt
     */
    @Override
    public void Attack(Player player) {
        if (this.body.getBoundsInParent().intersects(player.getHitbox().getBoundsInParent()) && this.isAlive){
            player.hurt();


        }
    }

    /**
     * This is MoveLeft method, it moves left
     */
    @Override
    public void MoveLeft() {
        this.posX  = this.posX - Constants.RICE_SPEED;
        this.imageView.setX(this.posX);
    }


    /**
     * This is MoveRight method, it moves right
     */
    @Override
    public void MoveRight() {
        this.posX  = this.posX +  Constants.RICE_SPEED;
        this.imageView.setX(this.posX);
    }


    /**
     * This is Jump method, it allows rice to jump, note that it is built with a timeline to allow for actual
     * jumping animations
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
     * This is Jump helperJump, it allows rice to jump
     */
    @Override
    public void helperJump(){
        this.posY  = this.posY - Constants.RICE_JUMP;
        this.imageView.setY(this.posY);

    }

    /**
     * This is the Fall method, it allows rice to fall
     */
    @Override
    public void Fall(double platY) {
         this.doGravity(platY);
    }

    /**
     * This is the doGravity method, it simulates gravity on Rice
     */
    @Override
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
            this.posY = this.posY+ 2* this.yVel * Constants.DURATION;
            this.positioning(this.posX, this.posY);


        }
    }

    /**
     * This is the Die method, it tells Rice what to do when it dies
     */
    @Override
    public void Die() {
        if(this.isAlive) {
            if (this.eHP <= 0) {
                //remove from the world
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);


                //put a Rice ingredient into the player inventory if they have less than 20 items
                this.isAlive = false;
                if(this.inventory.getInventory().size()<=Constants.INVENTORY_SIZE) {
                    this.inventory.getInventory().add(new RiceING());
                }
            }
            ;

            if (this.getPosY() >= Constants.OUT_OF_BOUNDS) {
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);

                this.isAlive = false;
                if(this.inventory.getInventory().size()<=Constants.INVENTORY_SIZE) {
                    this.inventory.getInventory().add(new RiceING());
                }

            }
            ;
        }


    }

    /**
     * This is the getStatus method, it returns Rice's alive status
     */
    @Override
    public boolean getStatus() {
        return this.isAlive;

    }

    /**
     * This is the getHP method, it returns Rice's HP
     */
    @Override
    public int getHP() {
        return this.eHP;
    }

    /**
     * This is the setHP method, it sets Rice's HP
     */
    @Override
    public void setHP(int newHP) {
        this.eHP = newHP;

    }
    /**
     * This is the getBody method, it gets Rice's hitbox
     */
    @Override
    public Rectangle getBody(){
        return this.body;
    };

    /**
     * This is the getPosX method, it gets posX
     */
    @Override
    public double getPosX(){return this.posX;};
    @Override
    /**
     * This is the getPosY method, it gets posY
     */
    public double getPosY(){return this.posY;};

    /**
     * This is the setPosX method, it sets posX
     */
    @Override
    public void setPosX(double posX){this.posX = posX;}

    /**
     * This is the positioning method, it sets the image and position of the body
     */
    @Override
    public void positioning(double posX, double posY){
        this.posX = posX;
        this.posY = posY;

        this.body.setX(posX);
        this.body.setY(posY);
        this.imageView.setX(posX);
        this.imageView.setY(posY);
    }




}

