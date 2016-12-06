package LevelChoice;

import Normal.Size;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-03.
 * This classes has some inputs and outputs
 */
class TextBox {

    private String string;
    private static final Font fontNormal = new Font("Sans-Serif", Font.PLAIN, 20);
    private static final Font fontUpsideDown = new Font("Sans-Serif", Font.PLAIN, 20);

    private static final Color inputColor       = new Color(0,25,140);
    private static final Color blockColor       = new Color(30,65,180);
    private static final Color maybeColor       = new Color(10,45,160);
    private static final Color chosenColor      = new Color(20,180,20);
    private static final Color textColor        = new Color(140, 140, 190);
    private static final Color textColorChosen  = new Color(10, 10, 10);

    private final Size size = new Size(350, 100);
    private boolean chosen = false;
    private boolean maybe = false;
    private List<String> inputText = new ArrayList<>();


    void update(boolean maybe, boolean chosen) {
        this.chosen = chosen;
        this.maybe = maybe;
    }

    boolean isChosen() {
        return chosen;
    }

    TextBox(String string, int input1, int input2) {

        this.string = string;
        inputText.add(inputToText(input1));
        inputText.add(inputToText(input2));
    }

    private String inputToText(int input) {
        switch(input) {
            case 0:     return "A";
            case 1:     return "B";
            case 2:     return "C";
            case 3:     return "D";
        }
        return "ERROR";
    }

    private void drawInput(Graphics g, int x, int y) {
        int size = this.size.getHeight() / 2;
        x = x - size;
        FontMetrics fm = g.getFontMetrics();
        int stringX, stringY;

        for (String text: inputText) {
            g.setColor(inputColor);
            g.fillRect(x, y, size, size);
            stringX = x + size / 2 - fm.stringWidth(text) / 2;
            stringY = y + size / 2 - fm.getHeight() / 2 + fm.getAscent();
            g.setColor(textColor);
            g.drawString(text, stringX, stringY);
            y += size;
        }
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
            int w = (int) (size.getWidth() * percent);
            int h = (int) (size.getHeight() * percent);
            int arc = 5;

            g.fillRoundRect(x + size.getWidth() / 2 - w / 2, y + size.getHeight() / 2 - h / 2, w, h, (int) (arc - (percent * arc)), (int) (arc - (percent * arc)));
        }

        int stringX, stringY;

        stringY = y + size.getHeight() / 2 - fm.getHeight() / 2 + fm.getAscent();

        String[] lines = string.split("\\r?\\n");

        if (chosen) g.setColor(textColorChosen);
        else g.setColor(textColor);

        for (int i = 0; i < lines.length; i++) {
            stringX = x + size.getWidth()  / 2 - fm.stringWidth(lines[i]) / 2;
            g.drawString(lines[i], stringX, (int) (stringY + ((i + .5) - lines.length / 2f) * fm.getHeight()));
        }

        drawInput(g, x, y);
    }

    public int getHeight() {
        return size.getHeight();
    }

    public void draw(Graphics g, int x, int y, double percent) {
        draw(g, x, y, percent, false);
    }
}
