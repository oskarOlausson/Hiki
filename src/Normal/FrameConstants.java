package Normal;

/**
 * Created by oskar on 2016-11-17.
 * Has the width and height of the frame,
 * (Seconds is how many frames per second we are running)
 */
public enum FrameConstants {
    WIDTH (800),
    HEIGHT (600),
    SECOND (60),
    PROJECTOR (0);

    public final int value;

    FrameConstants(int value) {
        this.value = value;
    }
}
