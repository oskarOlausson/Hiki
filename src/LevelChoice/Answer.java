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
    private Timer nextTimer = new Timer(2);
    private boolean upSideDown;

    Answer(String text, String imgPath, int control1, int control2) {
        this.control1 = control1;
        this.control2 = control2;
        if (imgPath == null) imgPath = "other";
        image = Library.loadImage("Story/" + imgPath + ".png");
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
            System.out.println("hi");
        }
        else {
            nextTimer.restart();
            maybe = false;
            chosen = false;
        }
        textBox.update(maybe, chosen);
    }

    void drawTextBox(Graphics g, int x, int y) {
        double percent = nextTimer.getProgress();
        if (textBox.isChosen()) textBox.draw(g, x, y, percent);
        else textBox.draw(g, x, y, 0);
    }

    public void draw(Graphics g, World world) {
        DrawFunctions.drawImage(g, image, getX(), getY());
    }

    int getTextBoxHeight() {
        return textBox.getHeight();
    }

    boolean isDone() {
        return next;
    }

    public void setUpSideDown(boolean upSideDown) {
        this.upSideDown = upSideDown;

        if (upSideDown) {
            direction = 180;
        }
        else direction = 0;
        textBox.setUpSideDown(upSideDown);
    }
}
