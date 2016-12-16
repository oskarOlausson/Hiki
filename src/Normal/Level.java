package Normal;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by oskar on 2016-11-18.
 * All levels we create should be a child of this class
 * If you want the level to remain the same when you leave it
 * (like if you walked to the left, changed level and got back the player should still be
 * to the left). Leave end empty and move the start code into the creation of the object
 */
public abstract class Level {

    protected BufferedImage betweenImage;

    /**
     * constructor
     */
    public Level(String path) {

        ImageRes res = new ImageRes("Betweens/" + path);

        if (res == null) {
            res = new ImageRes("Betweens/noExplanation.png");
        }

        betweenImage = Library.loadImage(res);
    }

    public Level() {
        ImageRes res = new ImageRes("Betweens/noExplanation.png");
        betweenImage = Library.loadImage(res);
    }

    /**Called at start of level, create objects in this function**/
    public abstract void start();

    /**Called at end of level, null objects in this function**/
    public abstract void end();

    /**
     * This function does all the logic behind movement and updates in the level
     * @param input, this is the way the world class sends what phidgets controls are where
     *
     */
    public abstract void tick(Input input);

    /**
     * This function should draw everything (also destroys objects that should be destroyed due to synchronize reasons)
     * @param g, the graphics we are drawing to
     */
    public abstract void doDrawing(Graphics g);

    public void doDrawing(Graphics g, GameState gameState) {
        if (gameState.equals(GameState.BETWEEN)) {
            drawBetween(g);
        }
        else doDrawing(g);
    }


    public void drawBetween(Graphics g) {
        g.drawImage(betweenImage, 0, 0, null);
    }
}
