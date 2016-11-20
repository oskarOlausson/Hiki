package Normal;

/**
 * Created by oskar on 2016-11-17.
 */
public enum FrameConstants {
    WIDTH (800),
    HEIGHT (600),
    SECOND (60);

    public final int value;

    FrameConstants(int value) {
        this.value = value;
    }
}
