package Normal;

import java.awt.*;

/**
 * Created by oskar on 2016-11-23.
 * This classes has some inputs and outputs
 */
public class Particle {

    private double x;
    private double y;
    private double dy;
    private Color color;
    private double pulse = 0;
    private double normalWidth = 7 + Math.random() * 6;
    private double flipSpeed = 0.5 + Math.random() * 2;

    public Color getColor() {
        return color;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Particle() {
        this.x = Math.random() * FrameConstants.WIDTH.value;
        this.y = -Math.random() * 600;
        dy = 3 + Math.random() * 3;

        color = Color.getHSBColor(200, 200, (int) (Math.random() * 255));
    }

    public int getHeight() {
        return (int) normalWidth * 2;
    }

    public int getWidth() {
        pulse += flipSpeed;
        if (pulse > 360) {
            pulse -= 360;
        }
        return  (int)(normalWidth * Math.abs(Math.cos(Math.toRadians(pulse))));
    }

    public void move() {
        if (y < FrameConstants.HEIGHT.value + 100) {
            y += dy;
        }
        else {
            y = -100;
        }
    }
}
