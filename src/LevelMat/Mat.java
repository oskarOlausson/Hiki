package LevelMat;

import Normal.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by oskar on 2016-11-17.
 * Handles all thing in the mat level
 */
class Mat extends Player {

    private Color color1 = new Color(120, 64, 44);
    private Color color2 = new Color(200, 145, 99);
    private BasicStroke myStroke = new BasicStroke(3);

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
        String string = "mat";
        image = Library.loadImage(string);

        setSizeFromImage();
        x = 100;
        y = 100;
    }

    @Override
    public void inputs() {
        double tl = playerControllers.get(0).getSliderValue();
        double tr = playerControllers.get(1).getSliderValue();
        double bl = playerControllers.get(2).getSliderValue();
        double br = playerControllers.get(3).getSliderValue();

        dx = (tr + br) - (tl + bl);
        dy = (bl + br) - (tl + tr);
    }

    @Override
    public void draw(Graphics2D g2d) {
        Position pos = getCenter();
        int cx = pos.drawX();
        int cy = pos.drawY();

        drawRope(g2d, cx, cy, cx - 800, cy - 800, color1, color2);
        drawRope(g2d, cx, cy, cx + 800, cy - 800, color1, color2);
        drawRope(g2d, cx, cy, cx - 800, cy + 800, color1, color2);
        drawRope(g2d, cx, cy, cx + 800, cy + 800, color1, color2);

        DrawFunctions.drawImage(g2d, image, getX(), getY());
    }

    private void drawRope(Graphics2D g2d, int x, int y, int x2, int y2, Color c1, Color c2) {
        Color colorBefore = g2d.getColor();
        Stroke strokeBefore = g2d.getStroke();

        g2d.setStroke(myStroke);
        int lenOfOne = 5;
        double dx;
        double dy;

        double totalDistance = (int) Math.sqrt(Math.pow(x - x2, 2) +  Math.pow(y - y2, 2));
        double angle = Math.atan2(x2 - x, y2 - y);

        for (int i = 0; i < totalDistance / lenOfOne; i++) {

            if (i % 2 == 0) {
                g2d.setColor(c1);
            }
            else g2d.setColor(c2);

            dx = Math.cos(angle) * lenOfOne;
            dy = Math.sin(angle) * lenOfOne;
            g2d.drawLine((int) (x + dx * i), (int) (y + dy * i), (int) (x + dx * (i + 1)), (int) (y + dy * (i + 1)));
        }

        g2d.setStroke(strokeBefore);
        g2d.setColor(colorBefore);
    }
}

