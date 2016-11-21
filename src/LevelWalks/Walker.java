package LevelWalks; /**
 * Created by oskar on 2016-11-17.
 */

import Normal.*;

import java.awt.*;
import java.util.List;

public class Walker extends Player {

    private int inp_xdir = InputConstants.SLIDER;
    private int inp_ydir = InputConstants.DIAL;
    private int inp_speed = InputConstants.LIGHT;

    public Walker(World world, double x, double y) {
        super(world, "Images/player.png");
        this.x = x;
        this.y = y;
    }

    /**
     * Use to alter which sensors controls which attribute
     * @param inp_xdir: side to side movement
     * @param inp_ydir: up and down movement
     * @param inp_speed: how fast it moves
     */
    public void setSensors(int inp_xdir, int inp_ydir, int inp_speed) {
        if (inp_xdir != -1) {
            this.inp_xdir = inp_xdir;
        }
        if (inp_ydir != -1) {
            this.inp_ydir = inp_ydir;
        }
        if (inp_speed != -1) {
            this.inp_speed = inp_speed;
        }
    }

    public void move(List<Block> blocks) {

        double previousX = x;
        double previousY = y;
        x = Math.max(Math.min(x + dx, FrameConstants.WIDTH.value), 0);

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

        y = Math.max(Math.min(y + dy, FrameConstants.HEIGHT.value), 0);

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

    public void inputs(int[] sensorData) {
        double slider = (((double) sensorData[inp_xdir]) / 1000) - 0.5;
        double dial = (((double) sensorData[inp_ydir]) / 1000) - 0.5;
        double light = (((double) sensorData[inp_speed]) / 1000);
        dx = slider * light * 5;
        dy = dial * light * 5;
    }

    public void draw(Graphics2D g2d) {
        world.drawEntity(g2d, this);
    }
}

