package LevelColor;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-12-01.
 * This class has some inputs and outputs
 */
class ColorParticle extends Entity {

    @SuppressWarnings("FieldCanBeLocal")
    private double minSpeed = 3;
    @SuppressWarnings("FieldCanBeLocal")
    private double maxSpeed = 6;
    private double speed;
    private int life;

    ColorParticle(double x, double y, Color color) {
        init(x, y, Math.random() * Math.PI * 2, minSpeed + Math.random() * (maxSpeed - minSpeed), color);
    }

    void init(double x, double y, double radians, double speed, Color color) {
        this.x = x;
        this.y = y;
        this.direction = radians;
        this.speed = speed;
        this.life = (int) ((.5 + Math.random() * 0.75) * FrameConstants.SECOND.value);

        image = Library.loadImage("particle", color);
        setSizeFromImage();
    }

    void move() {
        x += Math.cos(direction) * speed;
        y += Math.sin(direction) * speed;
        speed = Math.max(0, speed - 0.025);
        if (speed == 0) delete();

        direction += 2 * Math.PI / 180;
        life -= 1;
        if (life <= 0) {
            delete();
        }
    }

    void draw(Graphics g) {
        DrawFunctions.drawImage(g, image, (int) x, (int) y);
    }
}
