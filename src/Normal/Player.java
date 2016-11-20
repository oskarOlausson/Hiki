package Normal;

import java.awt.*;
import java.util.List;

/**
 * Created by oskar on 2016-11-18.
 * This class is a parent class of moving avatars that you control
 * is not needed but is a good foundation of what is needed of a typical player
 */
public abstract class Player extends Entity{
    /**
     * Created by oskar on 2016-11-17.
     */
    private World world;

    public Player(World world, String imagePath) {
        this.world = world;
        String string = "Images/player.png";
        image = ImageFunction.loadImage(string);

        setSizeFromImage();
    }

    public abstract void move(List<Block> blocks);

    public abstract void draw(Graphics2D g2d);

    public abstract void inputs(int[] sensorData);
}
