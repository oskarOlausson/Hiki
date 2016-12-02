package LevelChoice;

import Normal.Entity;
import Normal.ImageFunctions;
import Normal.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class Event extends Entity{

    Event parent;
    List<Event> answers = new ArrayList<>();
    Event child = null;

    public Event(Event parent, String imgPath) {
        image = ImageFunctions.loadImage("Images/story" + imgPath + ".png");
        setSizeFromImage();
        this.parent = parent;
    }

    public Event getParent() {
        return parent;
    }

    public boolean hasChild() {
        return child != null;
    }

    public Event getChild() {
        return child;
    }

    public Event getAnswer(int index) {
        return answers.get(index);
    }

    //variable number of parameters
    public void addAnswers(Event... events) {
        for (Event e: events) {
            answers.add(e);
        }
    }

    //variable number of parameters
    public Event setChild(Event child) {
        this.child = child;
        return this.child;
    }

    public void draw(Graphics g, World world) {
        world.drawEntity((Graphics2D) g, this);
    }
}
