/**
 * Created by oskar on 2016-11-17.
 */

import java.util.List;

public class Walker extends Player {

    private double max_speed = 10;
    private double acceleration = 3;
    private double friction = 3;
    private int inp_xdir = InputConstants.SLIDER;
    private int inp_ydir = InputConstants.DIAL;
    private int inp_speed = InputConstants.LIGHT;

    private World world;

    public Walker(World world) {
        super(world, "Images/player.png");
        initWalker();
        this.world = world;
    }

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

    private void initWalker() {

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
        double slider = (((double) sensorData[inp_xdir]) / 1000) - 0.5;
        double dial = (((double) sensorData[inp_ydir]) / 1000) - 0.5;
        double light = (((double) sensorData[inp_speed]) / 1000);
        dx = slider * light * 5;
        dy = dial * light * 5;
    }
}

