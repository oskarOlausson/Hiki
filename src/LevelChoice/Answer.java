package LevelChoice;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-12-03.
 * This classes has some inputs and outputs
 */
class Answer extends Entity {

    private TextBox textBox;
    private int control1 = 0;
    private int control2 = 1;
    private boolean chosen = false;
    private boolean maybe = false;
    private boolean next = false;
    private Timer nextTimer = new Timer(FrameConstants.SECOND.value * 2);

    Answer(String text, String imgPath, int control1, int control2) {
        this.control1 = control1;
        this.control2 = control2;
        if (imgPath == null) imgPath = "other";
        image = ImageFunctions.loadImage("Images/Story/" + imgPath + ".png");
        setSizeFromImage();
        this.textBox = new TextBox(text, control1, control2);
        x = FrameConstants.WIDTH.value / 2;
        y = FrameConstants.HEIGHT.value / 2;
    }

    void update(boolean[] pressed) {
        if (pressed[control1] ^ pressed[control2]) {
            maybe = true;
            nextTimer.restart();
        }
        else if (pressed[control1] && pressed[control2]) {
            chosen = true;
            nextTimer.update();
            if (nextTimer.isDone()) {
                next = true;
            }
        }
        else {
            nextTimer.restart();
        }
        textBox.update(maybe, chosen);
    }

    void drawTextBox(Graphics g, int x, int y) {
        double percent = nextTimer.getPercent();
        if (textBox.isChosen()) textBox.draw(g, x, y, percent);
        else textBox.draw(g, x, y, 0);
    }

    public void draw(Graphics g, World world) {
        world.drawEntity((Graphics2D) g, this);
    }

    int getTextBoxHeight() {
        return textBox.getHeight();
    }

    boolean isDone() {
        return next;
    }
}
