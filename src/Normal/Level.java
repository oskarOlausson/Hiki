package Normal;

import java.awt.*;

/**
 * Created by oskar on 2016-11-18.
 * All levels we create should be a child of this class
 * If you want the level to remain the same when you leave it
 * (like if you walked to the left, changed level and got back the player should still be
 * to the left). Leave end empty and move the start code into the creation of the object
 */
public abstract class Level {

    protected BetweenLevels between;
    private boolean done = false;

    /**
     * constructor
     */
    public Level(BetweenLevels between) {
        this.between = between;
    }

    public Level() {
        between = new NoExplanation();
    }

    /**Called at start of level, create objects in this function
     * @param input**/
    public abstract void start(Input input);

    /**Called at end of level, null objects in this function**/
    public abstract void end();

    /**
     * This function does all the logic behind movement and updates in the level
     *
     */
    public abstract void tick();

    public void tickBetween(Input input) {
        between.tick(input);
    }

    /**
     * This function should draw everything (also destroys objects that should be destroyed due to synchronize reasons)
     * @param g, the graphics we are drawing to
     */
    public abstract void doDrawing(Graphics g);

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public void drawBetween(Graphics g) {
        between.draw(g);
    }

    public boolean hasExplanation() {
        return false;
    }

    public abstract LevelEnum toEnum();
}
