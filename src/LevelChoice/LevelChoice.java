package LevelChoice;

import Normal.Input;
import Normal.Level;

import java.awt.*;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class LevelChoice implements Level{

    private Mood guardMood;
    private Mood  dateMood;

    @Override
    public void start() {
        guardMood = Mood.NEUTRAL;
        dateMood = Mood.NEUTRAL;
    }

    @Override
    public void end() {

    }

    @Override
    public void tick(Input input) {

    }

    @Override
    public void doDrawing(Graphics g) {

    }
}
