package LevelChoice;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class LevelChoice implements Level{

    private Story story;
    private World world;
    private Timer timer = new Timer(5);

    public LevelChoice(World world) {
        this.world = world;
    }

    @Override
    public void start() {
        story = new Story();
    }

    @Override
    public void end() {
        story = null;
    }

    @Override
    public void tick(Input input) {
        if (story.update(input)) {
            /*
            timer.update();
            if (timer.isDone()) {
                world.nextLevel();
            }
            */
        }
    }

    @Override
    public void doDrawing(Graphics g) {
        story.draw(g, world);
    }
}
