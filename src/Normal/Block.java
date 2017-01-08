package Normal;

import java.awt.*;

/**
 * Created by oskar on 2016-11-17.
 * This classes has some inputs and outputs
 */
public class Block extends Entity {

    Image image2;

    public Block(double x, double y) {
        this.x = x;
        this.y = y;
        image = Library.loadImage("block");
        image2 = Library.loadImage("blockBottom");
        setSizeFromImage();
    }


    public void draw(Graphics g) {
        DrawFunctions.drawImage(g, image2, getX(), (int) (getY() + height));
        DrawFunctions.drawImage(g, image,  getX(), getY());
    }
}
