package LevelChoice;

import Normal.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class Event extends Entity{

    private List<Answer> answers = new ArrayList<>();
    private String text = null;

    public Event(String imgPath) {
        this(null, imgPath);
    }

    public Event(String text, String imgPath) {
        if (imgPath == null) imgPath = "other";
        image = ImageFunctions.loadImage("Images/Story/" + imgPath + ".png");
        setSizeFromImage();
        this.text = text;
        x = FrameConstants.WIDTH.value / 2;
        y = FrameConstants.HEIGHT.value / 2;
    }

    public Answer getAnswer(int index) {
        return answers.get(index);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    //variable number of parameters
    public void addAnswers(Answer... answers) {
        int count = 0;
        for (Answer a: answers) {
            this.answers.add(a);
            a.addControls(count, count + 1);
            count++;
        }
    }

    public void draw(Graphics g, World world) {
        world.drawEntity((Graphics2D) g, this);
    }

    public boolean hasAnswers() {
        return (answers != null && !answers.isEmpty());
    }
}
