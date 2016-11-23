package LevelMat; /**
 * Created by oskar on 2016-11-17.
 */

import Normal.*;

import java.awt.*;
import java.util.List;

public class Mat extends Player {

    private int inp_topLeft = InputConstants.P1_SLIDE;
    private int inp_topRight = InputConstants.P2_SLIDE;
    private int inp_bottomLeft= InputConstants.P3_SLIDE;
    private int inp_bottomRight = InputConstants.P4_SLIDE;

    private World world;

    public Mat(World world) {
        super(world, "Images/player.png");
        initWalker();
        this.world = world;
    }

    public Mat(World world, double x, double y) {
        this(world);
        this.x = x;
        this.y = y;
    }

    public void setSensors(int inp_topLeft, int inp_topRight, int inp_bottomLeft, int inp_bottomRight) {
        if (inp_topLeft != -1) {
            this.inp_topLeft = inp_topLeft;
        }
        if (inp_topRight != -1) {
            this.inp_topRight = inp_topRight;
        }
        if (inp_bottomLeft != -1) {
            this.inp_bottomLeft = inp_bottomLeft;
        }
        if (inp_bottomRight != -1) {
            this.inp_bottomRight = inp_bottomRight;
        }
    }

    private void initWalker() {

        String string = "Images/player.png";
        image = ImageFunction.loadImage(string);

        setSizeFromImage();
        x = 100;
        y = 100;
    }

    public void inputs(int[] sensorData, boolean[] digitalData) {
        double tl = normalize(sensorData[inp_topLeft]);
        double tr = normalize(sensorData[inp_topRight]);
        double bl = normalize(sensorData[inp_bottomLeft]);
        double br = normalize(sensorData[inp_bottomRight]);

        dx = (tr + br) - (tl + bl);
        dy = (bl + br) - (tl + tr);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Position pos = getCenter();
        int cx = pos.drawX();
        int cy = pos.drawY();

        g2d.setPaint(new Color(20, 20, 20));

        g2d.drawString(InputConstants.sensorToString(inp_topLeft), cx - 100, cy - 100);
        g2d.drawString(InputConstants.sensorToString(inp_topRight), cx + 100, cy - 100);
        g2d.drawString(InputConstants.sensorToString(inp_bottomLeft), cx - 100, cy + 100);
        g2d.drawString(InputConstants.sensorToString(inp_bottomRight), cx + 100, cy + 100);

        g2d.drawLine(cx, cy, cx - 800, cy - 800);
        g2d.drawLine(cx, cy, cx + 800, cy - 800);
        g2d.drawLine(cx, cy, cx - 800, cy + 800);
        g2d.drawLine(cx, cy, cx + 800, cy + 800);

        world.drawEntity(g2d, this);
    }
}

