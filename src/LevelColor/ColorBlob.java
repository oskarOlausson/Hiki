package LevelColor;

import Normal.Entity;
import Normal.FrameConstants;

import java.awt.*;

/**
 * Created by oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
class ColorBlob extends Entity{
    private int index;
    private int index2 = -1;
    private int size = 20;
    private boolean mix = false;


    ColorBlob(int index) {

        if (index < 0) {
            index = (int) Math.round(Math.random() * 2);
        }
        this.index = index;
        this.width = size;
        this.height = size;
        dx = 1 + Math.random();

        y = 150 + (Math.random() * (FrameConstants.HEIGHT.value - 300));
    }

    ColorBlob(int index, int index2) {
        this(index);
        if (index != index2) {
            this.index2 = index2;
            mix = true;
        }
        else {
            System.err.println("Tried to mix the same colors, rather pointless. " +index +" = " + index2);
        }

        dx = 0.5 + 0.5 * Math.random();
    }

    boolean collision(double lowRange, double highRange) {
        double rangeCenter = (lowRange + highRange) / 2;
        double rangeWidth = highRange - lowRange;
        return (Math.abs(rangeCenter - x) < (rangeWidth + width) / 2);
    }

    boolean match(int index) {
        if (!mix) {
            if (this.index == index) return true;
        }
        return false;
    }

    boolean match(int index, int index2) {
        if (mix) {
            if (this.index == index && this.index2 == index2)       return true;
            else if (this.index == index2 && this.index2 == index)  return true;
        }
        return false;
    }

    boolean isMix() {
        return mix;
    }

    public void update() {
        x += dx;
    }

    void draw(Graphics g, Colors colors) {
        if (isMix()) {
            g.setColor(colors.secondaryGet(getIndex(), getIndex2()));
        }
        else g.setColor(colors.primaryGet(getIndex()));

        g.fillOval(getX(), getY(), size, size);
    }

    int getIndex() {
        return index;
    }

    int getIndex2() {
        if (index2 == -1) {
            System.err.println("Can´t get index2, is a primary color");
        }
        return index2;
    }
}
