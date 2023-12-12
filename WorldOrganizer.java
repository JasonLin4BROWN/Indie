package indie;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * This is the WorldOrganizer class, it organizes the world, creates levels, and more
 */
public class WorldOrganizer {
    private double yStart;
    private double yEnd;

    private double curX;
    private double curY;

    private double xStart;
    private double xEnd;
    private ArrayList<Wall> wallList;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Portal>  portalList;
    private Pane worldpane;
    private Inventory inventory;
    private PaneOrganizer paneOrganizer;

    public WorldOrganizer(PaneOrganizer paneOrganizer, Pane worldpane, Inventory inventory){

        //essentially it will only generate anything if it is called to do so.
        this.wallList = new ArrayList<Wall>();
        this.enemyList = new ArrayList<Enemy>();
        this.portalList = new ArrayList<Portal>();

        this.paneOrganizer = paneOrganizer;
        this.worldpane = worldpane;
        this.yStart = 0;
        this.yEnd = 0;

        this.xStart = 0;
        this.xEnd = Constants.WORLD_XEND;

        this.curX = this.xStart;
        this.curY = this.yStart;


        //adds a floor for under where the player spawns
        this.wallList.add(new Wall(worldpane, 0, Constants.FLOOR_Y, Constants.FLOOR_WIDTH, Constants.FLOOR_HEIGHT));

        this.inventory = inventory;





    }

    /**
     * This is the generateEvery method, it compiles everything we want in a level
     */
    public void generateEvery(){
        //generate everything for this World:
        this.procedural_level_generation();

        //generate the end portal
        this.generatePortals();

        //set the starting y location of the player on the start tower
        this.yStart = this.getyStart();

    }

    /**
     * This is the generateFloor method, it generates walls that act as the floor
     */
    public void generateFloor(double X){
        this.wallList.add(new Wall(this.worldpane, X, Constants.FLOOR_Y, Constants.FLOOR_WIDTH, Constants.FLOOR_HEIGHT));


    }
    /**
     * This is the generateWallPlats method, it generates walls that act as the platforms
     */
    public void generateWallPlats(double wallX, double wallY){
        double width = Math.random() *  Constants.WALLPLAT_VARIATION * 3 + Constants.WALLPLAT_WIDTH;
        double height = Math.random() *  Constants.WALLPLAT_VARIATION + Constants.WALLPLAT_HEIGHT;


        this.wallList.add(new Wall(this.worldpane, wallX, wallY, width, height));

    }
    /**
     * This is the generateSETowers method, it generates the starting and ending towers where the player
     * wants to get to
     */
    public void generateSETowers(){
        //Add the left most barrier wall
        this.wallList.add(new Tower(this.worldpane, Constants.BARRIER_X,0 , Constants.TOWER_WIDTH, Constants.SCENE_HEIGHT));

        //start:
        double rndStart = Math.random();
        this.yStart = Constants.SE_TOWER_DEFAULT + rndStart*Constants.TOWER_WIDTH;
        this.xStart = 0;
        this.wallList.add(new Tower(this.worldpane,this.xStart ,this.yStart , Constants.TOWER_WIDTH, Constants.SCENE_HEIGHT -yStart ));

        //goal:
        double rndEnd = Math.random();
        this.yEnd = Constants.SE_TOWER_DEFAULT + rndEnd * Constants.TOWER_WIDTH - rndStart*Constants.TOWER_WIDTH;
        this.xEnd = Constants.WORLD_XEND;

        this.wallList.add(new Tower(this.worldpane, this.xEnd, this.yEnd, Constants.TOWER_WIDTH, Constants.SCENE_HEIGHT - yEnd ));

    }
    /**
     * This is the generateTowers method, it generates towers of a height
     */
    public void generateTowers(double X, double y){
        this.wallList.add(new Tower(this.worldpane, X,y , Constants.TOWER_WIDTH, Constants.SCENE_HEIGHT - y));

    }
    /**
     * This is the getyStart method, it returns the starting y for the player
     */
    public double getyStart(){
        return this.yStart;
    }

    /**
     * This is the generateSEnemies method, it spawns standard/melee enemies, in this case the melee Rice
     */
    public void generateSEnemies(double enemyX, double enemyY){
        this.enemyList.add(new Rice(this.worldpane, enemyX, enemyY, this.inventory));

    }
    /**
     * This is the generateREnemies method, it spawns ranged enemies, in this case the Seaweed
     */
    public void generateREnemies(double enemyX, double enemyY){
        this.enemyList.add(new Seaweed(this.worldpane, enemyX, enemyY, this.inventory));
    }
    /**
     * This is the generatePortals method, it spawns the end portal
     */
    public void generatePortals(){
        //end Portal
        this.portalList.add(new Portal(this.paneOrganizer, this.worldpane, Constants.PORTAL_X, Constants.PORTAL_Y));

    }

    /**
     * This is the removeEvery method, it removes everything from existence
     */
    public void removeEvery(){
        this.wallList.clear();
        this.enemyList.clear();
        this.portalList.clear();

    }

    /**
     * This is the procedural_level_generation method, it generates a certain level of wallplat,
     * towers, and floors necessary for the player to get from the start to the end.
     *
     * It also randomly spawns enemies on said objects
     */

    public void procedural_level_generation(){

        //generate the start and end
        this.generateSETowers();
        this.yStart = this.getyStart();


        //getting distance that needs to be covered
        double distY = this.yStart - this.yEnd;
        double distX = this.xEnd - this.yStart;

        double spawnNum = Math.max(distY/Constants.PLAYER_ABILITY, distX/Constants.PLAYER_ABILITY);

        //spawn in the level based on a bit of randomness
        for (int i = 0; i < spawnNum; i++) {
            if (this.curX < this.xEnd) {
                double xMin = this.xStart + Constants.MIN_DISTANCE_X;
                double xMax = this.xStart + Constants.MAX_DISTANCE_X;

                this.curX = this.curX +
                        Math.random() * (xMax - xMin) + xMin;

                double yMax = Constants.MAX_DISTANCE_Y;
                double yMin = Constants.MIN_DISTANCE_Y;
                this.curY = Math.min(this.yEnd, this.yStart +
                        Math.random() * (yMax - yMin) + yMin);

                //select a random number to determine what is spawned and where
                int generate_num = (int) Math.floor(Math.random() * 4);
                int generate_enemy = (int) Math.floor(Math.random() * 2);

                //Random Generation based on procedure
                switch (generate_num) {
                    case 1:
                        this.generateFloor(this.curX);
                        if (generate_enemy ==0){
                            this.generateSEnemies(this.curX, Constants.FLOOR_Y-Constants.ENEMY_SIZE);
                        }

                        if (generate_enemy ==1){
                            this.generateREnemies(this.curX, Constants.FLOOR_Y-Constants.ENEMY_SIZE);
                        }

                        break;
                    case 2:
                        this.generateTowers(this.curX, this.curY);
                        if (generate_enemy ==0){
                            this.generateSEnemies(this.curX, this.curY-Constants.ENEMY_SIZE);
                        }

                        if (generate_enemy ==1){
                            this.generateREnemies(this.curX, this.curY-Constants.ENEMY_SIZE);
                        }

                        break;

                    case 3:
                        this.generateWallPlats(this.curX, this.curY);
                        if (generate_enemy ==0){
                            this.generateSEnemies(this.curX, this.curY-Constants.ENEMY_SIZE);
                        }

                        if (generate_enemy ==1){
                            this.generateREnemies(this.curX, this.curY-Constants.ENEMY_SIZE);
                        }

                        break;

                    default:
                        this.generateWallPlats(this.curX, this.curY);
                        break;

                }
            }
        }
    }

    /**
     * This is the boss_level_generation method, it generates our boss room and adds the boss into the game
     */
    public void boss_level_generation() {
        //add the floor
        for (int i = 0; i < Constants.SCENE_WIDTH/Constants.FLOOR_WIDTH; i++) {
            this.wallList.add(new Wall(this.worldpane, i*Constants.FLOOR_WIDTH, Constants.FLOOR_Y, Constants.FLOOR_WIDTH, Constants.FLOOR_HEIGHT));
        }

        //create the towers either side:
        this.wallList.add(new Tower(this.worldpane, Constants.BARRIER_X,0 , Constants.TOWER_WIDTH, Constants.SCENE_HEIGHT));
        this.wallList.add(new Tower(this.worldpane, Constants.BARRIER_RIGHT_X,0 , Constants.TOWER_WIDTH, Constants.SCENE_HEIGHT));


        //add Alhambra into the game
        this.enemyList.add(new Alhambra(this.worldpane, Constants.ALHAMBRA_SPAWN_X, Constants.ALHAMBRA_SPAWN_Y, this.inventory));

        this.yStart = this.getyStart();

    }
    /**
     * This is the getwallList method, it gets our walllist
     */
    public ArrayList<Wall> getwallList(){
        return this.wallList;
    }
    /**
     * This is the getenemyList method, it gets our enemylist
     */
    public ArrayList<Enemy> getenemyList(){
        return this.enemyList;
    }
    /**
     * This is the getportalList method, it gets our portals
     */
    public ArrayList<Portal> getportalList(){
        return this.portalList;
    }
    /**
     * This is the getInventory method, it gets our inventory
     */
    public Inventory getInventory(){return this.inventory; }


}
