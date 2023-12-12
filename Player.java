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

/**
 * the Player class, Eiffel, is who you play as in this world
 */
public class Player {
    private Rectangle hitbox;
    private Pane worldpane;
    private ImageView currentimage;
    double posX;
    double posY;
    double xVel;
    double yVel;
    double feetY;
    int HP;
    private Rectangle attackBox;
    //Animation Instance Variables
    private int aniTick = 1, aniIndex, aniSpeed = 2;
    private boolean isAlive;


    public Player(Pane worldpane){
        //Set all instance variables, most are self-explanatory
        this.isAlive = true;
        this.HP = Constants.PLAYER_HP;
        this.hitbox = new Rectangle(Constants.HITBOX_WIDTH,Constants.HITBOX_HEIGHT);
        this.hitbox.setFill(Color.TRANSPARENT);

        //as Eiffel is a box, we want her feet specifically for platforms and moving
        this.feetY = this.posY + Constants.HITBOX_HEIGHT;

        //images
        this.currentimage = new ImageView();
        Image pystand = new Image("indie/Paris Walk Animation.png");
        this.currentimage.setImage(pystand);
        this.currentimage.setFitHeight(Constants.PLAYER_IMG_HEIGHT);
        this.currentimage.setFitWidth(Constants.PLAYER_IMG_WIDTH);
        this.currentimage.setPreserveRatio(false);


        //positioning
        this.posX = Constants.STARTING_X;
        this.posY = 0;
        this.yVel = 0;
        this.xVel = 0;
        this.positioning(this.posX,this.posY);


        this.worldpane = worldpane;
        this.worldpane.getChildren().addAll(this.hitbox, this.currentimage);
    };

    /**
     * the positioning method, it repositions Eiffel's hitbox and her image
     */
    public void positioning(double X, double y){
        this.hitbox.setX(X);
        this.hitbox.setY(y);
        this.currentimage.setX(X-Constants.PLAYER_IMG_X_CORRECTION);
        this.currentimage.setY(y-Constants.PLAYER_IMG_Y_CORRECTION);

    }

    /**
     * the getFeet method, gets where Eiffel's feet actually touches the ground
     */
    public double getFeet(){
        this.feetY = this.posY + Constants.HITBOX_HEIGHT;
        return this.feetY;
    }
    /**
     * the setFeet method, sets where Eiffel's feet actually touches the ground
     */
    public void setFeet(double Y){
        this.posY = Y - Constants.HITBOX_HEIGHT;

    }
    /**
     * the moveRight method, it handles moving right
     */
    public void moveRight() {
        if (this.isAlive) {
            this.xVel = 10;

            // we call animate right twice for a more smooth animation and look to her movement
            for(int i = 0; i<2; i++){
                this.animateRight();
            }
        }
    }

    /**
     * the moveLeft method, it handles moving left
     */
    public void moveLeft() {
        if (this.isAlive) {

            this.xVel = -10;
            for(int i = 0; i<2; i++){
                this.animateLeft();
            }
        }
    }


    /**
     * the resizePos method, it handles the resizing of Eiffel's image
     */
    public void resizePos(){
        this.currentimage.setFitHeight(Constants.PLAYER_IMG_HEIGHT);
        this.currentimage.setFitWidth(Constants.PLAYER_IMG_WIDTH);
        this.currentimage.setPreserveRatio(false);
        this.positioning(this.posX, this.posY);
    }

    /**
     * the animateRight method, animates the movement of Eiffel right
     */
    public void animateRight() {
        //animation business
        this.aniTick++;
        if (this.aniTick >= this.aniSpeed) {
            this.aniTick = 0;
            this.aniIndex++;
            if (this.aniIndex > 7) {
                this.aniIndex = 1;
            } else {
                this.currentimage.setImage(new Image("indie/Paris Walk Animation (" + this.aniIndex + ").png"));
                this.posX = this.posX + Constants.WALK_DIS;
                this.resizePos();
            }

        }
    }

    /**
     * the animateLeft method, animates the movement of Eiffel left
     */
    public void animateLeft() {

        this.aniTick++;
        if (this.aniTick >= this.aniSpeed) {
            this.aniTick = 0;
            this.aniIndex--;
            if (this.aniIndex < 1) {
                this.aniIndex = 7;
            } else {
                this.currentimage.setImage(new Image("indie/Paris Walk Animation (" + this.aniIndex + ").png"));
                this.posX = this.posX - Constants.WALK_DIS;
                this.resizePos();
            }

        }
    }

    /**
     * the jump method, animates the movement jumping of Eiffel.
     */
    public void jump(){
        //you can only jump when standing on something
        if(this.yVel ==0 && this.isAlive) {
            //note we use a timeline here to simulate the movement of jumping
            KeyFrame jumpframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.jumphelper());
            Timeline timeline = new Timeline(jumpframe);
            timeline.setCycleCount(20);
            timeline.play();
        }
    }
    /**
     * the jumphelper method, it helps Eiffel jump
     */
    public void jumphelper(){
        this.posY = this.posY - Constants.JUMP_HEIGHT;
        this.positioning(this.posX, this.posY);
    }

    /**
     * the jump method, animates the movement jumping of Eiffel.
     */
    public void fall(double platY){
        if(this.isAlive) {
            KeyFrame leftframe = new KeyFrame(
                    Duration.millis(20),
                    (ActionEvent e) -> this.doGravity(platY));
            Timeline timeline = new Timeline(leftframe);
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    /**
     * the doGravity method, allows gravity to act on Eiffel
     */
    public void doGravity(double platY) {

        //This function simulates the acceleration of gravity over time.
        this.yVel += Constants.GRAVITY * Constants.DURATION;
        platY = platY - Constants.HITBOX_HEIGHT;

        if (this.posY >= platY) {
            this.yVel = 0;
            this.posY = platY ;
            this.positioning(this.posX, this.posY);

        }
        else {
            //we use a modifier to make gravity feel more natural
            this.posY = this.posY+ this.yVel * Constants.DURATION * 2;
            this.positioning(this.posX, this.posY);


        }

    }
    /**
     * the idle method, when Eiffel is doing nothing she will return to her idle stance
     */
    public void idle(){
        if(this.isAlive) {
            Image pystand = new Image("indie/Paris Walk Animation.png");
            this.currentimage.setImage(pystand);
            this.resizePos();
        }
    }
    /**
     * the attackLeft method, allows Eiffel to attack to the left
     */
    public void attackLeft(){
        if(this.isAlive) {
            //we create an attack box which if the enemy intersects will damage them and push them back
            this.attackBox = new Rectangle(Constants.ATTACK_BOX_WIDTH, Constants.ATTACK_BOX_HEIGHT);
            this.attackBox.setX(this.getHitbox().getBoundsInParent().getMinX() - Constants.ATTACK_BOX_WIDTH);
            this.attackBox.setY(this.getHitbox().getBoundsInParent().getMinY() + Constants.ATTACK_BOX_CORRECTION_Y);
            this.attackBox.setFill(Color.WHITE);

            this.worldpane.getChildren().addAll(this.attackBox);
        }

    }
    /**
     * the attackRight method, allows Eiffel to attack to the right
     */
    public void attackRight(){
        if(this.isAlive) {
            this.attackBox = new Rectangle(Constants.ATTACK_BOX_WIDTH, Constants.ATTACK_BOX_HEIGHT);
            this.attackBox.setX(this.getHitbox().getBoundsInParent().getMaxX());
            this.attackBox.setY(this.getHitbox().getBoundsInParent().getMinY()+Constants.ATTACK_BOX_CORRECTION_Y);
            this.attackBox.setFill(Color.WHITE);

            this.worldpane.getChildren().addAll(this.attackBox);
        }
    }

    /**
     * the dash method, allows Eiffel to dash
     */
    public void dash() {
        if(this.xVel !=0 && this.isAlive){
            this.yVel = 0;
            //fluid motion of dashing
            //note that I did not limit dashing, mainly because it is funner that way
            KeyFrame acceframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.dashAccel());
            Timeline timeline = new Timeline(acceframe);
            timeline.setCycleCount(8);
            timeline.play();

        }

    }

    /**
     * the dashAccel method, allows Eiffel to accelerate into the dash based on the direction she was
     * moving in.
     */
    public void dashAccel(){

        if (this.xVel < 0){
            this.xVel += - Constants.DASH_SPEED_X * Constants.DASH_TIME;
            this.yVel += -Constants.DASH_SPEED_Y * Constants.DASH_TIME;
            this.posX = this.posX + this.xVel * Constants.DASH_TIME;
            this.posY = this.posY + this.yVel * Constants.DASH_TIME;
            this.positioning(this.posX, this.posY);

        }

        if (this.xVel > 0){
            this.xVel +=  Constants.DASH_SPEED_X  * Constants.DASH_TIME;
            this.yVel += -Constants.DASH_SPEED_Y * Constants.DASH_TIME;
            this.posX = this.posX + this.xVel * Constants.DASH_TIME;
            this.posY = this.posY + this.yVel * Constants.DASH_TIME;
            this.positioning(this.posX, this.posY);

        }

    }

    /**
     * the getYVel method, it gets the y velocity of the Player
     */
    public double getYVel() {
        return this.yVel;
    }

    /**
     * the getXVel method, it gets the x velocity of the Player
     */
    public double getXVel() {
        return this.xVel;
    }

    /**
     * the getHitbox method, it gets the hitbox of the player
     */
    public Rectangle getHitbox() {
        return this.hitbox;
    }

    /**
     * the getAttackBox method, it gets the attackBox of the player
     */
    public Rectangle getAttackBox(){
        return this.attackBox;
    }

    /**
     * the getAlive method, it checks if the player is alive
     */
    public boolean getAlive(){
        if(this.HP <= 0){
            this.isAlive = false;
        }

        else if(this.getFeet() >= Constants.OUT_OF_BOUNDS){
            this.isAlive = false;
        }

        else{
            this.isAlive = true;
        }


        return this.isAlive;
    }

    /**
     * the hurt method, it reduces the player's HP by 1
     */
    public void hurt(){
        this.HP = this.HP - 1;
    }
    /**
     * the getHP method, it gets the player's current HP
     */
    public int getHP(){
        return this.HP;
    }
    /**
     * the setHP method, it sets the player's current HP
     */
    public void setHP(int HP){
        this.HP = HP;
    }
    /**
     * the removePlayer method, it removes the player from view
     */
    public void removePlayer(){
        this.isAlive = false;
        this.worldpane.getChildren().removeAll(this.hitbox, this.currentimage);


    }
    /**
     * the boundYPlayer method, it ensures the player cannot just jump of the screen in the y-direction
     */
    public void boundYPlayer(){
        if (this.posY< Constants.OUT_OF_BOUNDS_TOP){
            this.posY = Constants.OUT_OF_BOUNDS_TOP;
            this.positioning(this.posX,this.posY);
        }


    }

}
