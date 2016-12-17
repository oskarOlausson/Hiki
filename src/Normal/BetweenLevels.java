package Normal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-13.
 * This classes has some inputs and outputs
 */
public abstract class BetweenLevels {

    protected Image image;

    public BetweenLevels(String filePath) {
        ImageRes res = new ImageRes("Betweens/" + filePath);

        if (res == null) {
            res = new ImageRes("Betweens/noExplanation.png");
        }

        image = Library.loadImage(res);
    }

    //returns true when done
    public abstract void tick(Input input);

    public abstract void draw(Graphics g);
}
