package MatMover; /**
 * Created by oskar on 2016-11-17.
 */

import Normal.*;

import java.awt.*;
import java.util.List;

public class Mat extends Player {

    private int inp_topLeft = InputConstants.SLIDER;
    private int inp_topRight = InputConstants.DIAL;
    private int inp_bottomLeft= InputConstants.PREASSURE;
    private int inp_bottomRight = InputConstants.JOYX;

    private World world;

    public Mat(World world) {
        super(world, "Images/player.png");
        initWalker();
        this.world = world;
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

    public void move(List<Block> blocks) {

        double prevx = x;
        double prevy = y;

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
            x = prevx;
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
            y = prevy;
        }
    }

    public void inputs(int[] sensorData) {
        double tl = (((double) sensorData[inp_topLeft]) / 1000);
        double tr = (((double) sensorData[inp_topRight]) / 1000);
        double bl = (((double) sensorData[inp_bottomLeft]) / 1000);
        double br = (((double) sensorData[inp_bottomRight]) / 1000);

        dx = (tr + br) - (tl + bl);
        dy = (bl + br) - (tl + tr);
    }

    public void draw(Graphics2D g2d) {
        Position pos = getCenter();
        int cx = pos.drawX();
        int cy = pos.drawY();

        g2d.setPaint(new Color(20, 20, 20));

        g2d.drawString(InputConstants.toString(inp_topLeft), cx - 100, cy - 100);
        g2d.drawString(InputConstants.toString(inp_topRight), cx + 100, cy - 100);
        g2d.drawString(InputConstants.toString(inp_bottomLeft), cx - 100, cy + 100);
        g2d.drawString(InputConstants.toString(inp_bottomRight), cx + 100, cy + 100);

        g2d.drawLine(cx, cy, cx - 800, cy - 800);
        g2d.drawLine(cx, cy, cx + 800, cy - 800);
        g2d.drawLine(cx, cy, cx - 800, cy + 800);
        g2d.drawLine(cx, cy, cx + 800, cy + 800);

        world.drawEntity(g2d, this);
    }
}

