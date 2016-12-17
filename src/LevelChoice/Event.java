package LevelChoice;

import Normal.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
class Event extends Entity{

    private final boolean upSideDown;
    private List<Answer> answers = new ArrayList<>();
    private Random random = new Random();

    Event(String imgPath) {
        if (imgPath == null) imgPath = "other";
        image = Library.loadImage("Story/" + imgPath);
        setSizeFromImage();
        x = FrameConstants.WIDTH.value / 2;
        y = FrameConstants.HEIGHT.value / 2;
        if (FrameConstants.PROJECTOR.value != 0) {
            upSideDown = random.nextBoolean();
            if (upSideDown) {
                direction = 180;
            }
        }
        else upSideDown = false;
    }

    Answer getAnswer(int index) {
        return answers.get(index);
    }

    List<Answer> getAnswers() {
        return answers;
    }

    //variable number of parameters
    void addAnswers(Answer... answers) {
        Collections.addAll(this.answers, answers);

        this.answers.forEach(a -> a.setUpSideDown(upSideDown));
    }

    public void draw(Graphics g, World world) {
        DrawFunctions.drawImage(g, image, getX(), getY());
    }

    boolean hasAnswers() {
        return (answers != null && !answers.isEmpty());
    }

}
