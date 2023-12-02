package wonders;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class WorldOrganizer {
    private double yStart;
    private ArrayList<Platforms> platformList;
    private ArrayList<Wall> wallList;
    private ArrayList<Enemy> enemyList;
    private ArrayList<Portal>  portalList;
    private Pane worldpane;
    private PaneOrganizer paneOrganizer;

    public WorldOrganizer(PaneOrganizer paneOrganizer, Pane worldpane){

        this.yStart = 0;
        //essentially it will only generate anything if it is called to do so.
        this.platformList = new ArrayList<Platforms>();
        this.wallList = new ArrayList<Wall>();
        this.enemyList = new ArrayList<Enemy>();
        this.portalList = new ArrayList<Portal>();

        this.paneOrganizer = paneOrganizer;
        this.worldpane = worldpane;

    }

    public void generateEvery(){


        //generate everything for this World:
        this.generateFloor();
        this.generatePlatforms();
        this.generateWalls();
        this.generateTowers();

        this.generateSEnemies();
        this.generateREnemies();

        this.generatePortals();

        new DebugMarkers(this.worldpane);
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

    public void generateFloor(){
        //floor
        for (int i = 0; i < Constants.SCENE_WIDTH/190; i++){
            if(Math.random() >= 0.4){
                this.wallList.add(new Wall(worldpane, i*190, 700, 190, 300));

            }
        }

    }

    public void generateWalls(){
        //platforms
        int wallNum = 10;

        for (int i = 0; i < wallNum; i++){
            double wallX = Math.random() *  1000 + 200;
            double wallY = Math.random() *  500 + 100;
            double width = Math.random() *  200 + 10;
            double height = Math.random() *  10 + 60;


            this.wallList.add(new Wall(worldpane, wallX, wallY, width, height));

        }

    }

    public void generateTowers(){
        //start:
        double rndStart = Math.random();
        this.yStart = 300 + rndStart*200;
        this.wallList.add(new Tower(worldpane, 0,yStart , 200, Constants.SCENE_HEIGHT -yStart ));

        //goal:
        double rndEnd = Math.random();
        double yEnd = 300 + rndEnd*200 - rndStart*200;

        this.wallList.add(new Tower(worldpane, 1300, yEnd, 200, Constants.SCENE_HEIGHT - yEnd ));

    }

    public double getyStart(){
        return this.yStart;
    }

    public void generateSEnemies(){
        int senemiesNum = 2;

        for (int i = 0; i < senemiesNum; i++){
            double enemyX = Math.random() *  1000 + 500;
            double enemyY = 0;

            this.enemyList.add(new StandardEnemy(this.worldpane, enemyX, enemyY));

        }
    }

    public void generateREnemies(){
        int renemiesNum = 3;

        for (int i = 0; i < renemiesNum; i++){
            double enemyX = Math.random() *  1000 + 200;
            double enemyY = 0;

            this.enemyList.add(new RangedEnemy(this.worldpane, enemyX, enemyY));

        }
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


}
