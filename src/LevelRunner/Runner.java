package LevelRunner;

import Normal.*;
import java.awt.*;

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
    public void draw(Graphics2D g2d) {
        world.drawEntity(g2d, this);
    }

    @Override
    public void inputs(int[] sensorData, boolean[] digitalData) {

        degrees = 360 * (normalize(sensorData[InputConstants.P1_SLIDE]) - 0.5);
        speed = speedMax * (1.2 - normalize(sensorData[InputConstants.P2_SLIDE]));

        dx = speed * Math.cos(Math.toRadians(degrees));
        dy = speed * Math.sin(Math.toRadians(degrees));
    }
}
