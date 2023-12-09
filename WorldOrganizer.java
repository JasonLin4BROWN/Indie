package wonders;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class WorldOrganizer {
    private double yStart;
    private double yEnd;

    private double curX;
    private double curY;

    private double xStart;
    private double xEnd;

    private ArrayList<Platforms> platformList;
    private ArrayList<Wall> wallList;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Portal>  portalList;
    private Pane worldpane;
    private Inventory inventory;
    private PaneOrganizer paneOrganizer;

    public WorldOrganizer(PaneOrganizer paneOrganizer, Pane worldpane, Inventory inventory){

        //essentially it will only generate anything if it is called to do so.
        this.platformList = new ArrayList<Platforms>();
        this.wallList = new ArrayList<Wall>();
        this.enemyList = new ArrayList<Enemy>();
        this.portalList = new ArrayList<Portal>();

        this.paneOrganizer = paneOrganizer;
        this.worldpane = worldpane;
        this.yStart = 0;
        this.yEnd = 0;

        this.xStart = 0;
        this.xEnd = 1300;

        this.curX = this.xStart;
        this.curY = this.yStart;


        //adds a floor for the player
        this.wallList.add(new Wall(worldpane, 0, 700, 190, 300));

        this.inventory = inventory;





    }

    public void generateEvery(){
        //generate everything for this World:
        this.procedural_level_generation();

        this.generatePortals();

        new DebugMarkers(this.worldpane);
        this.yStart = this.getyStart();

    }

    public void generatePlatforms(){
        //platforms are kinda useless now, but we will keep them in case of utility (they are a wall with no
        //side collisions)

        int platformNum = 0;

        for (int i = 0; i < platformNum; i++){
            double platX = Math.random() *  1000 + 200;
            double platY = Math.random() *  500 + 100;

            this.platformList.add(new Platforms(worldpane, platX, platY));

        }

    }

    public void generateFloor(double X){
        this.wallList.add(new Wall(worldpane, X, 700, 190, 300));


    }

    public void generateWallPlats(double wallX, double wallY){
        double width = Math.random() *  200 + 10;
        double height = Math.random() *  10 + 60;


        this.wallList.add(new Wall(worldpane, wallX, wallY, width, height));

    }

    public void generateSETowers(){
        //start:
        double rndStart = Math.random();
        this.yStart = 300 + rndStart*200;
        this.xStart = 0;
        this.wallList.add(new Tower(worldpane,this.xStart ,this.yStart , 200, Constants.SCENE_HEIGHT -yStart ));

        //goal:
        double rndEnd = Math.random();
        this.yEnd = 300 + rndEnd*200 - rndStart*200;
        this.xEnd = 1300;

        this.wallList.add(new Tower(worldpane, this.xEnd, this.yEnd, 200, Constants.SCENE_HEIGHT - yEnd ));

    }

    public void generateTowers(double X, double y){
        this.wallList.add(new Tower(worldpane, X,y , 200, Constants.SCENE_HEIGHT - y));

    }

    public double getyStart(){
        return this.yStart;
    }

    public void generateSEnemies(double enemyX, double enemyY){
        this.enemyList.add(new Rice(this.worldpane, enemyX, enemyY, this.inventory));

    }

    public void generateREnemies(double enemyX, double enemyY){
        this.enemyList.add(new Seaweed(this.worldpane, enemyX, enemyY, this.inventory));
    }

    public void generatePortals(){
        //end Portal
        this.portalList.add(new Portal(this.paneOrganizer, this.worldpane, 1400, 0));

    }


    public void updateEnemies(Player player){
        for (int i = 0; i < this.enemyList.size(); i++){
            this.enemyList.get(i).Update(player);

        }
    }

    public void removeEvery(){
        this.platformList.clear();
        this.wallList.clear();
        this.enemyList.clear();


    }

    public void procedural_level_generation(){
        //getting distance that needs to be covered

        this.generateSETowers();
        new DebugMarkers(this.worldpane);
        this.yStart = this.getyStart();

        for (int i = 0; i < 10; i++) {
            if (this.curX < this.xEnd) {

                double distY = this.yStart - this.yEnd;
                double distX = this.xEnd - this.yStart;

                double xMin = this.xStart + Constants.MIN_DISTANCE_X;
                double xMax = this.xStart + Constants.MAX_DISTANCE_X;

                this.curX = this.curX +
                        Math.random() * (xMax - xMin) + xMin;

                double yMax = Constants.MAX_DISTANCE_Y;
                double yMin = Constants.MIN_DISTANCE_Y;
                this.curY = Math.min(this.yEnd, this.yStart +
                        Math.random() * (yMax - yMin) + yMin);

                int generate_num = (int) Math.floor(Math.random() * 4);
                int generate_enemy = (int) Math.floor(Math.random() * 4);




                switch (generate_num) {
                    case 1:
                        this.generateFloor(this.curX);
                        if (generate_enemy ==0){
                            this.generateSEnemies(this.curX, 700-100);
                        }

                        if (generate_enemy ==1){
                            this.generateREnemies(this.curX, 700-100);
                        }

                        break;
                    case 2:
                        this.generateTowers(this.curX, this.curY);
                        if (generate_enemy ==0){
                            this.generateSEnemies(this.curX, this.curY-100);
                        }

                        if (generate_enemy ==1){
                            this.generateREnemies(this.curX, this.curY-100);
                        }

                        break;

                    case 3:
                        this.generateWallPlats(this.curX, this.curY);
                        if (generate_enemy ==0){
                            this.generateSEnemies(this.curX, this.curY-100);
                        }

                        if (generate_enemy ==1){
                            this.generateREnemies(this.curX, this.curY-100);
                        }

                        break;

                    default:
                        this.generateWallPlats(this.curX, this.curY);
                        break;


                }
            }
        }




    }

    public ArrayList<Platforms> getPlatList(){
        return this.platformList;
    }

    public ArrayList<Wall> getwallList(){
        return this.wallList;
    }

    public ArrayList<Enemy> getenemyList(){
        return this.enemyList;
    }

    public ArrayList<Portal> getportalList(){
        return this.portalList;
    }

    public Inventory getInventory(){return this.inventory; }


}
