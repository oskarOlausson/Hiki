package LevelChoice;

import Normal.Position;
import Normal.Size;

import java.awt.*;
import java.util.List;

/**
 * Created by oskar on 2016-12-03.
 * This classes has some inputs and outputs
 */
public class TextBox {

    String string;
    private Font fontNormal = new Font("Sans-Serif", Font.PLAIN, 20);
    private Font fontUpsideDown = new Font("Sans-Serif", Font.PLAIN, 20);

    private static Color blockColor = new Color(30,65,180);
    private static Color maybeColor = new Color(10,45,160);
    private static Color chosenColor = new Color(180,180,20);
    private static Color textColor = new Color(140, 140, 190);
    private static Color textColorChosen = new Color(10, 10, 10);
    private Size size = new Size(350, 100);
    private boolean chosen = false;
    private boolean maybe = false;

    public void update(boolean maybe, boolean chosen) {
        this.chosen = chosen;
        this.maybe = maybe;
    }

    public boolean isChosen() {
        return chosen;
    }

    public TextBox(String string) {
        this.string = string;
    }

    public void draw(Graphics g, int x, int y, double percent, boolean upsideDown) {
        if (upsideDown) g.setFont(fontNormal);
        else g.setFont(fontUpsideDown);

        FontMetrics fm = g.getFontMetrics();

        if (maybe) g.setColor(maybeColor);
        else  g.setColor(blockColor);

        g.fillRect(x, y, size.getWidth(), size.getHeight());

        if (chosen) {
            g.setColor(chosenColor);
            g.fillRect(x, y, (int) (size.getWidth() * percent), size.getHeight());
        }

        int stringX, stringY;

        stringY = y + size.getHeight() / 2 - fm.getHeight() / 2 + fm.getAscent();

        String[] lines = string.split("\n");

        if (chosen) g.setColor(textColorChosen);
        else g.setColor(textColor);

        for (int i = 0; i < lines.length; i++) {
            stringX = x + size.getWidth()  / 2 - fm.stringWidth(lines[i]) / 2;
            g.drawString(lines[i], stringX, (int) (stringY + ((i + .5) - lines.length / 2f) * fm.getHeight()));
        }
    }

    public int getHeight() {
        return size.getHeight();
    }

    public void draw(Graphics g, int x, int y, double percent) {
        draw(g, x, y, percent, false);
    }
}
