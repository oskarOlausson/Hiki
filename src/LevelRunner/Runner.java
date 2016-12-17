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
        super("player");
        this.x = x;
        this.y = y;
        speed = 1;
        speedMax = 5;
    }

    @Override
    public void inputs(int[] sensorData, boolean[] digitalData) {

        direction = 360 * (normalize(sensorData[InputConstants.P1_SLIDE]) - 0.5);
        speed = speedMax * (1.2 - normalize(sensorData[InputConstants.P2_SLIDE]));

        dx = speed * Math.cos(Math.toRadians(direction));
        dy = speed * Math.sin(Math.toRadians(direction));
    }
}
