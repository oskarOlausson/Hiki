package LevelColor;

import Normal.FrameConstants;
import Normal.InputConstants;
import Normal.Player;
import Normal.World;

import java.awt.*;

/**
 * Created by oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
public class ColorSlider extends Player{

    private int sensorIndex = InputConstants.P1_SLIDE;
    private int sensorIndex2= InputConstants.P2_SLIDE;
    private int pad = 50;
    private double gotoX;
    private double colorIndexDouble = 0;
    private int colorIndex = 0;

    public ColorSlider(World world, double x, double y) {
        super(world, null);
        this.x = x;
        this.y = y;
        gotoX = x;
        colorIndex = (int) Math.round(Math.random() * 2);
        width = 60;
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

    public void setControl(int sensorIndex, int sensorIndex2) {
        this.sensorIndex = sensorIndex;
        this.sensorIndex2 = sensorIndex2;
    }

    public void move() {
        x = 0.8 * x + 0.2 * gotoX;
        if (Math.abs(x - gotoX) < 2) x = gotoX;
    }

    @Override
    public void inputs(int[] sensorData, boolean[] digitalData) {
        double sensorValue = normalize(sensorData[sensorIndex]);
        colorIndexDouble = 3 * normalize(sensorData[sensorIndex2]);
        colorIndex = (int) (colorIndexDouble);
        if (colorIndex > 2) colorIndex = 2;

        this.gotoX = pad + sensorValue * (FrameConstants.WIDTH.value - pad * 2);
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

    public void drawInterface(Graphics g, Colors colors) {

        int boxWidth = 20;
        int boxHeight = 40;

        for (int i = 0; i < 3; i++) {
            g.setColor(colors.primaryGet(i));
            g.fillRect(getX() + boxWidth * i, (int) (y - boxHeight / 2), boxWidth, boxHeight);
        }

        g.setColor(Color.BLACK);
        g.fillRect((int) (getX() + boxWidth * colorIndexDouble) - 2, (int) (y - boxHeight / 2), 4, boxHeight);
        g.setColor(Color.WHITE);
        g.drawRect((int) (getX() + boxWidth * colorIndexDouble) - 2, (int) (y - boxHeight / 2), 4, boxHeight);
    }
}
