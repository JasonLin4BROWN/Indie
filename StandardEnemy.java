package indie;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Random;

public class StandardEnemy implements Enemy{
    private double posX;
    private double posY;
    private Rectangle body;
    private Pane worldpane;
    private int eHP;
    private boolean isAlive;
    private double yVel;
    private long lastattack;

    public StandardEnemy(Pane worldpane, double X, double y){
        Random rnd = new Random();
        this.eHP = rnd.nextInt(7 - 4) + 4;
        this.isAlive = true;

        this.posX = X;
        this.posY = y;
        this.yVel = 0;
        this.lastattack = 0;
        this.worldpane = worldpane;

        this.Spawn(this.worldpane, this.posX, this.posY);
    }

    @Override
    public void Spawn(Pane worldpane, double X, double y) {
        //we will make a circle like object
        this.body = new Rectangle(100,100);
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.GREEN);

        this.worldpane.getChildren().addAll(this.body);

    }

    @Override
    public void Update(Player player) {
        if(this.isAlive) {
            this.Sense(player);
            this.Die();

            long time = System.currentTimeMillis();
            long coolDownTime = 500;
            if (time > this.lastattack + coolDownTime) {
                this.Attack(player);
                this.lastattack = time;
            }

        }

    }

    @Override
    public void Sense(Player player) {
        if (Math.abs(player.posX - this.body.getX()) <= 300){
            this.ReactX(player);
        }

        if (Math.abs(player.posX - this.body.getX()) <= 300){
            if (Math.abs(player.posY - this.body.getY()) <= 300) {
                this.ReactY(player);
            }
        }

        else{
            this.DefaultBehavior();
        }

    }

    @Override
    public void DefaultBehavior() {
        double behaviorNum = Math.random() * 100;
        if (0<=behaviorNum && behaviorNum<=40){
            this.body.setX(this.posX);
        }

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
    public void ReactX(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posX - this.body.getX() > 0){
            //player is currently right of enemy
            this.MoveRight();


        }

        else if (player.posX - this.body.getX() < 0){
            //player is currently left of enemy
            this.MoveLeft();

        }

        else {
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);

        }

        

    }
    public void ReactY(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posY - this.body.getY() > 0){
            //player is currently right of enemy
            this.ReactX(player);
        }

        else if (player.posY - this.body.getY() < 0){
            //player is currently left of enemy
            this.Jump();
        }

        else {
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);

        }



    }

    public void antiReactX(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posX - this.body.getX() > 0){
            //player is currently right of enemy
            this.MoveLeft();
        }

        else if (player.posX - this.body.getX() < 0){
            //player is currently left of enemy
            this.MoveRight();
        }

        else {
            this.MoveRight();
        }



    }

    public void antiReactY(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posY - this.body.getY() > 0){
            //player is currently below of enemy
            this.Jump();
        }

        else if (player.posY - this.body.getY() < 0){
            //player is currently left of enemy
            this.antiReactX(player);

        }

        else {
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);

        }



    }

    @Override
    public void Attack(Player player) {
        if (this.body.getBoundsInParent().intersects(player.getHitbox().getBoundsInParent()) && this.isAlive){
            long time = System.currentTimeMillis();
            this.lastattack = 0;
            long coolDownTime = 10000;
            if (time > this.lastattack + coolDownTime) {
                this.attackHelper(player);
                this.body.setFill(Color.GREEN);
                this.lastattack = time;

            }

        }


    }

    public void attackHelper(Player player){
        this.body.setFill(Color.WHITE);
        player.hurt();


    }

    @Override
    public void MoveLeft() {
        KeyFrame leftframe = new KeyFrame(
                Duration.millis(50),
                (ActionEvent e) -> this.helperLeft());
        Timeline timeline = new Timeline(leftframe);
        timeline.setCycleCount(2);
        timeline.play();

    }

    public void helperLeft(){
        this.posX  = this.posX - 1;
        this.body.setX(this.posX);
    }

    @Override
    public void MoveRight() {
        KeyFrame rightframe = new KeyFrame(
                Duration.millis(50),
                (ActionEvent e) -> this.helperRight());
        Timeline timeline = new Timeline(rightframe);
        timeline.setCycleCount(2);
        timeline.play();
    }

    public void helperRight(){
        this.posX  = this.posX + 1;
        this.body.setX(this.posX);
    }

    @Override
    public void Jump() {
        KeyFrame rightframe = new KeyFrame(
                Duration.millis(50),
                (ActionEvent e) -> this.helperJump());
        Timeline timeline = new Timeline(rightframe);
        timeline.setCycleCount(2);
        timeline.play();
    }

    public void helperJump(){
        this.posY  = this.posY - 10;
        this.body.setY(this.posY);
    }

    @Override
    public void Fall(double platY) {
        KeyFrame leftframe = new KeyFrame(
                Duration.millis(20),
                (ActionEvent e) -> this.doGravity(platY));
        Timeline timeline = new Timeline(leftframe);
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void doGravity(double platY) {

        //This function simulates the acceleration of gravity over time.
        this.yVel += Constants.GRAVITY * Constants.DURATION;
        platY = platY - 100;

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

    @Override
    public void Die() {
        if (this.eHP == 0){
            this.worldpane.getChildren().remove(this.body);
            this.isAlive = false;
        };

        if (this.getPosY() >= 1300){
            this.worldpane.getChildren().remove(this.body);
            this.isAlive = false;
        };


    }

    @Override
    public boolean getStatus() {
        return this.isAlive;

    }

    @Override
    public int getHP() {
        return this.eHP;
    }

    @Override
    public void setHP(int newHP) {
        this.eHP = newHP;

    }
    @Override
    public Rectangle getBody(){
        return this.body;
    };

    public double getPosX(){return this.posX;};
    public double getPosY(){return this.posY;};

    public void setPosX(double posX){this.posX = posX;}
    public void setPosY(double posY){this.posY = posY;}

    public void positioning(double posX, double posY){
        this.posX = posX;
        this.posY = posY;

        this.body.setX(posX);
        this.body.setY(posY);
    }




}
