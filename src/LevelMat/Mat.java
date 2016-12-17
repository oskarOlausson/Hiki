package LevelMat;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-11-17.
 * Handles all thing in the mat level
 */
class Mat extends Player {

    private int inp_topLeft = InputConstants.P1_SLIDE;
    private int inp_topRight = InputConstants.P2_SLIDE;
    private int inp_bottomLeft= InputConstants.P3_SLIDE;
    private int inp_bottomRight = InputConstants.P4_SLIDE;



    Mat() {
        super("player");
        initWalker();
    }

    Mat(double x, double y) {
        this();
        this.x = x;
        this.y = y;
    }

    private void initWalker() {

        String string = "player";
        image = Library.loadImage(string);

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

        DrawFunctions.drawImage(g2d, image, getX(), getY());
    }
}

