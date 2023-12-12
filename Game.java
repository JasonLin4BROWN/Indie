package indie;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This is the Game class, it has all the logic of our platformer,metroidvania game
 */
public class Game {
    private Pane worldpane;
    private Player player;
    private Inventory inventory;
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
    private long lastRattack;

    public Game(Pane worldpane, WorldOrganizer worldorganizer, Player player){
        //instantiate all private instance variables
        this.gameState = true;
        this.lastPortal = 0;
        this.lastRattack = 0;

        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;

        this.worldpane = worldpane;
        this.worldorganizer = worldorganizer;

        this.player = player;
        this.inventory = this.worldorganizer.getInventory();
        this.intitalization();

        //setting up timeline
        this.setupTimeline();

        //setting up the situation
        this.pProjList = new ArrayList<PlayerProjectile>();
        this.healthbar = new Healthbar(this.worldpane, this.player);
        this.worldpane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        this.worldpane.setOnKeyReleased((KeyEvent e) -> this.handleKeyRelease(e));

        this.worldpane.setFocusTraversable(true);


    }

    /**
     * This is the intitalization method, it has some specific method for the position of the player when
     * they start the game. The player should spawn on top of the closest tower.
     */
    public void intitalization(){
        //make the player spawn at the appropriate height per level
        this.col = this.worldorganizer.getyStart();
        this.player.setFeet(this.col);
        this.player.posX = Constants.STARTING_X;
        this.player.positioning(Constants.STARTING_X, this.player.getY());

        //ensure that the pane handles key inputs correctly
        this.worldpane.setOnKeyPressed((KeyEvent e) -> this.handleKeyPress(e));
        this.worldpane.setOnKeyReleased((KeyEvent e) -> this.handleKeyRelease(e));

        this.worldpane.setFocusTraversable(true);

    }

    /**
     * This is the setupTimeline method, we set our game to update every 0.05 seconds.
     */
    public void setupTimeline(){
        KeyFrame kf = new KeyFrame(
                Duration.millis(50),
                (ActionEvent e) -> this.updateLabel());
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    /**
     * This is the updateLabel method, it defines what we update every 0.05 seconds.
     */
    private void updateLabel() {
        //we call our collision for the player 6 times, this ensures that falling onto platforms will
        //be accounted for. As a bonus it also corrects for the player's fall speed
        for(int i = 0; i <=7;i++) {
            this.col = this.wallTDCollisions();
            this.player.fall(this.col);
            this.wallLRCollisions();

        }

        //update everything else below
        this.healthbar.update();
        this.player.boundYPlayer();
        this.handleResponse();
        this.portalCollisions();
        this.handleEnemy();
        this.updatePproject();
        this.checkAlive();
        this.gameOver();

        //handling inventory;
        this.inventory.displayING();
        this.inventory.displayFoods();


    }

    /**
     * Below are the game logics concerning the player
     */
    /**
     * the wallLRCollisions method handles the left and right collisions of the player with walls
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

                //checks if player has intersected from the left
                if (this.player.posX + Constants.HITBOX_WIDTH <= LRrectbounds.getMinX() + Constants.COLLISION_CORRECTION) {
                    this.player.posX = LRrectbounds.getMinX() - Constants.HITBOX_WIDTH;
                    this.player.positioning(this.player.posX, this.player.posY);
                }

                //player moves right into the left intersection
                if (this.player.getXVel() > 0 && this.player.posX + Constants.HITBOX_WIDTH <= LRrectbounds.getMinX()) {
                    this.player.posX = LRrectbounds.getMinX() - Constants.HITBOX_WIDTH;
                    this.player.positioning(this.player.posX, this.player.posY);
                }


                //Right intersection
                if (this.player.posX >= LRrectbounds.getMaxX() - Constants.COLLISION_CORRECTION) {
                    this.player.posX = LRrectbounds.getMaxX();
                    this.player.positioning(this.player.posX, this.player.posY);
                }

                //player moves left into the right intersection
                if (this.player.getXVel() < 0 && this.player.posX >= LRrectbounds.getMaxX()) {
                    this.player.posX = LRrectbounds.getMaxX();
                    this.player.positioning(this.player.posX, this.player.posY);
                }

            }
        }
    }
    /**
     * the wallTDCollisions method handles the top-down collisions of the player with walls.
     * Note that I only wanted collisions above as that way you can jump onto walls from below
     */
public double wallTDCollisions(){
    //handling top and bottom intersections
    //first we set the top down to an absurd value
    double wallcol = 2000;
    for (int i = 0; i < this.worldorganizer.getwallList().size(); i++){
            Wall wall = this.worldorganizer.getwallList().get(i);
            Rectangle rect = wall.getRect();
            Bounds rectbounds = rect.getBoundsInParent();
            Bounds playerbounds = this.player.getHitbox().getBoundsInParent();

             //then if the player intersects from above, we set the wallcol to that wall's min Y
            if(rectbounds.intersects(playerbounds) && (this.player.getFeet() <= rect.getY()+ Constants.WALLPLAT_HEIGHT)) {
                    wallcol = rect.getBoundsInParent().getMinY();
                    //player can only stand on one wall at a time, so automatic breaks are set or else it will only
                    // take the lowest one
                    break;
            }
            //same idea but this one is called if the player is falling into the wall and prevents falling from
            //effecting the top-down collision
            else if(rectbounds.intersects(playerbounds) && (this.player.getYVel()>0)  && (this.player.getFeet() <= rect.getY()+ Constants.COLLISION_CORRECTION)) {
                wallcol = rect.getBoundsInParent().getMinY();
                break;
            }

            else{
                wallcol = 2000;

            }

    }
        return wallcol;

    }

    /**
     * the portalCollisions method handles intersections of the player with portals which will
     * send them to the next level/world.
     */
    public void portalCollisions() {
        //call each portal
        for (int i = 0; i < this.worldorganizer.getportalList().size(); i++) {
            Portal portal = this.worldorganizer.getportalList().get(i);
            Rectangle portalRect = portal.getRect();
            Bounds portalbounds = portalRect.getBoundsInParent();
            Bounds playerbounds = this.player.getHitbox().getBoundsInParent();

            //if the player has intersected the portal
            if (portalbounds.intersects(playerbounds)) {
                if(this.lastPortal<1) {
                    //send them to the next world/level
                    portal.sendWorld();
                    //this ensures that it only does it once
                    this.lastPortal++;
                }

            }
        }
    }


    /**
     * the playerattacks method handles the player's melee attack. Essentially it
     * calls each enemy and then if the player's attackbox intersects with the enemy
     * they take damage
     */
    public void playerattacks() {
        for (int i = 0; i < this.worldorganizer.getenemyList().size(); i++) {
            Enemy enemy = this.worldorganizer.getenemyList().get(i);
            Rectangle enemyBody = enemy.getBody();
            Bounds enemyBounds = enemyBody.getBoundsInParent();
            Bounds playerattackbounds = this.player.getAttackBox().getBoundsInParent();

            if (playerattackbounds.intersects(enemyBounds) && this.player.getAlive()){
                enemy.setHP(enemy.getHP()-1);
                //pushback the enemy so you can keep attacking.
                for(int j = 0 ; j < 10; j++){
                    enemy.antiReactX(this.player);
                }

            }

        }
    }
    /**
     * the playerAttL method handles the player's melee attack to the left, unlike enemies the player
     * can attack either side
     */
    public void playerAttL(){
        this.player.attackLeft();
        this.playerattacks();
        this.worldpane.getChildren().remove(this.player.getAttackBox());

    }

    /**
     * the playerAttR method handles the player's melee attack to the left, unlike enemies the player
     * can attack either side, or at the same time
     */
    public void playerAttR(){
        this.player.attackRight();
        this.playerattacks();
        this.worldpane.getChildren().remove(this.player.getAttackBox());
    }


    /**
     * the checkAlive method checks if the player is alive and sets the game state as appropriate
     * This enables game overs basically
     */
    public void checkAlive(){
        if (this.player.getAlive()){
            //player is alive
            this.gameState = true;
        }

        if(!this.player.getAlive()){
            //player is dead
            this.gameState = false;
        }

    }

    /**
     * the gameOver method models what happens when the player's HP reaches zero or falls out of the world
     */
    public void gameOver(){
        if(!this.gameState){
            //the screen goes dark
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-0.8);
            this.worldpane.setEffect(colorAdjust);


            //game over is added to the screen
            Label gameOver = new Label("Game Over");
            gameOver.setTranslateX(Constants.SCENE_WIDTH/2 - 500);
            gameOver.setTranslateY(Constants.SCENE_HEIGHT/2 - 300);
            gameOver.setTextFill(Color.WHITE);
            gameOver.setFont(new Font("Arial", 100.0));

            this.worldpane.getChildren().addAll(gameOver);

            //player is removed from existence
            this.player.removePlayer();

        }
    }


    /**
     * the updatePproject method updates each of the player's projectiles
     */
    public void updatePproject(){
        for(int i = 0; i < this.pProjList.size(); i++){
            this.pProjList.get(i).sense();
            this.pProjList.get(i).hunt();
            this.pProjList.get(i).deathClock();


        }
    }

    /**
     * the clearpProjList method clears the player projects, note that this method is used
     * outside the scope of the game class in pane organizer and thus needs to exist
     */
    public void clearpProjList(){
        this.pProjList.clear();
    }



    /**
     * for the enemies:
     */
    /**
     * the wallLRCollisionsEnemy method hands the left right intersection of the enemy with walls
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
                    if (currentE.getPosX() + Constants.ENEMY_SIZE <= LRrectbounds.getMinX() + Constants.ENEMY_SIZE * 1.5) {
                        currentE.setPosX(LRrectbounds.getMinX() - Constants.ENEMY_SIZE);
                    }

                    //Right intersection
                    if (currentE.getPosX()>= LRrectbounds.getMaxX() - Constants.ENEMY_SIZE) {
                        currentE.setPosX(LRrectbounds.getMaxX());
                    }

                }
            }

    }

    /**
     * the wallTDCollisionsEnemy method hands the top-down intersection of the enemy with walls.
     * Same idea with enemies only intersecting with the top.
     */
    public double wallTDCollisionsEnemy(Enemy currentE){
        //handling top intersections
        //put some absurd value for the wallcol
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
            }
            else{
                wallcol = 2000;

            }

        }
        return wallcol;

    }

    /**
     * the handleEnemy method complies all the methods we want our enemies to have updated and updates each
     * of them as such
     */
    public void handleEnemy(){
        for (int i = 0; i < this.worldorganizer.getenemyList().size(); i++) {
            Enemy currentE = this.worldorganizer.getenemyList().get(i);
            if(currentE.getStatus()) {
                //same idea with the player for checking intersections,
                //this just makes sure no one gets hurt
                for (int j = 0; j<6; j++) {
                    this.enemyCol = this.wallTDCollisionsEnemy(currentE);
                    currentE.Fall(this.enemyCol);
                    this.wallLRCollisionsEnemy(currentE);

                }
                currentE.Update(this.player);
            }
        }

    }


    /**
     * the handleKeyPress method, it handles key presses:
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
                //we don't want jump to be holdable
                this.player.jump();
                break;

            case DOWN:
                this.down = true;
                break;

            case A:
                this.playerAttL();
                break;

            case D:
                this.playerAttR();
                break;

            case ESCAPE:
                System.exit(0);
                break;

            case W:
                //this method limits the player's ranged attack to once every second
                long time = System.currentTimeMillis();
                long coolDownTime = 1000;
                if (time > this.lastRattack + coolDownTime) {
                    this.pProjList.add(new PlayerProjectile(this.worldpane, this.player, this.worldorganizer.getenemyList()));
                    this.lastRattack = time;
                }

                break;
            case SPACE:
                this.player.dash();
                this.player.xVel = 0;
                break;

            case E:
                this.inventory.displayInv();
                break;

            case P:
                //this method is for testing purposes, it adds Rice and Seaweed to the player's inventory
                //it also limits the maximum size of the inventory to 20
                if(this.inventory.getInventory().size()<20) {
                    this.inventory.getInventory().add(new RiceING());
                }
                if(this.inventory.getInventory().size()<20) {
                    this.inventory.getInventory().add(new SeaweedING());
                }
                break;


            default:
                this.player.idle();
                break;
        }
        e.consume();
    }

    /**
     * the handleKeyRelease method, it handles key releases, essentially enabling keyholding
     * to be a thing:
     */
    public void handleKeyRelease(KeyEvent e) {
        KeyCode keyPressed = e.getCode();
        switch (keyPressed) {
            case RIGHT:
                this.right = false;
                break;
            case LEFT:
                this.left = false;
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

    /**
     * the handleResponse method, it handles the holding of keys and maps them to their appropriate methods
     */
    public void handleResponse() {
        if (this.right) {
            this.player.moveRight();
        }
        if (this.left) {
            this.player.moveLeft();
        }
        if (this.down){
            this.player.fall(Constants.GROUND_HEIGHT);
        }

        if(!this.right && !this.left && !this.up && !this.down){
            this.player.idle();
        }
    }


}
