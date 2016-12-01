package LevelColor;

import Normal.Entity;
import Normal.FrameConstants;

import java.awt.*;

/**
 * Created by oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
public class ColorBlob extends Entity{
    private int index;
    private int index2 = -1;
    private int size = 20;
    private boolean mix = false;

    public ColorBlob(int index) {
        this.index = index;
        this.width = size;
        this.height = size;
        dx = 2;

        y = 100 + (Math.random() * (FrameConstants.HEIGHT.value - 200));
    }

    public ColorBlob(int index, int index2) {
        this(index);
        if (index != index2) {
            this.index2 = index2;
            mix = true;
        }
        else {
            System.err.println("Tried to mix the same colors, rather pointless. " +index +" = " + index2);
        }
    }

    public boolean collision(double lowRange, double highRange) {
        double rangeCenter = (lowRange + highRange) / 2;
        double rangeWidth = highRange - lowRange;
        return (Math.abs(rangeCenter - x) < (rangeWidth + width) / 2);
    }

    public boolean match(int index) {
        if (!mix) {
            if (this.index == index) return true;
        }
        return false;
    }

    public boolean match(int index, int index2) {
        if (mix) {
            if (this.index == index && this.index2 == index2)       return true;
            else if (this.index == index2 && this.index2 == index)  return true;
        }
        return false;
    }

    public boolean isMix() {
        return mix;
    }

    public void update() {
        x += dx;
        if (x > FrameConstants.WIDTH.value) {
            delete();
        }
    }

    public void draw(Graphics g, Colors colors) {
        if (isMix()) {
            g.setColor(colors.secondaryGet(getIndex(), getIndex2()));
        }
        else g.setColor(colors.primaryGet(getIndex()));

        g.fillOval(getX(), getY(), size, size);
    }

    public int getIndex() {
        return index;
    }

    public int getIndex2() {
        if (index2 == -1) {
            System.err.println("Can´t get index2, is a primary color");
        }
        return index2;
    }
}