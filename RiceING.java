package wonders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class RiceING implements Ingredients{
    private String type;
    private ImageView riceImage;
    private Boolean shown;
    public RiceING(){
        this.type = "rice";
        this.shown = false;
        this.riceImage = new ImageView();


    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public void showImage(Pane worldpane){
        this.riceImage.setImage(new Image("wonders/Enemies/Rice.png",65, 65, true, true));

        worldpane.getChildren().add(this.riceImage);
        this.shown = true;

    }

    public void removeImage(Pane worldpane){
        worldpane.getChildren().remove(this.riceImage);
        this.shown = false;

    }

    public ImageView getImage(){
        return this.riceImage;
    }

    public boolean getShown(){
        return this.shown;
    }


}
