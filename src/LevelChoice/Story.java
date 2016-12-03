package LevelChoice;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Normal.*;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class Story {

    private List<Event> mainStory;
    private List<Event> endings;
    private Event lastEvent = null;
    private boolean answerMode = false;
    private int progression = 0;
    private int choice = 0;
    private int[] debugChoice = {0, 1, 2};
    private int points = 0;
    private Timer timer = new Timer(FrameConstants.SECOND.value * 8);
    private boolean done = false;
    private Position answerPosition = new Position(FrameConstants.WIDTH.value - 310, FrameConstants.HEIGHT.value * 0.5);

    public Story() {
        mainStory = new ArrayList<>();
        endings = getEndings();
        loadGalaStory();
    }

    public void storyAdd(String s) {
        mainStory.add(lastEvent = new Event(s));
    }

    private void loadGalaStory() {
        storyAdd("storyRoot");
        storyAdd("storyDrink");
        lastEvent.addAnswers(new Answer("yes", "drinkYes"), new Answer("no", "drinkNo"), new Answer("dick move", "drinkDickMove"));
        storyAdd("storyDate");
        lastEvent.addAnswers(new Answer("yes", "dateYes"), new Answer("no", "dateNo"), new Answer("dick move", "dateDickMove"));
        storyAdd("storyCeleb");
        lastEvent.addAnswers(new Answer("yes", "other"), new Answer("no", "other"), new Answer("dick move", "other"));
    }

    /**
     * @param input, the sensor and digital input
     * @return: if its finished with the story
     */
    public boolean update(Input input) {
        if (!done) {
            timer.update();
            if (answerMode) {
                if (timer.isDone()) {
                    timer.restart();
                    answerMode = false;
                    if (progression < mainStory.size() - 1) progression++;
                }
            } else {
                if (timer.isDone()) {
                    timer.restart();
                    answerMode = true;

                    if (mainStory.get(progression).equals(lastEvent)) done = true;
                    else if (mainStory.get(progression).hasAnswers()) makeChoice(debugChoice[progression - 1]);
                    else timer.ring();
                }
            }
        }

        return done;
    }

    private void makeChoice(int index) {
        if (progression == 1) {
            if (index != 2) points += 9;
        }
        else if (progression == 2) {
            points += index * 3;
        }
        else if (progression == 3) {
            points += index;
        }

        answerMode = true;
        choice = index;
    }

    public void draw(Graphics g, World world) {
        if (!done) {
            if (answerMode) {
                if (mainStory.get(progression).hasAnswers()) {
                    mainStory.get(progression).getAnswer(choice).draw(g, world);
                }
            } else {
                mainStory.get(progression).draw(g, world);
                drawOptions(g);
            }
        }
        else {
            endings.get(points).draw(g, world);
        }
    }

    public void drawOptions(Graphics g) {
        Event e = mainStory.get(progression);
        int count = 0;
        for (Answer a: e.getAnswers()) {
            a.drawTextBox(g, answerPosition.drawX(), answerPosition.drawY() + count * (a.getTextBoxHeight() + 5));
            count++;
        }
    }

    public List<Event> getEndings() {
        List<Event> endings = new ArrayList<>();
        for (int i = 0; i <= 17; i++) {
            endings.add(new Event("storyCeleb" + Integer.toString(i)));
        }
        return endings;
    }
}
