package indie;

import javafx.scene.paint.Color;

public class Constants {

    public static final int SCENE_WIDTH = 1920;
    public static final int SCENE_HEIGHT = 1080;

    public static final double WALK_DIS = 20;

    public static final double JUMP_HEIGHT = 10;

    public static final int GRAVITY = 1000; // acceleration constant (UNITS: pixels/s^2)
    public static final double DURATION = 0.0016; // KeyFrame duration (UNITS: s)


    //hitboxes:

    public static final double HITBOX_HEIGHT = 283/3.3;
    public static final double HITBOX_WIDTH = 206/3.3;


    //for the Procedural Generation:

    public static final double MAX_DISTANCE_X = 250;
    public static final double MIN_DISTANCE_X = 200;
    public static final double MAX_DISTANCE_Y = -100;
    public static final double MIN_DISTANCE_Y = 50;


    public static final int ALHAMBRA_HP = 20;
    public static final double ALHAMBRA_WIDTH = 192/1.5;
    public static final double ALHAMBRA_HEIGHT = 300/1.5;
    public static final double ALHAMBRA_HB_WIDTH = 63;
    public static final double ALHAMBRA_HB_HEIGHT = 160;
    public static final double ALHAMBRA_IMAGE_CORRECTION_X = 25;

    public static final double ALHAMBRA_IMAGE_CORRECTION_Y = 40;
    public static final double ALHAMBRA_DASH_LENGTH = 40;
    public static final double ALHAMBRA_DASH_TIME = 0.5;

    public static final double ALHAMBRA_XVEL = 10;
    public static final double ALHAMBRA_MOVEMENT_SPEED = 5;
    public static final double ALHAMBRA_JUMP_HEIGHT = 10;


    public static final double PROJECTILE_SIZE = 20;
    public static final double E_PROJECTILE_SPEED = 0.2;
    public static final long E_PROJECTILE_LIFESPAN = 5000;

    public static final double COLLISION_CORRECTION= 100;


    public static final double WALLPLAT_HEIGHT = 60;
    public static final double ENEMY_SIZE = 100;
    public static final double GROUND_HEIGHT = 700;

    //Healthbar Constants:
    public static final double HB_X = 50;
    public static final double HB_Y = 50;
    public static final double HB_WIDTH = 50;

    public static final double HB_HEIGHT = 50;
    public static final Color HB_COLOR = Color.GREEN;
    public static final Color HBB_COLOR = Color.GRAY;

    //Inventory Constants:
    public static final double INVENTORY_WIDTH = 1450;
    public static final double INVENTORY_HEIGHT = 800;
    public static final double ICON_SIZE = 65;
    public static final int WIDTH_BTW_ICONS = 117;
    public static final int HEIGHT_BTW_ICONS = 100;

    public static final int INGREDIENT_POSX_CORRECTION = 207;
    public static final int INGREDIENT_POSY_CORRECTION = 225;

    public static final int FOOD_POSX_CORRECTION = 755;
    public static final int FOOD_POSY_CORRECTION = 525;

    //Onigiri specific:
    public static final double ONIGIRI_SIZE = 80;
    public static final int HEAL_HP = 1;

    //PaneOrganizer Constants:

    public static final int MAX_NUM_WORLDS = 3;
    public static final double BACKGROUND_WIDTH = 1450;
    public static final double BACKGROUND_HEIGHT = 875;







    //Player constants
    public static final int PLAYER_HP = 5;











}
