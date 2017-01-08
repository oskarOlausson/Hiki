package Normal;

import com.phidgets.TextLCDPhidget;

/**
 * Created by oskar on 2016-12-16.
 * This classes has some inputs and outputs
 */
public enum ScreenNumbers {
    ONE (141787),
    TWO (141799),
    THREE (141627),
    FOUR (141568);

    public final int value;

    ScreenNumbers(int value) {
        this.value = value;
    }

    int getSize() {
        switch(value) {
            case 141627: return TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_2x20;
            case 141568: return TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_2x20;
            case 141799: return TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20;
            case 141787: return TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20;
            default: return -1;
        }
    }
}
