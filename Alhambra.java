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

public class Alhambra implements Enemy {
    private double posX;
    private double posY;
    private Inventory inventory;
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
        this.eHP = 100;
        this.isAlive = true;
        this.attackNum  = 0;

        this.posX = X;
        this.posY = y;
        //Inventory
        this.inventory = inventory;


        this.yVel = 0;
        this.xVel = 0;
        this.lastattack = 0;
        this.worldpane = worldpane;

        this.enemyProjectileArrayList = new ArrayList<EnemyProjectile>();

        //For Rice Specifically:
        this.image = new Image("indie/Enemies/Alhambra.png", 192/1.5, 300/1.5, true, true);
        this.imageView = new ImageView();

        this.Spawn(this.worldpane, this.posX, this.posY);
    }

    @Override
    public void Spawn(Pane worldpane, double X, double y) {
        //we will make a circle like object
        this.body = new Rectangle(63,160);
        this.body.setX(this.posX);
        this.body.setY(this.posY);
        this.body.setFill(Color.ORANGE);

        this.imageView.setImage(this.image);
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY - 100);


        this.worldpane.getChildren().addAll(this.body, this.imageView);

    }

    @Override
    public void Update(Player player) {
        if (this.isAlive) {
            this.Sense(player);
            this.Die();
            this.projectileUpdater();

            long time = System.currentTimeMillis();
            long coolDownTime = 2000;
            if (time > this.lastattack + coolDownTime) {
                double closeNum = Math.random() * 100;
                this.attackNum = closeNum;
                this.lastattack = time;
            }

        }

    }

    @Override
    public void Sense(Player player) {
        //this sense method will have basically everything

        //if player is close
        if ((Math.abs(player.posX - this.body.getX()) <= 500)) {
            //60% of time she will give chase and do a melee attack
            if (0 <= this.attackNum && this.attackNum <= 60) {
                if (Math.abs(player.posX - this.body.getX()) > 100) {
                    this.ReactX(player);
                    this.ReactY(player);
                }
                else if (Math.abs(player.posX - this.body.getX()) <= 100) {
                    this.Attack(player);
                    }
                }

            //40% of the time she will move away and then range attack
            else if (60 < this.attackNum && this.attackNum <= 100) {
                if (Math.abs(player.posX - this.body.getX()) <= 750) {
                    this.antiReactX(player);
                    this.antiReactY(player);
                }
                else if (Math.abs(player.posX - this.body.getX()) > 750) {
                        this.RAttack(player);
                    }
                }

        }
        else if ((Math.abs(player.posX - this.body.getX()) > 500)) {
            //30% of time she will give chase and do a melee attack
            if (0 <= this.attackNum && this.attackNum <= 30) {
                if (Math.abs(player.posX - this.body.getX()) > 100) {
                    this.ReactX(player);
                    this.ReactY(player);
                }
                else if (Math.abs(player.posX - this.body.getX()) <= 100) {
                    this.Attack(player);
                }
            }

            //40% of the time she will jump away and then range attack
            else if (40 < this.attackNum && this.attackNum <= 100) {
                if (Math.abs(player.posX - this.body.getX()) <= 750) {
                    this.antiReactX(player);
                    this.antiReactY(player);
                }
                else if (Math.abs(player.posX - this.body.getX()) > 750) {
                    this.RAttack(player);
                }
                }
            }
            //otherwise she will run into melee range after performing a ranged attack
            else{
                this.RAttack(player);
                if (Math.abs(player.posX - this.body.getX()) >= 400) {
                    this.ReactX(player);
                    this.ReactY(player);
                }
            }
        }


    @Override
    public void DefaultBehavior() {
        double behaviorNum = Math.random() * 100;
        if (0 <= behaviorNum && behaviorNum <= 40) {
            this.body.setX(this.posX);
        } else if (40 < behaviorNum && behaviorNum <= 70) {
            if (this.body.getY() > 0) {
                this.MoveLeft();
            }
        } else {
            if (this.body.getY() < 1000) {
                this.MoveRight();
            }
        }


    }

    @Override
    public void ReactX(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posX - this.body.getX() > 0) {
            //player is currently right of enemy
            this.MoveRight();


        } else if (player.posX - this.body.getX() < 0) {
            //player is currently left of enemy
            this.MoveLeft();

        } else {
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);

        }


    }

    public void ReactY(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posY - this.body.getY() > 0) {
            //player is currently right of enemy
            this.ReactX(player);
        } else if (player.posY - this.body.getY() < 0) {
            //player is currently left of enemy
            this.Jump();
        } else {
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);

        }


    }

    public void antiReactX(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posX - this.body.getX() > 0) {
            //player is currently right of enemy
            this.MoveLeft();
        } else if (player.posX - this.body.getX() < 0) {
            //player is currently left of enemy
            this.MoveRight();
        } else {
        }


    }

    public void antiReactY(Player player) {
        //this is where our little AI function lives

        //this checks if player is (40), (80): this means player i
        if (player.posY - this.body.getY() > 0) {
            //player is currently below of enemy
            this.Jump();
        } else if (player.posY - this.body.getY() < 0) {
            //player is currently left of enemy
            this.antiReactX(player);

        } else {
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);
            this.antiReactX(player);

        }


    }

    @Override
    public void Attack(Player player) {
        /**
         * melee dash attack player
         */
        if (this.isAlive) {
            long time = System.currentTimeMillis();
            this.lastattack = 0;
            long coolDownTime = 500;
            if (time > this.lastattack + coolDownTime) {
                if(this.xVel !=0) {
                    this.yVel = 0;

                    KeyFrame acceframe = new KeyFrame(
                            Duration.millis(20),
                            (ActionEvent e) -> this.attackHelper(player));
                    Timeline timeline = new Timeline(acceframe);
                    timeline.setCycleCount(1);
                    timeline.play();

                }
            }

        }

    }

    @Override
    public void attackHelper(Player player) {
        if (this.xVel < 0){
            this.xVel += - 25 * 0.5;
            this.yVel += -25 * 0.5;
            this.posX = this.posX + this.xVel * 0.5;
            this.posY = this.posY + this.yVel * 0.5;
            this.positioning(this.posX, this.posY);

        }

        else if (this.xVel > 0){
            this.xVel +=  25 * 0.5;
            this.yVel += -25 * 0.5;
            this.posX = this.posX + this.xVel * 0.5;
            this.posY = this.posY + this.yVel * 0.5;
            this.positioning(this.posX, this.posY);

        }

        if(this.body.getBoundsInParent().intersects(player.getHitbox().getBoundsInParent()) && this.isAlive) {
            player.hurt();
        }

    }
    public void RAttack(Player player) {
        /**
         * we need to make this such that it spawns in projectiles to kill player
         */
        if (this.isAlive) {
            long time = System.currentTimeMillis();
            this.lastattack = 0;
            long coolDownTime = 500;
            if (time > this.lastattack + coolDownTime) {
                this.enemyProjectileArrayList.add(new EnemyProjectile(this.worldpane, this, player));

            }

        }

    }



    public void projectileUpdater() {
        for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
            this.enemyProjectileArrayList.get(i).hunt();
            this.enemyProjectileArrayList.get(i).deathClock();
        }


    }

    @Override
    public void MoveLeft() {
        this.helperLeft();

    }

    public void helperLeft() {

        this.xVel = -10;

        this.posX = this.posX - 5;
        this.body.setX(this.posX);
        this.positioning(this.posX, this.posY);
    }

    @Override
    public void MoveRight() {
        this.helperRight();

    }

    public void helperRight() {

        this.xVel = 10;

        this.posX = this.posX + 5;
        this.body.setX(this.posX);
        this.positioning(this.posX, this.posY);

    }

    @Override
    public void Jump() {
        this.helperJump();
    }

    public void helperJump() {
        this.posY = this.posY - 10;
        this.body.setY(this.posY);
        this.positioning(this.posX, this.posY);

    }

    @Override
    public void Fall(double platY) {
        this.doGravity(platY);
    }

    public void doGravity(double platY) {

        //This function simulates the acceleration of gravity over time.
        this.yVel += Constants.GRAVITY * Constants.DURATION;
        platY = platY - 160;

        if (this.posY >= platY) {
            this.yVel = 0;
            this.posY = platY;
            this.positioning(this.posX, this.posY);


        } else {
            this.posY = this.posY + 4 * this.yVel * Constants.DURATION;
            this.positioning(this.posX, this.posY);


        }
    }

    @Override
    public void Die() {
        if (this.isAlive) {
            if (this.eHP == 0) {
                for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
                    this.enemyProjectileArrayList.get(i).despawn();
                }

                this.enemyProjectileArrayList.clear();
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);

                this.isAlive = false;
                if (this.inventory.getInventory().size() <= 20) {
                    this.inventory.getInventory().add(new SeaweedING());
                }

            }
            ;

            if (this.getPosY() >= 1300) {
                for (int i = 0; i < this.enemyProjectileArrayList.size(); i++) {
                    this.enemyProjectileArrayList.get(i).despawn();
                }

                this.enemyProjectileArrayList.clear();
                this.worldpane.getChildren().remove(this.body);
                this.worldpane.getChildren().remove(this.imageView);

                this.isAlive = false;
                if (this.inventory.getInventory().size() <= 20) {
                    this.inventory.getInventory().add(new SeaweedING());
                }

            }
            ;
        }


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
    public Rectangle getBody() {
        return this.body;
    }

    ;

    public double getPosX() {
        return this.posX;
    }

    ;

    public double getPosY() {
        return this.posY;
    }

    ;

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void positioning(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;

        this.body.setX(posX);
        this.body.setY(posY);

        this.imageView.setX(this.posX - 25);
        this.imageView.setY(this.posY  - 40 );
    }
}

