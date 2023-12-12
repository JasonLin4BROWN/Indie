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


public class Player extends Rectangle {
    private Rectangle hitbox;
    private Pane worldpane;
    private ImageView currentimage;

    double posX;
    double posY;

    double xVel;
    double yVel;

    //remaking via hitboxes
    double headY;
    double feetY;
    double maxX;
    double minX;


    int HP;
    private Rectangle attackBox;

    private int aniTick = 1, aniIndex, aniSpeed = 2;

    private boolean isAlive;


    public Player(Pane worldpane){
        super();

        this.isAlive = true;
        this.HP = Constants.PLAYER_HP;

        this.hitbox = new Rectangle(Constants.HITBOX_WIDTH,Constants.HITBOX_HEIGHT);
        this.hitbox.setFill(Color.TRANSPARENT);
        this.hitbox.setOpacity(0.5);

        this.headY = this.hitbox.getBoundsInLocal().getMaxY();
        this.feetY = this.hitbox.getBoundsInLocal().getMinY();
        this.maxX = this.hitbox.getBoundsInLocal().getMaxX();
        this.minX = this.hitbox.getBoundsInLocal().getMinX();





        this.currentimage = new ImageView();

        Image pystand = new Image("indie/res/Eiffel/Paris Walk Animation.png");
        this.currentimage.setImage(pystand);
        this.currentimage.setFitHeight(283/(2));
        this.currentimage.setFitWidth(206/(1.75));
        this.currentimage.setPreserveRatio(false);



        this.posX = 50;
        this.posY = 300 - Constants.HITBOX_HEIGHT;
        this.yVel = 0;
        this.xVel = 0;


        this.positioning(this.posX,this.posY);


        this.worldpane = worldpane;
        this.worldpane.getChildren().addAll(this.hitbox, this.currentimage);




    };

    public void positioning(double X, double y){
        this.hitbox.setX(X);
        this.hitbox.setY(y);
        this.currentimage.setX(X-35);
        this.currentimage.setY(y-45);



    }
    public double getFeet(){
        this.feetY = this.posY + Constants.HITBOX_HEIGHT;
        return this.feetY;
    }

    public void setFeet(double Y){
        this.posY = Y - Constants.HITBOX_HEIGHT;

    }
    public void moveRight() {
        if (this.isAlive) {
            this.xVel = 10;


            KeyFrame leftframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.animateRight());
            Timeline timeline = new Timeline(leftframe);
            timeline.setCycleCount(2);
            timeline.play();
        }


    }

    public void moveLeft() {
        if (this.isAlive) {

            this.xVel = -10;

            KeyFrame leftframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.animateLeft());
            Timeline timeline = new Timeline(leftframe);
            timeline.setCycleCount(2);
            timeline.play();
        }



    }

    public void resizePos(){
        this.currentimage.setFitHeight(283/(2));
        this.currentimage.setFitWidth(206/(1.8));
        this.currentimage.setPreserveRatio(false);
        this.positioning(this.posX, this.posY);
    }

    public void animateRight() {
        //animation business
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex > 7) {
                aniIndex = 1;
            } else {
                this.currentimage.setImage(new Image("indie/res/Eiffel/Paris Walk Animation (" + aniIndex + ").png"));
                this.posX = this.posX + Constants.WALK_DIS;
                this.resizePos();
            }

        }
    }

    public void animateLeft() {

        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex--;
            if (aniIndex < 1) {
                aniIndex = 7;
            } else {
                this.currentimage.setImage(new Image("indie/res/Eiffel/Paris Walk Animation (" + aniIndex + ").png"));
                this.posX = this.posX - Constants.WALK_DIS;
                this.resizePos();
            }

        }
    }


    public void jump(){
        if(this.yVel ==0 && this.isAlive) {
            KeyFrame leftframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.jumphelper());
            Timeline timeline = new Timeline(leftframe);
            timeline.setCycleCount(20);
            timeline.play();
        }
    }

    public void jumphelper(){
        posY = posY - Constants.JUMP_HEIGHT;
        this.positioning(posX, posY);
    }

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
            this.posY = this.posY+ this.yVel * Constants.DURATION * 2;
            this.positioning(this.posX, this.posY);


        }

    }

    public void idle(){
        if(this.isAlive) {
            Image pystand = new Image("indie/res/Eiffel/Paris Walk Animation.png");
            this.currentimage.setImage(pystand);
            this.resizePos();
        }


    }
    public void attackLeft(){
        if(this.isAlive) {
            this.attackBox = new Rectangle(150, 70);
            this.attackBox.setX(this.getHitbox().getBoundsInParent().getMinX() - 50);
            this.attackBox.setY(this.getHitbox().getBoundsInParent().getMinY() + 20);
            this.attackBox.setFill(Color.WHITE);

            this.worldpane.getChildren().addAll(this.attackBox);
        }

    }

    public void attackRight(){
        if(this.isAlive) {
            this.attackBox = new Rectangle(150,70);
            this.attackBox.setX(this.getHitbox().getBoundsInParent().getMaxX());
            this.attackBox.setY(this.getHitbox().getBoundsInParent().getMinY()+20);
            this.attackBox.setFill(Color.WHITE);

            this.worldpane.getChildren().addAll(this.attackBox);
        }
    }

    /**
     * Dash mechanics
     */
    public void dash() {
        if(this.xVel !=0 && this.isAlive){
            this.yVel = 0;

            KeyFrame acceframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.dashAccel());
            Timeline timeline = new Timeline(acceframe);
            timeline.setCycleCount(10);
            timeline.play();

        }

    }


    public void dashAccel(){

        if (this.xVel < 0){
            this.xVel += - 5 * 0.5;
            this.yVel += -20 * 0.5;
            this.posX = this.posX + this.xVel * 0.5;
            this.posY = this.posY + this.yVel * 0.5;
            this.positioning(this.posX, this.posY);

        }

        if (this.xVel > 0){
            this.xVel +=  5 * 0.5;
            this.yVel += -20 * 0.5;
            this.posX = this.posX + this.xVel * 0.5;
            this.posY = this.posY + this.yVel * 0.5;
            this.positioning(this.posX, this.posY);

        }

    }

    public double getYVel() {
        return this.yVel;
    }

    public double getXVel() {
        return this.xVel;
    }

    public Rectangle getHitbox() {
        return this.hitbox;
    }

    public Rectangle getAttackBox(){
        return this.attackBox;
    }

    public boolean getAlive(){
        if(this.HP <= 0){
            this.isAlive = false;
        }

        else if(this.getFeet() >= 1300){
            this.isAlive = false;
        }

        else{
            this.isAlive = true;
        }


        return this.isAlive;
    }

    public void hurt(){
        this.HP = this.HP - 1;
    }

    public int getHP(){
        return this.HP;
    }

    public void setHP(int HP){
        this.HP = HP;
    }

    public void removePlayer(){
        this.isAlive = false;
        this.worldpane.getChildren().removeAll(this.hitbox, this.currentimage);


    }

}
