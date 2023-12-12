package indie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * This is the SeaweedING class, it creates defines the properies of the Seaweed ingredient
 */
public class SeaweedING implements Ingredients{
    private String type;
    private ImageView seaweedImage;
    private Boolean shown;
    public SeaweedING(){
        this.type = "seaweed";
        this.shown = false;
        this.seaweedImage = new ImageView();


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
     * This is the showImage method, it displays the image of the seaweed for use
     */
    public void showImage(Pane worldpane){
        this.seaweedImage.setImage(new Image("indie/Enemies/Seaweed.png",Constants.ING_SIZE, Constants.ING_SIZE, true, true));
        worldpane.getChildren().add(this.seaweedImage);
        this.shown = true;

    }
    /**
     * This is the removeImage method, it removes the image of the seaweed in inventory
     */
    public void removeImage(Pane worldpane){
        worldpane.getChildren().remove(this.seaweedImage);
        this.shown = false;

    }
    /**
     * This is the getImage method, it gets the image of the seaweed in inventory
     */
    public ImageView getImage(){
        return this.seaweedImage;
    }
    /**
     * This is the getShown method, it gets if the seaweed is displaying its image
     */
    public boolean getShown(){
        return this.shown;
    }


}
