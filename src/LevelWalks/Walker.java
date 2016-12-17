package LevelWalks;
import Normal.*;


/**
 * Created by oskar on 2016-11-17.
 * Walks around, old protoype
 */

public class Walker extends Player {

    public Walker(double x, double y) {
        super("player");
        this.x = x;
        this.y = y;
    }

    public void inputs(int[] sensorData, boolean[] digitalData) {
        int inp_xdir = InputConstants.P1_SLIDE;
        int inp_ydir = InputConstants.P2_SLIDE;
        int inp_speed = InputConstants.P3_SLIDE;

        double slider = normalize(sensorData[inp_xdir])- 0.5;
        double dial   = normalize(sensorData[inp_ydir]) - 0.5;
        double light  = normalize(sensorData[inp_speed]);

        dx = slider * light * 5;
        dy = dial * light * 5;
    }
}

