package Normal;

import java.awt.*;

/**
 * Created by oskar on 2016-12-16.
 * This classes has some inputs and outputs
 */
public class MenuButton {

    private final int index;
    private final int x;
    private final int y;
    private final int centerX;
    private final int centerY;
    private boolean animationDone;
    private int smallSize = 40;
    private boolean done;
    private Image imageNotDone = Library.loadImage("button");
    private Image imageDone = Library.loadImage("buttonGreen");
    private double size = 0;

    private Color color = new Color(200, 200, 200, 255);
    private boolean pressed = true;

    public MenuButton(int index, int x, int y) {
        this.index = index;
        done = false;
        this.x = x - imageDone.getWidth(null) / 2;
        this.y = y - imageDone.getWidth(null) / 2;
        this.centerX = x;
        this.centerY = y;
        animationDone = false;
    }

    public void draw(Graphics g) {
        if (done) {
            if (size > 0 && size < 1000) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g2.setColor(color);
                g2.drawOval((int) (centerX - size / 2), (int) (centerY - size / 2), (int) size, (int) size);
            }
            DrawFunctions.drawImage(g, imageDone, x, y);
        }
        else DrawFunctions.drawImage(g, imageNotDone, x, y);
    }

    public boolean update(boolean[] buttonArray) {
        if (done) {
            color = new Color(color.getRed(), color.getBlue(), color.getGreen(), Math.max(0, color.getAlpha() - 8));
            if (size < 1000) size = size * 1.1;
            else animationDone = true;
        }

        if (buttonArray[index]) {
            if (pressed) {
                done = true;
                size = smallSize;
                color = new Color(color.getRed(), color.getBlue(), color.getGreen(), 255);
                pressed = false;
            }
        }
        else pressed = true;

        return false;
    }

    public void reset() {

        done = false;
        animationDone = false;
        color = new Color(color.getRed(), color.getBlue(), color.getGreen(), 255);
        size = smallSize;
    }

    public boolean isDone() {
        return animationDone;
    }
}
