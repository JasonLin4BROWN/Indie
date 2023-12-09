package wonders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SeaweedING implements Ingredients{
    private String type;
    private ImageView seaweedImage;
    private Boolean shown;
    public SeaweedING(){
        this.type = "seaweed";
        this.shown = false;
        this.seaweedImage = new ImageView();


    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void showImage(Pane worldpane){
        this.seaweedImage.setImage(new Image("wonders/Enemies/Seaweed.png",65, 65, true, true));

        worldpane.getChildren().add(this.seaweedImage);
        this.shown = true;

    }

    public void removeImage(Pane worldpane){
        worldpane.getChildren().remove(this.seaweedImage);
        this.shown = false;

    }

    public ImageView getImage(){
        return this.seaweedImage;
    }

    public boolean getShown(){
        return this.shown;
    }


}
