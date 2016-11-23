package LevelWalks; /**
 * Created by oskar on 2016-11-17.
 */

import Normal.*;

import java.awt.*;
import java.util.List;

public class Walker extends Player {

    private int inp_xdir = InputConstants.P1_SLIDE;
    private int inp_ydir = InputConstants.P2_SLIDE;
    private int inp_speed = InputConstants.P3_SLIDE;

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

    public void inputs(int[] sensorData, boolean[] digitalData) {
        double slider = normalize(sensorData[inp_xdir])- 0.5;
        double dial   = normalize(sensorData[inp_ydir]) - 0.5;
        double light  = normalize(sensorData[inp_speed]);
        dx = slider * light * 5;
        dy = dial * light * 5;
    }
}

