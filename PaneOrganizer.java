package wonders;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class PaneOrganizer {
    private ArrayList<WorldOrganizer> worldList;
    private int worldCur;
    private int maxWorldNum;
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

        this.worldList.add(new WorldOrganizer(this, worldpane));

        //Create Access Menu
        HBox accesspane = new HBox();
        this.root.setTop(accesspane);
        this.setUpButton(accesspane);
        this.setUpNextLevelButton(accesspane);

        new Game(this.root,worldpane, this.worldList.get(this.worldCur));
        this.worldList.get(this.worldCur).generateEvery();


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

            //get the next one
            this.worldCur++;

            //set the world to this next one:
            Pane worldpane = new Pane();
            this.setUpSituation(worldpane);
            this.root.setCenter(worldpane);

            //create the nextWorld;
            this.worldList.add(new WorldOrganizer(this, worldpane));


            new Game(this.root,worldpane, this.worldList.get(this.worldCur));
            this.worldList.get(this.worldCur).generateEvery();




        }


    };
    public int getWorldCur(){
        return this.worldCur;
    }








}
