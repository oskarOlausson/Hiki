package LevelChoice;

import Normal.Entity;
import Normal.FrameConstants;
import Normal.ImageFunctions;
import Normal.World;

import java.awt.*;

/**
 * Created by oskar on 2016-12-03.
 * This classes has some inputs and outputs
 */
public class Answer extends Entity {

    private TextBox textBox;

    public Answer(String text, String imgPath) {
        if (imgPath == null) imgPath = "other";
        image = ImageFunctions.loadImage("Images/Story/" + imgPath + ".png");
        setSizeFromImage();
        this.textBox = new TextBox(text);
        x = FrameConstants.WIDTH.value / 2;
        y = FrameConstants.HEIGHT.value / 2;
    }

    public void drawTextBox(Graphics g, int x, int y) {
        textBox.draw(g, x, y);
    }

    public void draw(Graphics g, World world) {
        world.drawEntity((Graphics2D) g, this);
    }

    public int getTextBoxHeight() {
        return (int) textBox.getHeight();
    }
}
