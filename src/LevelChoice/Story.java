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
    private int points = 0;
    private Timer timer = new Timer(5);
    private boolean done = false;
    private Position answerPosition = new Position(FrameConstants.WIDTH.value - 285, FrameConstants.HEIGHT.value * 0.4);

    Story() {
        mainStory = new ArrayList<>();
        endings = getEndings();
        loadGalaStory();
    }

    private void storyAdd(String s) {
        mainStory.add(lastEvent = new Event(s));
    }

    private void loadGalaStory() {
        storyAdd("step1");
        storyAdd("step2");
        lastEvent.addAnswers(new Answer("Tack gärna", "step2Answer1", 0, 1), new Answer("Nej tack", "step2Answer2", 1, 2), new Answer("Dra åt helvete", "step2Answer3", 2, 3));
        storyAdd("step3");
        lastEvent.addAnswers(new Answer("Sätt dig ner", "step3Answer1", 0, 1), new Answer("Dra ut hennes stol\noch sätt dig sedan", "step3Answer2", 1, 2), new Answer("Muttra något om att du \nvill ha en snyggare dejt", "step3Answer3", 2, 3));
        storyAdd("step4");
        lastEvent.addAnswers(new Answer("Gå fram till Persbrant direkt", "other", 0, 1), new Answer("Ställ dig i Persbrants sällskap\noch skratta högt åt\nhennes skämt", "other", 1, 2), new Answer("Gå upp på scenen\n och kräv att Persbrant ska\nprata med dig", "other", 2, 3));
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

                Answer a;
                int answerIndex = -1;

                for (int i = 0; i < mainStory.get(progression).getAnswers().size(); i++) {
                    a = mainStory.get(progression).getAnswer(i);
                    a.update(input.digitalData());
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
            points += (2 - index) * 9;
        }
        else if (progression == 2) {
            points += (2 - index) * 3;
        }
        else if (progression == 3) {
            points += (2 - index); // * 1
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
        String s = "step4Answer";

        //0-2
        endings.add(new Event(s + "2.1.2"));
        endings.add(new Event(s + "1.2.3"));
        endings.add(new Event(s + "2.1.2"));
        //3-5
        endings.add(new Event(s + "2.1.2"));
        endings.add(new Event(s + "1.2.3"));
        endings.add(new Event(s + "2.1.2"));
        //6-8
        endings.add(new Event(s + "1.2"));
        endings.add(new Event(s + "1.3.2"));
        endings.add(new Event(s + "1.2"));
        //9-11
        endings.add(new Event(s + "2.1.2"));
        endings.add(new Event(s + "NOT MADE YET"));
        endings.add(new Event(s + "2.1.2"));
        //12-14
        endings.add(new Event(s + "2.1.2"));
        endings.add(new Event(s + "1.2.3"));
        endings.add(new Event(s + "2.1.2"));
        //15-17
        endings.add(new Event(s + "3.4"));
        endings.add(new Event(s + "2.3.3"));
        endings.add(new Event(s + "3.4"));
        //18-20
        endings.add(new Event(s + "NOT MADE YET"));
        endings.add(new Event(s + "NOT MADE YET"));
        endings.add(new Event(s + "NOT MADE YET"));
        //21-23
        endings.add(new Event(s + "2.1.2"));
        endings.add(new Event(s + "2.2"));
        endings.add(new Event(s + "2.1.2"));
        //24-26
        endings.add(new Event(s + "3.4"));
        endings.add(new Event(s + "2.2"));
        endings.add(new Event(s + "3.4"));

        return endings;
    }
}
