import java.awt.*;

/**
 * Created by oskar on 2016-11-18.
 * This classes has some inputs and outputs
 */
public interface Level {

    void tick(Input input);
    void doDrawing(Graphics g);

}
