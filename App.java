package indie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class App extends Application {

    public App(){};

    public void start(Stage stage){
        stage.setTitle("Cooking Wonders");

        //creating paneOrganizer
        PaneOrganizer paneOrganizer = new PaneOrganizer();

        //Setting the Scene
        Scene scene = new Scene(paneOrganizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        Scene mockscene = new Scene(new Pane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] var0) {
        launch(var0);
    }
}

