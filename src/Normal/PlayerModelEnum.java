package Normal;

/**
 * Created by oskar on 2016-12-27.
 * This classes has some inputs and outputs
 */
public enum PlayerModelEnum {
    TALKATIVE, COOPERATIVE, ACTION, PUZZLE;

    public static PlayerModelEnum fromString(String word) {
        word = word.toUpperCase();
        switch(word) {
            case "TALKATIVE":
                return TALKATIVE;
            case "COOPERATIVE":
                return COOPERATIVE;
            case "ACTION":
                return ACTION;
            case "PUZZLE":
                return PUZZLE;
            default:
                return null;
        }
    }
}
