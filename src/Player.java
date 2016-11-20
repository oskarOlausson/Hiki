import java.awt.*;
import java.util.List;

/**
 * Created by oskar on 2016-11-18.
 * This classes has some inputs and outputs
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
