package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This is the RiceING class, it creates defines the properies of the Rice ingredient
 */
public class RiceING implements Ingredients{
    private String type;
    private ImageView riceImage;
    private Boolean shown;
    public RiceING(){
        //define this ingredient's type as rice
        this.type = "rice";
        this.shown = false;
        this.riceImage = new ImageView();


    }

    /**
     * This is the setType method, it creates allows setting of Ingredient types
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * This is the getType method, it creates allows getting of Ingredient types
     */
    public String getType(){
        return this.type;
    }

    /**
     * This is the showImage method, it displays the rice image in the inventory
     */
    public void showImage(Pane worldpane){
        this.riceImage.setImage(new Image("indie/Enemies/white-rice-in-bow-free-png.png",Constants.ING_SIZE, Constants.ING_SIZE, true, true));
        worldpane.getChildren().add(this.riceImage);
        this.shown = true;

    }

    /**
     * This is the removeImage method, it removes the rice image in the inventory
     */
    public void removeImage(Pane worldpane){
        worldpane.getChildren().remove(this.riceImage);
        this.shown = false;

    }

    /**
     * This is the getImage method, it gets the rice image
     */
    public ImageView getImage(){
        return this.riceImage;
    }
    /**
     * This is the getShown method, it gets if the rice image is shown
     */
    public boolean getShown(){
        return this.shown;
    }


}
