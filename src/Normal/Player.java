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
    protected World world;
    protected int points = 0;

    public Player(World world, String imagePath) {
        this.world = world;
        String string = imagePath;
        image = ImageFunction.loadImage(string);

        setSizeFromImage();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void move(List<Block> blocks) {
        double previousX = x;
        double previousY = y;

        int halfSize = getSize().getHeight() / 2;

        x = Math.max(Math.min(x + dx, FrameConstants.WIDTH.value - halfSize), halfSize);

        boolean collide = false;

        for (Block block : blocks) {
            if (collision(block)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            x = previousX;
        }

        y = Math.max(Math.min(y + dy, FrameConstants.HEIGHT.value - halfSize), halfSize);

        collide = false;

        for (Block block : blocks) {
            if (collision(block)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            y = previousY;
        }
    }

    public void draw(Graphics2D g2d) {
        world.drawEntity(g2d, this);
    }

    public double normalize(int value) {
        return (double) value / 1000;
    }

    public abstract void inputs(int[] sensorData, boolean[] digitalData);

    public void givePoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }
}
