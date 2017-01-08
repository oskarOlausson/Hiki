package LevelClub;
import Normal.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by oskar on 2016-11-23.
 * This classes has some inputs and outputs
 */
class Clubber extends Player {

    private String name;
    private int index = 0;
    private int aimX = 0;
    private int index2 = 0;
    private boolean locked = false;
    private boolean pressed = false;
    private Font font = new Font("Sans-Serif", Font.PLAIN, 20);
    private Image shadow;
    private Image buttonLocked = Library.loadImage("littleButtonGreen");
    private Image buttonUnlocked = Library.loadImage("littleButtonRed");

    Clubber(String playerImage, double x, double y, String name) {
        super(playerImage);
        this.x = x;
        this.y = y;
        this.name = name;
        shadow = Library.tint((BufferedImage) getImage(), 0, 0, 0, 0.25f);
    }

    void setSensorIndex(int index) {
        this.index = index;
    }
    void setDigitalIndex(int index) {
        this.index2 = index;
    }

    @Override
    public void move() {
        if (isLocked()) return;

        x = 0.8 * x + 0.2 * aimX;
    }

    String getName() {
        return name;
    }


    public void inputs() {
        double slide = firstController().getSliderValue() - 0.5;

        if (firstController().isButtonPressed()) {
            locked = !locked;
        }

        aimX = (int) (FrameConstants.WIDTH.value * 0.5 + slide * 500);
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void draw(Graphics2D g2d) {
        Image button;
        if (isLocked()) {
            button = buttonLocked;
        }
        else button = buttonUnlocked;

        DrawFunctions.drawImage(g2d, button, getX() - button.getWidth(null) / 2, getY() - 100);

        DrawFunctions.drawImage(g2d, shadow, getX(), (int) (getY() + getSize().getHeight() * 0.7), 1, -.5, Math.toRadians(direction));

        DrawFunctions.drawImage(g2d, image, getX(), getY(), 1, 1, Math.toRadians(direction));


    }

    public PlayerController getController() {
        return firstController();
    }
}
