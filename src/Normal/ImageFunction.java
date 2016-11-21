package Normal; /**
 * Created by oskar on 2016-11-17.
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by oskar on 2016-10-15.
 * Static class which has an important function used to load images
 */
public class ImageFunction {

    public static BufferedImage loadImage(String filePath) {

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filePath));
        } catch (IOException e){
            System.err.println("Could not find file at: '" + filePath+"'");
            System.exit(2);
        }

        return img;
    }

    public static void main(String[] arguments) {
        loadImage("Images/lock.png");
    }
}
