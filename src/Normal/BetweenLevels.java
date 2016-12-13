package Normal;

import java.awt.*;
import java.sql.Time;

/**
 * Created by oskar on 2016-12-13.
 * This classes has some inputs and outputs
 */
public class BetweenLevels {
    private static final Font fontNormal     = new Font("Sans-Serif", Font.PLAIN, 20);
    private static final Font fontUpsideDown = new Font("Sans-Serif", Font.PLAIN, -20);

    private static final Color blockColor = new Color(240,255,240);
    private static final Color textColor  = new Color(30, 20, 10);

    private final Size size = new Size(FrameConstants.WIDTH.value, FrameConstants.HEIGHT.value);
    private final boolean upSideDown;
    private String[] text;

    private Timer timer;

    public BetweenLevels(String string, boolean upSideDown) {
        text = string.split("\\r?\\n");
        this.upSideDown = upSideDown;
        timer = new Timer(5);
    }

    public void update() {
        timer.update();
    }

    public boolean timerDone() {
        return timer.isDone();
    }

    public void newTimer (Timer timer) {
        this.timer = timer;
    }

    public void draw(Graphics g, boolean upsideDown) {

        if (upsideDown) g.setFont(fontNormal);
        else g.setFont(fontUpsideDown);

        FontMetrics fm = g.getFontMetrics();

        g.setColor(blockColor);
        g.fillRect(0, 0, size.getWidth(), size.getHeight());

        int stringX, stringY;

        stringY = size.getHeight() / 2 - fm.getHeight() / 2 + fm.getAscent();

        g.setColor(textColor);

        for (int i = 0; i < text.length; i++) {
            stringX = size.getWidth()  / 2 - fm.stringWidth(text[i]) / 2;
            g.drawString(text[i], stringX, (int) (stringY + ((i + .5) - text.length / 2f) * fm.getHeight()));
        }
    }

    public void draw(Graphics g, int x, int y, double percent) {
        draw(g, false);
    }
}
