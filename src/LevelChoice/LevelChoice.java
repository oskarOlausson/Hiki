package LevelChoice;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class LevelChoice extends Level{

    private Story story;
    private World world;
    private Timer timer = new Timer(5);
    private Input input;

    public LevelChoice(World world) {
        this.world = world;
    }

    @Override
    public void start(Input input) {
        this.input = input;
        story = new Story();
    }

    @Override
    public void end() {
        story = null;
    }

    @Override
    public void tick() {
        if (story.update(input)) {
            timer.update();
            if (timer.isDone()) {
                world.nextLevel();
            }
        }
    }

    @Override
    public void doDrawing(Graphics g) {
        story.draw(g, world);
    }

    @Override
    public boolean hasExplanation() {
        return false;
    }

    @Override
    public LevelEnum toEnum() {
        return LevelEnum.CHOICE;
    }

}
