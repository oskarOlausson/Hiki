package Normal;
/*
 * Created by oskar on 2016-11-18.
 * Represents a display, can also know which player owns it
 */

import com.phidgets.*;

public class Lcd {
    private TextLCDPhidget screen;
    private PlayerNumber playerNumber;
    private int serial;

    public Lcd(int serial, int screenSize, PlayerNumber player) {

        this.playerNumber = player;
        this.serial = serial;

        try {
            screen = new TextLCDPhidget();
            screen.open(serial);
            System.out.println("Waiting for TextLCDPhidget[" + serial + "]...");
            screen.waitForAttachment();
        } catch (PhidgetException e) {
            System.err.println("No screen found");
            e.printStackTrace();
        }

        try {
            screen.setScreenSize(screenSize);
            screen.setBacklight(true);
            screen.setDisplayString(0, "...");
        } catch (PhidgetException e) {
            System.err.println("Cant set the screen size, backlight or display string");
            e.printStackTrace();
        }
    }

    public void setBacklight(boolean on) {
        try {
            screen.setBacklight(on);
        } catch (PhidgetException e) {
            System.err.println("Can´t turn on backlight on lcd " + serial);
            e.printStackTrace();
        }
    }

    public PlayerNumber getPlayerNumber() {
        return playerNumber;
    }

    public void setString(int index, String string) {
        try {
            screen.setDisplayString(index, string);
        } catch (PhidgetException e) {
            System.err.println("Can´t set display string: '" + string + "'");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Lcd lcd = new Lcd(141799, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20, PlayerNumber.P1);
        lcd.setString(0, "I am number 1");
    }

    public void close() {
        try {
            screen.close();
        } catch (PhidgetException e) {
            System.err.println("Error closing down Lcd: " + serial);
            e.printStackTrace();
        }
        screen = null;
    }
}
