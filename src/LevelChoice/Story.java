package LevelChoice;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Normal.*;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
class Story {

    private List<Event> mainStory;
    private List<Event> endings;
    private Event lastEvent = null;
    private boolean answerMode = false;
    private int progression = 0;
    private int choice = 0;
    private int[] debugChoice = {0, 2, 0};
    private int points = 0;
    private Timer timer = new Timer(5);
    private Timer debugTimer = new Timer(1);
    private boolean done = false;
    private Position answerPosition = new Position(FrameConstants.WIDTH.value - 360, FrameConstants.HEIGHT.value * 0.4);
    private Timer debugTimer2 = new Timer(1);
    private boolean upSideDown = false;

    Story() {
        mainStory = new ArrayList<>();
        endings = getEndings();
        loadGalaStory();
    }

    private void storyAdd(String s) {
        mainStory.add(lastEvent = new Event(s));
    }

    private void loadGalaStory() {
        storyAdd("storyRoot");
        storyAdd("storyDrink");
        lastEvent.addAnswers(new Answer("Tack gärna", "drinkYes", 0, 1), new Answer("Nej tack", "drinkNo", 1, 2), new Answer("Dra åt helvete", "drinkDickMove", 2, 3));
        storyAdd("storyDate");
        lastEvent.addAnswers(new Answer("Dra ut hennes stol\noch sätt dig sedan", "dateYes", 0, 1), new Answer("Sätt dig ner", "dateNo", 1, 2), new Answer("Muttra något om att du \nvill ha en snyggare dejt", "dateDickMove", 2, 3));
        storyAdd("storyCeleb");
        lastEvent.addAnswers(new Answer("Gå fram till Alicia direkt", "other", 0, 1), new Answer("Ställ dig i Alicias sällskap\noch skratta högt åt\nhennes skämt", "other", 1, 2), new Answer("Gå upp på scenen\n och kräv att Alicia ska\nprata med dig", "other", 2, 3));
    }

    /**
     * @param input, the sensor and digital input
     * @return: done, if its finished with the story
     */
    public boolean update(Input input) {
        if (!done) {

            if (answerMode) {
                timer.update();
                if (timer.isDone()) {
                    timer.restart();
                    answerMode = false;
                    if (progression < mainStory.size() - 1) progression++;
                }
            } else {
                boolean[] arr = input.digitalData().clone();

                //artificial choice
                if (mainStory.get(progression).hasAnswers()) {
                    debugTimer.update();
                    if (debugTimer.isDone()) {
                        arr[debugChoice[progression - 1]] = true;
                        debugTimer2.update();
                        if (debugTimer2.isDone()) {
                            int index = debugChoice[progression - 1] + 1;
                            arr[index] = true;
                        }
                    }
                }

                Answer a;
                int answerIndex = -1;

                for (int i = 0; i < mainStory.get(progression).getAnswers().size(); i++) {
                    a = mainStory.get(progression).getAnswer(i);
                    a.update(arr);
                    if (a.isDone()) {
                        answerIndex = i;
                        break;
                    }
                }

                if (!mainStory.get(progression).hasAnswers()) {
                    progression++;
                }

                if (answerIndex != -1) {
                    if (mainStory.get(progression).hasAnswers()) answerMode = true;
                    debugTimer.restart();
                    debugTimer2.restart();

                    if (mainStory.get(progression).equals(lastEvent)) done = true;

                    if (mainStory.get(progression).hasAnswers()) makeChoice(answerIndex);
                    else progression++;
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
            points += (2 - index) * 3;
        }
        else if (progression == 3) {
            points += (2 - index);
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

    private void drawOptions(Graphics g) {
        Event e = mainStory.get(progression);
        int count = 0;
        for (Answer a: e.getAnswers()) {
            a.drawTextBox(g, answerPosition.drawX(), answerPosition.drawY() + count * (a.getTextBoxHeight() + 5));
            count++;
        }
    }

    private List<Event> getEndings() {
        List<Event> endings = new ArrayList<>();
        for (int i = 0; i <= 17; i++) {
            endings.add(new Event("storyCeleb" + Integer.toString(i)));
        }
        return endings;
    }
}
