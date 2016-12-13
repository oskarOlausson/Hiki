package Normal;

/**
 * Created by oskar on 2016-10-15.
 * Static class which has an important function used to load images
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageFunctions {

    public static BufferedImage loadImage(String filePath) {

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filePath));
        } catch (IOException e){
            System.err.println("Could not find file at: '" + filePath+"'");

            try{
                img = ImageIO.read(new File("Images/Story/other.png"));
            } catch (IOException f){
                System.err.println("Could not find file at: '" + filePath+"'");
                System.exit(2);
            }
        }

        return img;
    }

    public static BufferedImage loadImage(String filePath, Color color) {
        BufferedImage img = loadImage(filePath);

        float r = color.getRed()    / 255f;
        float g = color.getGreen()  / 255f;
        float b = color.getBlue()   / 255f;
        float a = color.getAlpha()  / 255f;

        return tint(r, g, b, a, img);
    }

    public static BufferedImage loadImage(String filePath, Color color, double alpha) {
        BufferedImage img = loadImage(filePath);

        float r = color.getRed()    / 255f;
        float g = color.getGreen()  / 255f;
        float b = color.getBlue()   / 255f;
        float a = (float) alpha;

        return tint(r, g, b, a, img);
    }

    private static BufferedImage tint(float r, float g, float b, float a, BufferedImage img) {
        BufferedImage tintedSprite = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(img, 0, 0, null);
        graphics.dispose();

        for (int i = 0; i < tintedSprite.getWidth(); i++) {
            for (int j = 0; j < tintedSprite.getHeight(); j++) {
                int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                rx *= r;
                gx *= g;
                bx *= b;
                ax *= a;
                tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
            }
        }
        return tintedSprite;
    }



    public static void main(String[] arguments) {
        loadImage("Images/lock.png");
    }
}
