package indie;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * the PaneOrganizer class, it is what makes the pane organized and sets everything with each other
 */
public class PaneOrganizer {
    private ArrayList<WorldOrganizer> worldList;
    private int worldCur;
    private int maxWorldNum;
    private Game gameCur;
    private Player player;
    private int playerHP;
    private MediaPlayer mediaPlayer;
    private Inventory inventory;
    BorderPane root;
    public PaneOrganizer(){
        //create BorderPane
        this.root = new BorderPane();

        //set current world number
        this.worldCur = 0;

        //Create WorldPane
        Pane worldpane = new Pane();
        this.setUpSituation(worldpane);
        this.root.setCenter(worldpane);

        //Create world list which will store our level/worlds
        this.worldList = new ArrayList<WorldOrganizer>();

        //sets the number of worlds (note that 3 means 4 levels + 1 boss level);
        this.maxWorldNum = Constants.MAX_NUM_WORLDS;
        //spawn the player
        this.player = new Player(worldpane);
        //adds an Inventory
        this.inventory = new Inventory(worldpane, this.player);

        //add a world
        this.worldList.add(new WorldOrganizer(this, worldpane, this.inventory));

        //Create Access Menu
        HBox accesspane = new HBox();
        this.root.setTop(accesspane);
        this.setUpButton(accesspane);
        this.setUpNextLevelButton(accesspane);

        //create the current world
        this.worldList.get(this.worldCur).generateEvery();
        this.gameCur = new Game(worldpane, this.worldList.get(this.worldCur), this.player);
        this.gameCur.intitalization();

        this.playerHP = this.player.getHP();


        //Music:
        this.musicPlayer();

    };

    /**
     * the getRoot method, it returns the root
     */
    public BorderPane getRoot(){
        return this.root;
    };


    /**
     * the setUpSituation method, it sets up the background of the world
     */
    public void setUpSituation(Pane worldpane){
        Image image = new Image("indie/backgrounds/Celeste1.png",Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_HEIGHT, false, true);
        worldpane.setBackground(new Background(new BackgroundImage(image, null, null, null, null)));
    }

    public void setUpButton(HBox hbox) {
        //creates the quit button
        hbox.setAlignment(Pos.CENTER);
        Button quit = new Button("Quit");
        hbox.getChildren().add(quit);

        //makes it so that pressing it quits the game
        quit.setOnAction((event) -> {
            System.exit(0);
        });
        hbox.setFocusTraversable(false);
        quit.setFocusTraversable(false);
    };

    public void setUpNextLevelButton(HBox hbox) {
        //creates the quit button
        hbox.setAlignment(Pos.TOP_LEFT);
        Button nextlevel = new Button("Next Level");
        hbox.getChildren().add(nextlevel);

        //makes it so that pressing it quits the game
        nextlevel.setOnAction((event) -> {
            this.nextWorld();

        });
        hbox.setFocusTraversable(false);
        nextlevel.setFocusTraversable(false);
    };


    public void nextWorld(){
        if (this.worldCur<this.maxWorldNum) {
            //delete this world
            //is this really necessary?
            this.worldList.get(this.worldCur).removeEvery();
            this.gameCur.clearpProjList();


            //get the next one
            this.worldCur++;

            //set the world to this next one:
            StackPane orderpane = new StackPane();
            Pane worldpane = new Pane();
            this.setUpSituation(worldpane);
            this.root.setCenter(worldpane);

            //create the nextWorld;
            this.playerHP = this.player.getHP();
            this.player = new Player(worldpane);
            this.player.setHP(this.playerHP);

            this.inventory.changePane(worldpane);
            this.inventory.changePlayer(this.player);



            this.worldList.add(new WorldOrganizer(this, worldpane, this.inventory));

            this.worldList.get(this.worldCur).generateEvery();
            this.gameCur = new Game(worldpane, this.worldList.get(this.worldCur), this.player);
            this.gameCur.intitalization();

            this.mediaPlayer.dispose();
            this.musicPlayer();



            System.out.println(this.worldCur);
        }
        else if(this.worldCur == this.maxWorldNum){
            //delete this world
            //is this really necessary?
            this.worldList.get(this.worldCur).removeEvery();
            this.gameCur.clearpProjList();


            //get the next one
            this.worldCur++;

            //set the world to this next one:
            Pane worldpane = new Pane();
            this.setUpSituation(worldpane);
            this.root.setCenter(worldpane);

            //create the nextWorld;
            //get player current HP
            this.playerHP = this.player.getHP();
            //make new player
            this.player = new Player(worldpane);
            //set new player to current HP
            this.player.setHP(this.playerHP);

            this.inventory.changePane(worldpane);
            this.inventory.changePlayer(this.player);



            this.worldList.add(new WorldOrganizer(this, worldpane, this.inventory));

            this.worldList.get(this.worldCur).boss_level_generation();
            this.gameCur = new Game(worldpane, this.worldList.get(this.worldCur), this.player);
            this.gameCur.intitalization();


            System.out.println(this.worldCur);
            this.mediaPlayer.dispose();
            this.bossMusicPlayer();
        }


    };
    public int getWorldCur(){
        return this.worldCur;
    }


    public void musicPlayer(){
        int generate_music = (int) Math.floor(Math.random() * 5);
        System.out.println(generate_music);
        String musicLink = "";

        switch (generate_music){
            case 0:
                musicLink = "/indie/backgrounds/Carmen - Habanera.mp3";
                break;
            case 1:
                musicLink = "/indie/backgrounds/Chopin - chouchou op.9-2 -Nocturne- Shoujo Shuumatsu Ryokou OSTEps. 12.mp3";
                break;
            case 2:
                musicLink = "/indie/backgrounds/Hollow Knight OST - Dirtmouth.mp3";
                break;
            case 3:
                musicLink = "/indie/backgrounds/La tour Eiffel est toujours là.mp3";
                break;
            case 4:
                musicLink = "/indie/backgrounds/Nightcore  Le Lac Des Cygnes ( Tchaikovsky ).mp3";
                break;
            case 5:
                musicLink = "/indie/backgrounds/UNDERTALE Piano Collections_ 03. It's Raining Somewhere Else.mp3";
                break;
        }

        System.out.println(musicLink);


        this.music(musicLink);

    }

    public void bossMusicPlayer(){
       this.mediaPlayer.dispose();
       String musicLink= "/indie/Enemies/Spain Theme - Atomic (Civilization 6 OST)  Recuerdos de la Alhambra.mp3";
       this.music(musicLink);

    }



    public void music(String musicLink){
        Media media = null;
        try {
            media = new Media(getClass().getResource(musicLink).toURI().toString());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setAutoPlay(true);



    }








}
