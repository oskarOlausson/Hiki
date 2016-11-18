/**
 * Created by oskar on 2016-11-17.
 */

import java.awt.event.KeyEvent;
import java.util.List;

public class Player extends Entity {

    private double max_speed = 10;
    private double acceleration = 3;
    private double friction = 3;
    private World world;

    public Player(World world) {
        initFox();
        this.world = world;
    }

    private void initFox() {

        String string = "Images/player.png";
        image = ImageFunction.loadImage(string);

        setSizeFromImage();
        x = 100;
        y = 100;
    }

    public void move(List<Block> blocks) {

        double prevx = x;
        double prevy = y;
        x = Math.max(Math.min(x + dx, FrameConstants.WIDTH.value), 0);

        boolean collide = false;

        for (Block block : blocks) {
            if (collision(block)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            x = prevx;
        }

        y = Math.max(Math.min(y + dy, FrameConstants.HEIGHT.value), 0);

        collide = false;

        for (Block block : blocks) {
            if (collision(block)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            y = prevy;
        }
    }

    public void inputs(int[] sensorData) {
        double slider = (((double) sensorData[InputConstants.SLIDER]) / 1000) - 0.5;
        double dial = (((double) sensorData[InputConstants.DIAL]) / 1000) - 0.5;
        double light = (((double) sensorData[InputConstants.LIGHT]) / 1000);
        dx = slider * light * 5;
        dy = dial * light * 5;
    }
}

