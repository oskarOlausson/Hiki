package Normal;

/**
 * Created by oskar on 2016-11-17.
 */
public class InputConstants {
    public static final int P1_SLIDE = 1;
    public static final int P2_SLIDE = 5;
    public static final int P3_SLIDE = 4;
    public static final int P4_SLIDE = 7;



    public static final int BUTTON = 4;

    public static String sensorToString(int input) {
        switch(input) {
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
