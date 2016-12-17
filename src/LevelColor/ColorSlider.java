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
class ColorSlider extends Player{

    private int sensorIndex = InputConstants.P1_SLIDE;
    private int sensorIndex2= InputConstants.P2_SLIDE;
    private int pad = 50;
    private double gotoX;
    private double colorIndexDouble = 0;
    private int colorIndex = 0;

    private int span = FrameConstants.WIDTH.value;

    ColorSlider(World world, double x, double y) {
        super(null);
        this.x = x;
        this.y = y;
        gotoX = x;
        colorIndex = (int) Math.round(Math.random() * 2);
        width = 60;
    }

    int getIndex() {
        return colorIndex;
    }

    void changeColor() {
        colorIndex += 1;
        if (colorIndex > 2) {
            colorIndex = 0;
        }
    }

    void setControl(int sensorIndex, int sensorIndex2) {
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

        this.gotoX = pad + sensorValue * (span - pad * 2);
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

        int boxWidth = 40;
        int boxHeight = 60;
        int totalWidth = boxWidth * 3;
        int drawX = (int) (getX() + width / 2d - totalWidth / 2d);

        for (int i = 0; i < 3; i++) {
            g.setColor(colors.primaryGet(i));
            g.fillRect(drawX + boxWidth * i, (int) (y - boxHeight / 2), boxWidth, boxHeight);
        }

        g.setColor(Color.BLACK);
        g.fillRect((int) (drawX + boxWidth * colorIndexDouble) - 2, (int) (y - boxHeight / 2), 4, boxHeight);
        g.setColor(Color.WHITE);
        g.drawRect((int) (drawX + boxWidth * colorIndexDouble) - 2, (int) (y - boxHeight / 2), 4, boxHeight);
    }

    public void setPad(int pad) {
        this.pad = pad;
    }
}
