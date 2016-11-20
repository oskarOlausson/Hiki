package Normal;

/**
 * Created by oskar on 2016-11-17.
 */
public class InputConstants {
    public static final int JOYX = 0;
    public static final int JOYY = 1;
    public static final int DIAL = 2;
    public static final int LIGHT = 3;
    public static final int PREASSURE = 4;
    public static final int SLIDER = 6;

    //DIGITAL
    public static final int SWITCH = 0;

    public static String toString(int input) {
        switch(input) {
            case JOYX:      return "JOYX";
            case JOYY:      return "JOYY";
            case DIAL:      return "DIAL";
            case LIGHT:     return "LIGHT";
            case PREASSURE: return "PRESSURE";
            case SLIDER:    return "SLIDER";

            default: return "Unknown";
        }
    }


}
