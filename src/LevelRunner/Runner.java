package LevelRunner;

import Normal.*;
import java.awt.*;
import java.util.List;

/**
 * Created by oskar on 2016-11-21.
 * This classes has some inputs and outputs
 */
public class Runner extends Player{

    private double speed;
    private double speedMax;

    public Runner(World world, double x, double y) {
        super(world,"Images/player.png");
        this.x = x;
        this.y = y;
        speed = 1;
        speedMax = 5;
    }

    @Override
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

    @Override
    public void draw(Graphics2D g2d) {
        world.drawEntity(g2d, this);
    }

    @Override
    public void inputs(int[] sensorData) {
        degrees = 60 * ((sensorData[InputConstants.SLIDER] / 1000) - 0.5);
        speed = speedMax * (1.2 - (sensorData[InputConstants.PREASSURE] / 1000));
        System.out.println(degrees);

        //dx = speed * Math.cos(Math.toRadians(degrees));
        //dy = speed * Math.sin(Math.toRadians(degrees));
    }
}
