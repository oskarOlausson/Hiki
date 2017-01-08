package Normal;

/**
 * Created by oskar on 2016-12-27.
 * This classes has some inputs and outputs
 */
public enum LevelEnum {

    COLOR(true), CHOICE(false), CLUB(false), CODE(false), MAT(true);

    public final boolean isAction;

    LevelEnum(boolean isAction) {
        this.isAction = isAction;
    }


    public static LevelEnum fromString(String word) {
        word = word.toUpperCase();
        switch(word) {
            case "COLOR":
                return COLOR;
            case "CHOICE":
                return CHOICE;
            case "CLUB":
                return CLUB;
            case "CODE":
                return CODE;
            case "MAT":
                return MAT;

            default:
                return null;
        }
    }
}
