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

/**
 * This is the Alhambra class, she is our indie games boss and implements the enemy archetype as a type of enemy
 */
public class Alhambra implements Enemy {
    private double posX;
    private double posY;
    private Image image;
    private ImageView imageView;
    private Rectangle body;
    private Pane worldpane;
    private int eHP;
    private double attackNum;
    private boolean isAlive;
    private double yVel;
    private double xVel;
    private long lastattack;

    private ArrayList<EnemyProjectile> enemyProjectileArrayList;

    public Alhambra(Pane worldpane, double X, double y, Inventory inventory) {
        //set up location and movement
        this.posX = X;
        this.posY = y;
        this.yVel = 0;
        this.xVel = 0;

        //set up other important variables
        this.eHP = Constants.ALHAMBRA_HP;
        this.isAlive = true;
        this.attackNum  = 0;
        this.lastattack = 0;
        this.worldpane = worldpane;
        this.enemyProjectileArrayList = new ArrayList<EnemyProjectile>();

        //Make the image of Alhambra Specifically:
        this.image = new Image("indie/Alhambra.png", Constants.ALHAMBRA_WIDTH, Constants.ALHAMBRA_HEIGHT, true, true);
        this.imageView = new ImageView();

        //spawn Alhambra in
        this.Spawn(this.worldpane, this.posX, this.posY);
    }

    /**
     * This is the spawn method, it spawns in our Alhambra
     */
    @Override
    public void Spawn(Pane worldpane, double X, double y) {
        //this sets up Alhambra's hitbox
        this.body = new Rectangle(Constants.ALHAMBRA_HB_WIDTH,Constants.ALHAMBRA_HB_HEIGHT);
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.TRANSPARENT);

        //sets the ImageView to the image of Alhambra
        this.imageView.setImage(this.image);
        this.imageView.setX(this.posX - Constants.ALHAMBRA_IMAGE_CORRECTION_X);
        this.imageView.setY(this.posY - Constants.ALHAMBRA_IMAGE_CORRECTION_Y);


        this.worldpane.getChildren().addAll(this.body, this.imageView);

    }

    /**
     * This is the update method, it consolidates what needs to updated for Alhambra to function
     */
    @Override
    public void Update(Player player) {
        if (this.isAlive) {
            this.Sense(player);
            this.Die();
            this.projectileUpdater();

            //this manages Alhambra's attacks, every 0.5 seconds she will choose an attack to do randomly based on the
            //conditions in the sense method and then perform it.
            long time = System.currentTimeMillis();
            if (time > this.lastattack + Constants.ALHAMBRA_CD) {
                double closeNum = Math.random() * 100;
                this.attackNum = closeNum;
                this.lastattack = time;
            }

        }

    }

    /**
     * This is the sense method, it allows Alhambra to act appropriately based on the location of the player
     * relative to her.
     */
    @Override
    public void Sense(Player player) {
        //if player within 500 pixels
        if ((Math.abs(player.posX - this.body.getX()) <= Constants.ALHAMBRA_SENSE)) {
            //90% of time she will give chase and do a melee attack
            if (0 <= this.attackNum && this.attackNum <= 90) {
                if (Math.abs(player.posX - this.body.getX()) > Constants.ALHAMBRA_MSENSE) {
                    this.ReactX(player);
                    this.ReactY(player);
                }
                else if (Math.abs(player.posX - this.body.getX()) <= Constants.ALHAMBRA_MSENSE) {
                    this.Attack(player);
                    }
                }

            //10% of the time she will move away and then range attack
            else if (90 < this.attackNum && this.attackNum <= Constants.ALHAMBRA_MSENSE) {
                if (Math.abs(player.posX - this.body.getX()) <= Constants.ALHAMBRA_MSENSE) {
                    this.antiReactX(player);
                    this.antiReactY(player);
                }
                else if (Math.abs(player.posX - this.body.getX()) > Constants.ALHAMBRA_MSENSE) {
                        this.RAttack(player);
                    }
                }

        }

        //otherwise
        else if ((Math.abs(player.posX - this.body.getX()) > Constants.ALHAMBRA_SENSE)) {
            //30% of time she will give chase and do a melee attack
            if (0 <= this.attackNum && this.attackNum <= 30) {
                if (Math.abs(player.posX - this.body.getX()) > Constants.ALHAMBRA_MSENSE) {
                    this.ReactX(player);
                    this.ReactY(player);
                } else if (Math.abs(player.posX - this.body.getX()) <= Constants.ALHAMBRA_MSENSE) {
                    this.Attack(player);
                }
            }

            //40% of the time she will jump away and then range attack
            else if (40 < this.attackNum && this.attackNum <= Constants.ALHAMBRA_MSENSE) {
                if (Math.abs(player.posX - this.body.getX()) <= Constants.ALHAMBRA_MSENSE) {
                    this.antiReactX(player);
                    this.antiReactY(player);
                } else if (Math.abs(player.posX - this.body.getX()) > Constants.ALHAMBRA_MSENSE) {
                    this.RAttack(player);
                }
            }

            //otherwise she will run into melee range after performing a ranged attack
            else {
                this.RAttack(player);
                if (Math.abs(player.posX - this.body.getX()) >= Constants.ALHAMBRA_MSENSE) {
                    this.ReactX(player);
                    this.ReactY(player);
                }
            }
        }
        else{
            this.DefaultBehavior();
        }

        }


    /**
     * This is the DefaultBehavior method, it models what she does when she doesn't sense you
     */
    @Override
    public void DefaultBehavior() {
        double behaviorNum = Math.random() * 100;

        //40% of the time she will do nothing
        if (0 <= behaviorNum && behaviorNum <= 40) {
            this.body.setX(this.posX);

        }

        //30% of the time she will move left
        else if (40 < behaviorNum && behaviorNum <= 70) {
            if (this.body.getY() > 0) {
                this.MoveLeft();
            }
        }

        //30% of the time she will move left
        else {
            if (this.body.getY() < 1000) {
                this.MoveRight();
            }
        }


    }

    /**
     * This is the ReactX method, this method makes Alhambra move towards the player depending on their position
     * on the x axis
     */
    @Override
    public void ReactX(Player player) {

        //if the player X is larger than Alhambra and thus right of her
        if (player.posX - this.body.getX() > 0) {
            //Alhambra will move right
            this.MoveRight();

        //else if player is left of her
        } else if (player.posX - this.body.getX() < 0) {
            //she will move left
            this.MoveLeft();

        //otherwise Alhambra will move away from the player 5 times  and thus not constantly stand where the player is
        } else {
            for(int i = 0; i<5; i++){
                this.antiReactX(player);
            }
        }
    }
    /**
     * This is the ReactY method, this method makes Alhambra move towards the player depending on their position
     * on the y axis
     */
    public void ReactY(Player player) {

        //if the player is below Alhambra
        if (player.posY - this.body.getY() > 0) {
            //move towards the player
            this.ReactX(player);
        }
        //if the player is above the Alhambra
        else if (player.posY - this.body.getY() < 0) {
            //Alhambra should jump towards the player
            this.Jump();
        }
        //otherwise Alhambra will move away from the player 5 times  and thus not constantly stand where the player is
        else {
            for(int i = 0; i<5; i++){
                this.antiReactX(player);
            }

        }


    }

    /**
     * This is the antiReactX method, this method makes Alhambra move away the player on the x-axis
     */
    public void antiReactX(Player player) {
        //essentially this is doing the opposite of what the ReactX method does

        if (player.posX - this.body.getX() > 0) {
            //player is currently right of enemy
            this.MoveLeft();
        }
        else if (player.posX - this.body.getX() < 0) {
            //player is currently left of enemy
            this.MoveRight();
        }
    }

    /**
     * This is the antiReactY method, this method makes Alhambra move away the player on the y-axis
     */
    public void antiReactY(Player player) {
        //essentially this is doing the opposite of what the ReactY method does

        if (player.posY - this.body.getY() > 0) {
            this.Jump();
        }

        else if (player.posY - this.body.getY() < 0) {
            this.antiReactX(player);

        }

        else {
            for(int i = 0; i<5; i++){
                this.antiReactX(player);
            }
        }

    }

    /**
     * This is the Attack method, this is Alhambra's melee dash attack
     */
    @Override
    public void Attack(Player player) {
            if (this.xVel != 0) {
                this.yVel = 0;
                this.attackHelper(player);
            }
    }

    /**
     * This is the attackHelper method, this has the logic behind the melee dash attack
     */
    public void attackHelper(Player player) {

        //if Alhambra is moving left
        if (this.xVel < 0){

            //as this is the dash we will us an acceleration calculation
            this.xVel += - Constants.ALHAMBRA_DASH_LENGTH * Constants.ALHAMBRA_DASH_TIME;
            this.posX = this.posX + this.xVel * Constants.ALHAMBRA_DASH_TIME;
            this.positioning(this.posX, this.posY);

        }

        else if (this.xVel > 0){
            this.xVel +=  Constants.ALHAMBRA_DASH_LENGTH * Constants.ALHAMBRA_DASH_TIME;
            this.posX = this.posX + this.xVel * Constants.ALHAMBRA_DASH_TIME;
            this.positioning(this.posX, this.posY);

        }

        //if during this dash the player is in the way, then she will get damaged
        if(this.body.getBoundsInParent().intersects(player.getHitbox().getBoundsInParent()) && this.isAlive) {
            player.hurt();
        }

    }

    /**
     * the RAttack method allows Alhambra to make a ranged attack
     */
    public void RAttack(Player player) {
        //at the location of Alhambra a enemy projectile will be spawned
        //range melee attack every 0.4 second
        long time = System.currentTimeMillis();
        long coolDownTime = 500;
        if (time > this.lastattack + coolDownTime) {
            if (this.isAlive) {
                this.enemyProjectileArrayList.add(new EnemyProjectile(this.worldpane, this, player));
                this.lastattack = time;
            }
        }
    }

    /**
     * the projectileUpdater method updates each of the projectiles created by Alhambra to do their
     * appropriate tasks
     */
    public void projectileUpdater() {
        for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
            this.enemyProjectileArrayList.get(i).hunt();
            this.enemyProjectileArrayList.get(i).deathClock();
        }


    }

    /**
     * the MoveLeft method, it moves Alhambra left
     */
    @Override
    public void MoveLeft() {
        this.xVel = - Constants.ALHAMBRA_XVEL;
        this.posX = this.posX - Constants.ALHAMBRA_MOVEMENT_SPEED;
        this.positioning(this.posX, this.posY);
    }


    /**
     * the MoveLeft method, it moves Alhambra right
     */
    @Override
    public void MoveRight() {
        this.xVel = Constants.ALHAMBRA_XVEL;
        this.posX = this.posX + Constants.ALHAMBRA_MOVEMENT_SPEED;
        this.positioning(this.posX, this.posY);
    }

    /**
     * the jump method, it allows Alhambra to jump
     */
    @Override
    public void Jump() {
        if(this.yVel ==0 && this.isAlive) {
            //this timeline is necessary since it allows the movement of jumping as an animation rather than
            //teleportation.
            KeyFrame leftframe = new KeyFrame(
                    Duration.millis(10),
                    (ActionEvent e) -> this.helperJump());
            Timeline timeline = new Timeline(leftframe);
            timeline.setCycleCount(20);
            timeline.play();
        }
    }

    /**
     * the helperJump method, it helps Alhambra to jump
     */
    public void helperJump() {
        this.posY = this.posY - Constants.ALHAMBRA_JUMP_HEIGHT;
        this.positioning(this.posX, this.posY);

    }

    /**
     * the fall method, it models gravity and allows Alhambra to fall (sometimes it must be done on command)
     */
    @Override
    public void Fall(double platY) {
        this.doGravity(platY);
    }

    /**
     * the doGravity method, it models gravity
     */
    public void doGravity(double platY) {

        //This function simulates the acceleration of gravity over time.
        this.yVel += Constants.GRAVITY * Constants.DURATION;
        platY = platY - Constants.ALHAMBRA_HB_HEIGHT;

        //if there is a platform below, stop the falling and keep Alhambra up there
        if (this.posY >= platY) {
            this.yVel = 0;
            this.posY = platY;
            this.positioning(this.posX, this.posY);

        //otherwise, make her fall
        } else {
            this.posY = this.posY + 4* this.yVel * Constants.DURATION;
            this.positioning(this.posX, this.posY);


        }
    }

    /**
     * the Die method, it is the method called when Alhambra is defeated
     */
    @Override
    public void Die() {
        if (this.isAlive) {
            //when her HP reaches zero
            if (this.eHP <= 0) {

                //despawn all her projectiles
                for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
                    this.enemyProjectileArrayList.get(i).despawn();
                }
                this.enemyProjectileArrayList.clear();

                //then remove her from the world
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);
                this.isAlive = false;

            }
            ;

            //same idea if she falls out of the world
            if (this.getPosY() >=  Constants.OUT_OF_BOUNDS) {
                for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
                    this.enemyProjectileArrayList.get(i).despawn();
                }

                this.enemyProjectileArrayList.clear();
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);

                this.isAlive = false;

            }
            ;
        }


    }

    /**
     * the getStatus method, it returns if she is alive or not
     */
    @Override
    public boolean getStatus() {
        return this.isAlive;
    }

    /**
     * the getHP method, it returns her current HP
     */
    @Override
    public int getHP() {
        return this.eHP;
    }

    /**
     * the setHP method, it sets her current HP to a new value
     */
    @Override
    public void setHP(int newHP) {
        this.eHP = newHP;

    }

    /**
     * the getBody method, it returns her hitbox
     */
    @Override
    public Rectangle getBody() {
        return this.body;
    };

    /**
     * the getPosX method, it returns her x position
     */
    public double getPosX() {
        return this.posX;
    }

    ;
    /**
     * the getPosY method, it returns her y position
     */
    public double getPosY() {
        return this.posY;
    }

    ;

    /**
     * the setPosX method, it sets her x position
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }


    /**
     * the positioning helper method, it sets her x and y position, and then sets the image's position as well with
     * the appropriate corrections
     */
    public void positioning(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;

        this.body.setX(posX);
        this.body.setY(posY);

        this.imageView.setX(this.posX - Constants.ALHAMBRA_IMAGE_CORRECTION_X);
        this.imageView.setY(this.posY  - Constants.ALHAMBRA_IMAGE_CORRECTION_Y );
    }
}

