package LevelChoice;

import Normal.Position;
import Normal.Size;

import java.awt.*;

/**
 * Created by oskar on 2016-12-03.
 * This classes has some inputs and outputs
 */
public class TextBox {

    String string;
    private Font fontNormal = new Font("Sans-Serif", Font.PLAIN, 20);
    private Font fontUpsideDown = new Font("Sans-Serif", Font.PLAIN, 20);

    private Color blockColor = new Color(100,55,30);
    private Color textColor = new Color(50, 35, 10);
    private Size size = new Size(300, 60);

    public TextBox(String string) {
        this.string = string;
    }

    public void draw(Graphics g, int x, int y, boolean upsideDown) {
        if (upsideDown) g.setFont(fontNormal);
        else g.setFont(fontUpsideDown);

        FontMetrics fm = g.getFontMetrics();

        g.setColor(blockColor);
        g.fillRect(x, y, size.getWidth(), size.getHeight());

        int stringX, stringY;
        stringX = x + size.getWidth()  / 2 - fm.stringWidth(string) / 2;
        stringY = y + size.getHeight() / 2 - fm.getHeight() / 2 + fm.getAscent();

        g.setColor(textColor);
        g.drawString(string, stringX, stringY);
    }

    public int getHeight() {
        return size.getHeight();
    }

    public void draw(Graphics g, int x, int y) {
        draw(g, x, y, false);
    }
}
