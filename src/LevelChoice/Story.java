package LevelChoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-02.
 * This classes has some inputs and outputs
 */
public class Story {

    private Event root;
    private Event currentNode;
    private List<Event> mainStory;

    public Story() {
        mainStory = new ArrayList<>();
        loadGalaStory();
    }

    private void loadGalaStory() {
        root = new Event(null, "storyRoot");
        currentNode = root;

        Event main;
        main = root.setChild(new Event(root, "storyDrink"));
        main.addAnswers(new Event(main, "storyYes"), new Event(main, "storyNah"), new Event(main, "storyHellNo"));

        main = main.setChild(new Event(main, "storyDate"));
        main.addAnswers(new Event(main, "storySit"), new Event(main, "storyKind"), new Event(main, "storyRude"));

        main = main.setChild(new Event(main, "storyCeleb");
        main.addAnswers(new Event(main, "storyTalk"), new Event(main, "storyGroup"), new Event(main, "storyScene"));

        main = main.setChild(new Event(main, "storyEnd"));
    }
}
