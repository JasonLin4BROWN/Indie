package indie;

import javafx.scene.paint.Color;

public class Constants {

    public static final int SCENE_WIDTH = 1920;
    public static final int SCENE_HEIGHT = 1080;
    public static final int OUT_OF_BOUNDS= 1300;
    public static final int OUT_OF_BOUNDS_TOP= -50;
    public static final double PROJECTILE_SIZE = 20;
    public static final Color PLAYER_PROJECTILE_COLOR = Color.WHITE;
    public static final Color ENEMY_PROJECTILE_COLOR = Color.RED;



    //Player constants
    public static final int PLAYER_HP = 10;
    public static final double HITBOX_HEIGHT = 283/3.3;
    public static final double HITBOX_WIDTH = 206/3.3;
    public static final double PLAYER_IMG_HEIGHT = 283/(2);
    public static final double PLAYER_IMG_WIDTH  = 206/(1.75);
    public static final double PLAYER_IMG_X_CORRECTION = 35;
    public static final double PLAYER_IMG_Y_CORRECTION = 45;
    public static final double STARTING_X  = 50;
    public static final double WALK_DIS = 20;
    public static final double JUMP_HEIGHT = 10;
    public static final int GRAVITY = 1000;
    public static final double DURATION = 0.0016;
    public static final double ATTACK_BOX_WIDTH = 150;
    public static final double ATTACK_BOX_HEIGHT = 70;
    public static final double ATTACK_BOX_CORRECTION_Y = 20;
    public static final double DASH_SPEED_X = 10;
    public static final double DASH_SPEED_Y = 20;
    public static final double DASH_TIME = 0.5;
    public static final long PLAYER_PROJECTILE_LIFESPAN = 3000;
    public static final double PLAYER_PROJECTILE_SPEED = 2;



    //for the Procedural Generation:

    public static final double MAX_DISTANCE_X = 250;
    public static final double MIN_DISTANCE_X = 200;
    public static final double MAX_DISTANCE_Y = -100;
    public static final double MIN_DISTANCE_Y = 50;


    //Alhambra Constants
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
    public static final long ALHAMBRA_CD = 500;
    public static final long ALHAMBRA_SENSE = 500;
    public static final long ALHAMBRA_MSENSE = 200;

    public static final double E_PROJECTILE_SPEED = 0.2;
    public static final long E_PROJECTILE_LIFESPAN = 3000;

    //Game Constants:
    public static final double COLLISION_CORRECTION= 100;
    public static final double GROUND_HEIGHT = 700;

    //Healthbar Constants:
    public static final double HB_X = 50;
    public static final double HB_Y = 50;
    public static final double HB_WIDTH = 25;

    public static final double HB_HEIGHT = 50;
    public static final Color HB_COLOR = Color.GREEN;
    public static final Color HBB_COLOR = Color.GRAY;

    //Inventory Constants:
    public static final double INVENTORY_WIDTH = 1450;
    public static final double INVENTORY_HEIGHT = 800;
    public static final int INVENTORY_SIZE = 20;

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


    //Portal Constants:
    public static final double PORTAL_WIDTH = 200;
    public static final double PORTAL_HEIGHT = 1200;

    //Recipes Constants:
    public static final double RECIPE_X = 770;
    public static final double RECIPE_Y = 230;
    public static final double RECIPE_SIZE = 80;
    public static final int MAX_FOOD = 8;

    //Enemy Constants:
    public static final double ENEMY_SIZE = 100;
    public static final int RICE_MAX_HP = 7;
    public static final int RICE_MIN_HP = 4;
    public static final int RICE_SENSE_RANGE = 500;
    public static final int RICE_SPEED = 3;
    public static final int RICE_JUMP = 5;
    public static final long RICE_CD = 500;
    public static final int SEAWEED_MAX_HP = 4;
    public static final int SEAWEED_MIN_HP = 1;
    public static final int SEAWEED_SENSE_RANGE = 300;
    public static final int SEAWEED_SPEED = 2;
    public static final int SEAWEED_JUMP = 5;
    public static final long SEAWEED_CD = 5000;


    //Ingredient Constants:
    public static final double ING_SIZE = 65;

    //Walls, Towers, and World Organizer Constants:
    public static final double WALL_LR_HITBOX_OFFSET = 40;
    public static final double TOWER_LR_HITBOX_OFFSET = 20;
    public static final double WORLD_XEND = 1300;
    public static final double FLOOR_WIDTH = 190;
    public static final double FLOOR_HEIGHT = 300;
    public static final double FLOOR_Y = 700;
    public static final double WALLPLAT_WIDTH = 60;
    public static final double WALLPLAT_HEIGHT = 60;
    public static final double WALLPLAT_VARIATION = 50;
    public static final double BARRIER_X = -180;
    public static final double BARRIER_RIGHT_X = 1430;

    public static final double TOWER_WIDTH = 200;

    public static final double SE_TOWER_DEFAULT = 300;
    public static final double PORTAL_X = 1430;
    public static final double PORTAL_Y = -200;
    public static final double PLAYER_ABILITY = 200;

    public static final double ALHAMBRA_SPAWN_X = 800;
    public static final double ALHAMBRA_SPAWN_Y = 500;







































}
