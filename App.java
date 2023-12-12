package indie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is the App class that starts the indie game
 */
public class App extends Application {

    public App(){};

    /**
     * This is the start method, it creates our pane organizer, scene, and shows it all to play on
     */
    public void start(Stage stage){
        stage.setTitle("Cooking Wonders");

        //creating paneOrganizer
        PaneOrganizer paneOrganizer = new PaneOrganizer();

        //Setting the Scene
        Scene scene = new Scene(paneOrganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] var0) {
        launch(var0);
    }
}

