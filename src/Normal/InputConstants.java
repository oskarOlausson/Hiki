package Normal;

/**
 * Created by oskar on 2016-11-17.
 * Makes it easier when plugging in he controllers
 */
public class InputConstants {
    public static final int P1_SLIDE = 0;
    public static final int P2_SLIDE = 1;
    public static final int P3_SLIDE = 2;
    public static final int P4_SLIDE = 3;

    public static final int BUTTON = 4;

    public static String sensorToString(int input) {
        switch(input) {
            case P1_SLIDE: case P2_SLIDE: case P3_SLIDE: case P4_SLIDE: return "SLIDE";
            default: return "Unknown";
        }
    }

    public static String digitalToString(int input) {
        switch(input) {
            case BUTTON:      return "BUTTON";

            default: return "Unknown";
        }
    }


}
