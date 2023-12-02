package wonders;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {
    BorderPane root;
    Pane worldpane;
    Player player;
    private long start;
    private int lastPortal;

    private Boolean right;
    private Boolean left;
    private Boolean up;
    private Boolean down;
    private WorldOrganizer worldorganizer;


    private double col;
    private double enemyCol;
    private boolean gameState;
    private Healthbar healthbar;


    private ArrayList<PlayerProjectile> pProjList;


    public Game(BorderPane root, Pane worldpane, WorldOrganizer worldorganizer){
        this.gameState = true;

        //this is for recognizing portal hitboxes
        this.lastPortal = 0;

        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;



        this.root = root;
        this.worldpane = worldpane;
        this.worldorganizer = worldorganizer;

        this.player = new Player(this.worldpane);

        this.setupTimeline();
        this.rapidTimeline();

        this.worldpane .setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        this.worldpane .setOnKeyReleased((KeyEvent e) -> this.handleKeyRelease(e));

        this.worldpane .setFocusTraversable(true);


        //setting up the situation
        this.player.setFeet(this.worldorganizer.getyStart());
        this.col = this.player.getFeet();
        this.enemyCol = 600;
        this.pProjList = new ArrayList<PlayerProjectile>();
        this.healthbar = new Healthbar(this.worldpane, this.player);


    }

    public void setupTimeline(){
        //setting a start time
        this.start = System.currentTimeMillis();

        //Setting up timeline which updates the label as well as moves the cabbageMonster
        KeyFrame kf = new KeyFrame(
                Duration.millis(17),
                (ActionEvent e) -> this.updateLabel());

        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void rapidTimeline(){
        //setting a start time
        this.start = System.currentTimeMillis();

        //Setting up timeline which updates the label as well as moves the cabbageMonster
        KeyFrame kf = new KeyFrame(
                Duration.millis(1),
                (ActionEvent e) -> this.rapidUpdates());

        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void updateLabel() {
        this.player.fall(this.col);
        this.handleResponse();

        this.wallLRCollisions();
        this.handleEnemy();

        this.portalCollisions();
        this.updatePproject();
        this.healthbar.update();

    }

    private void rapidUpdates(){
        this.checkAlive();
        this.gameOver();
        this.wallLRCollisions();
        this.col = this.wallTDCollisions();
    }

    public double platformCollisions(){

        double platcol = 700;

        for (int i = 0; i < this.worldorganizer.getPlatList().size(); i++){
            Platforms platforms = this.worldorganizer.getPlatList().get(i);
            Rectangle rect = platforms.getRect();
            Bounds bounds = this.player.getHitbox().getBoundsInParent();


            if(rect.getBoundsInParent().intersects(bounds) && this.player.getFeet() <= (rect.getY()+20)){
                platcol = rect.getBoundsInParent().getMinY();
                System.out.println((rect.getY()+rect.getBoundsInParent().getMaxY()));
                break;

                //}
            }

            else{
                System.out.println((rect.getY()));
                platcol = 700;
                //this.player.fall(platcol);


            }
        }
        //the problem with this appears that it will always return the else statement
        return platcol;


    }

    /**
     * PLayer specific business:
     * Attacks:
     * Checking if alive
     *
     */
    public void wallLRCollisions() {

        //handling left and right intersections
        for (int i = 0; i < this.worldorganizer.getwallList().size(); i++) {
            Wall wall = this.worldorganizer.getwallList().get(i);
            Rectangle LRrect = wall.getLRhitbox();
            Bounds LRrectbounds = LRrect.getBoundsInParent();
            Bounds playerbounds = this.player.getHitbox().getBoundsInParent();

            //left intersection
            if (LRrectbounds.intersects(playerbounds)) {
                if (this.player.posX + Constants.HITBOX_WIDTH <= LRrectbounds.getMinX() + 100) {

                    this.player.posX = LRrectbounds.getMinX() - Constants.HITBOX_WIDTH;
                    this.player.positioning(this.player.posX, this.player.posY);


                }

                //Right intersection
                if (this.player.posX >= LRrectbounds.getMaxX() - 100) {

                    this.player.posX = LRrectbounds.getMaxX();
                    this.player.positioning(this.player.posX, this.player.posY);
                }

            }
        }
    }

public double wallTDCollisions(){
        //handling top and bottom intersections
    double wallcol = 2000;
    for (int i = 0; i < this.worldorganizer.getwallList().size(); i++){
            Wall wall = this.worldorganizer.getwallList().get(i);
            Rectangle rect = wall.getRect();
            Bounds rectbounds = rect.getBoundsInParent();
            Bounds playerbounds = this.player.getHitbox().getBoundsInParent();
                    //top intersection
            if(rectbounds.intersects(playerbounds) && (this.player.getFeet() <= rect.getY()+50)) {
                    wallcol = rect.getBoundsInParent().getMinY();
                    break;
                    //
            }
            else{
                wallcol = 2000;

            }

        }
        return wallcol;

    }
    public void portalCollisions() {

        //handling intersections
        for (int i = 0; i < this.worldorganizer.getportalList().size(); i++) {
            Portal portal = this.worldorganizer.getportalList().get(i);
            Rectangle portalRect = portal.getRect();
            Bounds portalbounds = portalRect.getBoundsInParent();
            Bounds playerbounds = this.player.getHitbox().getBoundsInParent();

            if (portalbounds.intersects(playerbounds)) {
                if(this.lastPortal<1) {
                    portal.sendWorld();
                    this.lastPortal++;
                }

            }
        }
    }


    public void playerattacks() {
        for (int i = 0; i < this.worldorganizer.getenemyList().size(); i++) {
            Enemy enemy = this.worldorganizer.getenemyList().get(i);
            Rectangle enemyBody = enemy.getBody();
            Bounds enemyBounds = enemyBody.getBoundsInParent();
            Bounds playerattackbounds = this.player.getAttackBox().getBoundsInParent();

            if (playerattackbounds.intersects(enemyBounds)){
                enemy.setHP(enemy.getHP()-1);

                //pushback
                KeyFrame kf = new KeyFrame(
                        Duration.millis(1),
                        (ActionEvent e) ->enemy.antiReactX(this.player));

                Timeline timeline = new Timeline(kf);
                timeline.setCycleCount(35);
                timeline.play();


            }

        }
    }

    public void checkAlive(){
        if (this.player.getAlive()){
            //player is alive
            this.gameState = true;
        }

        if(!this.player.getAlive()){
            //player is dead
            this.gameState = false;
        }

        System.out.println(this.gameState);
    }

    public void gameOver(){
        if(!this.gameState){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.8);

            this.worldpane.setEffect(colorAdjust);


            Label gameOver = new Label("Game Over");
            gameOver.setTranslateX(Constants.SCENE_WIDTH/2 - 300);
            gameOver.setTranslateY(Constants.SCENE_HEIGHT/2);
            gameOver.setTextFill(Color.WHITE);
            gameOver.setFont(new Font("Arial", 50.0));

            this.worldpane.getChildren().addAll(gameOver);

        }
    }


    /**
     * for Player Projectiles:
     */

    public void updatePproject(){
        for(int i = 0; i < this.pProjList.size(); i++){
            Enemy closestE = this.pProjList.get(i).sense();
            this.pProjList.get(i).hunt(closestE);
            this.pProjList.get(i).deathClock();


        }
    }

    public void clearpProjList(){
        this.pProjList.clear();
    }



    /**
     * for the enemies:
     */

    public void wallLRCollisionsEnemy(Enemy currentE) {
            //handling left and right intersections
            for (int i = 0; i < this.worldorganizer.getwallList().size(); i++) {
                Wall wall = this.worldorganizer.getwallList().get(i);
                Rectangle LRrect = wall.getLRhitbox();
                Bounds LRrectbounds = LRrect.getBoundsInParent();
                Bounds enemybounds = currentE.getBody().getBoundsInParent();

                //left intersection
                if (LRrectbounds.intersects(enemybounds)) {
                    if (currentE.getPosX() + 100 <= LRrectbounds.getMinX() + 100) {

                        currentE.setPosX(LRrectbounds.getMinX() - 100);


                    }

                    //Right intersection
                    if (currentE.getPosX()>= LRrectbounds.getMaxX() - 100) {

                        currentE.setPosX(LRrectbounds.getMaxX());
                    }

                }
            }

    }

    public double wallTDCollisionsEnemy(Enemy currentE){
        //handling top and bottom intersections
        double wallcol = 2000;
        for (int i = 0; i < this.worldorganizer.getwallList().size(); i++){
            Wall wall = this.worldorganizer.getwallList().get(i);
            Rectangle rect = wall.getRect();
            Bounds rectbounds = rect.getBoundsInParent();
            Bounds enemybounds = currentE.getBody().getBoundsInParent();
            //top intersection
            if(rectbounds.intersects(enemybounds) && (currentE.getPosY() + 100 <= rect.getY()+100)) {
                wallcol = rect.getBoundsInParent().getMinY();
                break;
                //
            }
            else{
                wallcol = 2000;

            }

        }
        return wallcol;

    }

    public void handleEnemy(){
        for (int i = 0; i < this.worldorganizer.getenemyList().size(); i++) {
            Enemy currentE = this.worldorganizer.getenemyList().get(i);

            currentE.Update(this.player);
            this.wallLRCollisionsEnemy(currentE);
            this.enemyCol = this.wallTDCollisionsEnemy(currentE);
            currentE.Fall(this.enemyCol);
        }

    }


    /** Handling those key presses
     *
     */
    public void handleKeyPress(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case RIGHT:
                this.right = true;
                break;
            case LEFT:
                this.left = true;
                break;

            case UP:

                //fix this jump function later
                this.up =  true;
                this.player.jump();
                break;

            case DOWN:
                this.down = true;
                this.player.hurt();
                break;

            case A:
                KeyFrame delayframes = new KeyFrame(
                        Duration.millis(50),
                        (ActionEvent s) -> this.playerAttL());
                Timeline timeline = new Timeline(delayframes);
                timeline.setCycleCount(1);
                timeline.play();


                break;

            case D:

                KeyFrame delayframesD = new KeyFrame(
                        Duration.millis(50),
                        (ActionEvent s) -> this.playerAttR());
                Timeline timelineD = new Timeline(delayframesD);
                timelineD.setCycleCount(1);
                timelineD.play();

                break;

            case ESCAPE:
                System.exit(0);
                break;

            case W:
                this.pProjList.add(
                        new PlayerProjectile(this.worldpane, this.player, this.worldorganizer.getenemyList()));

        }
        e.consume();
    }
    public void playerAttL(){
        this.player.attackLeft();
        this.playerattacks();
        this.worldpane.getChildren().remove(this.player.getAttackBox());

    }
    public void playerAttR(){
        this.player.attackRight();
        this.playerattacks();
        this.worldpane.getChildren().remove(this.player.getAttackBox());
    }

    public void handleKeyRelease(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case RIGHT:
                this.right = false;
                break;
            case LEFT:
                this.left = false;
                break;

            case UP:
                this.up = false;
                break;

            case DOWN:
                this.down = false;
                break;

            default:
                this.player.idle();
                break;
        }
        e.consume();
    }

    public void handleResponse() {
        if (this.right) {
            this.player.moveRight();
        }
        if (this.left) {
            this.player.moveLeft();
        }
        if (this.up) {
            //this.player.jump();
        }
        if (this.down){
            this.player.fall(500);

        }

        if(!this.right && !this.left && !this.up && !this.down){
            this.player.idle();
        }
    }


}