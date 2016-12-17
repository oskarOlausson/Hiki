package Normal;

import java.awt.*;

/**
 * Created by oskar on 2016-12-17.
 * This classes has some inputs and outputs
 */
public class NoExplanation extends BetweenLevels {

    public NoExplanation() {
        super("noExplanation");
    }

    @Override
    public void tick(Input input) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
