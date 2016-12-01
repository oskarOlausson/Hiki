package LevelColor;

import Normal.FrameConstants;
import Normal.InputConstants;
import Normal.Player;
import Normal.World;

/**
 * Created by oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
public class ColorSlider extends Player{

    private int sensorIndex = InputConstants.P1_SLIDE;
    private int digitalIndex = InputConstants.P2_SLIDE;
    private int pad = 50;
    private boolean pressed = false;
    private double gotoX;
    private int colorIndex = 0;

    public ColorSlider(World world, double x, double y) {
        super(world, null);
        this.x = x;
        this.y = y;
        gotoX = x;
        colorIndex = (int) Math.round(Math.random() * 2);
        width = 40;
    }

    public int getIndex() {
        return colorIndex;
    }

    public void changeColor() {
        colorIndex += 1;
        if (colorIndex > 2) {
            colorIndex = 0;
        }
    }

    public void setControl(int sensorIndex, int digitalIndex) {
        this.sensorIndex = sensorIndex;
        this.digitalIndex = digitalIndex;
    }

    public void move() {
        x = 0.8 * x + 0.2 * gotoX;
        if (Math.abs(x - gotoX) < 2) x = gotoX;
    }

    @Override
    public void inputs(int[] sensorData, boolean[] digitalData) {
        double sensorValue = normalize(sensorData[sensorIndex]);
        boolean digitalValue = digitalData[digitalIndex];

        this.gotoX = pad + sensorValue * (FrameConstants.WIDTH.value - pad * 2);
        if (digitalValue) {

            if (!pressed) {
                changeColor();
            }

            pressed = true;
        }
        else pressed = false;
    }

    public double lowPoint() {
        return x - width / 2;
    }

    public double highPoint() {
        return x + width / 2;
    }

    public boolean overLaps(ColorSlider colorSlider2) {
        double dist = Math.abs(getX() - colorSlider2.getX());
        return dist < width;
    }

    public double getWidth() {
        return width;
    }
}
