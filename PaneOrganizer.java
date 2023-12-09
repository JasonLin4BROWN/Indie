package wonders;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class PaneOrganizer {
    private ArrayList<WorldOrganizer> worldList;
    private int worldCur;
    private int maxWorldNum;
    private Game gameCur;

    private Inventory inventory;
    BorderPane root;
    public PaneOrganizer(){
        this.root = new BorderPane();
        this.worldCur = 0;

        //Create WorldPane
        Pane worldpane = new Pane();
        this.setUpSituation(worldpane);
        this.root.setCenter(worldpane);

        //Create WorldOrganizer and by extension world:
        this.worldList = new ArrayList<WorldOrganizer>();

        //sets the number of worlds
        this.maxWorldNum = 10;

        //adds an Inventory
        this.inventory = new Inventory(worldpane);

        //add a world
        this.worldList.add(new WorldOrganizer(this, worldpane, this.inventory));

        //Create Access Menu
        HBox accesspane = new HBox();
        this.root.setTop(accesspane);
        this.setUpButton(accesspane);
        this.setUpNextLevelButton(accesspane);

        this.worldList.get(this.worldCur).generateEvery();
        this.gameCur = new Game(this.root,worldpane, this.worldList.get(this.worldCur));
        this.gameCur.intitalization();


        //Music:
        this.music();





    };

    public BorderPane getRoot(){
        return this.root;
    };

    public void setUpSituation(Pane worldpane){
        ImageView iv = new ImageView();
        Image image = new Image("wonders/backgrounds/Celeste1.png",1450, 875, false, true);
        //, Constants.SCENE_WIDTH-450, Constants.SCENE_HEIGHT-300, false, true)

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
            this.inventory.changePane(worldpane);
            this.worldList.add(new WorldOrganizer(this, worldpane, this.inventory));

            this.worldList.get(this.worldCur).generateEvery();
            this.gameCur = new Game(this.root,worldpane, this.worldList.get(this.worldCur));
            this.gameCur.intitalization();
            System.out.println(this.worldCur);



        }


    };
    public int getWorldCur(){
        return this.worldCur;
    }



    public void music(){
        Media media = null;
        try {
            media = new Media(getClass().getResource("/wonders/backgrounds/Hollow Knight OST - Dirtmouth.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);



    }








}
