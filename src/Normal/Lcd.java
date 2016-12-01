package Normal;
/*
 * Created by oskar on 2016-11-18.
 * Represents a display, can also know which player owns it
 */

import com.phidgets.*;

public class Lcd {
    private TextLCDPhidget screen;
    private int serial;
    private boolean hasScreen;

    public Lcd(int serial, int screenSize) {

        this.serial = serial;
        hasScreen = true;

        try {
            screen = new TextLCDPhidget();
            screen.open(serial);
            System.out.println("Waiting for LCD, serial: " + serial);
            screen.waitForAttachment(20);
        } catch (PhidgetException e) {
            System.err.println("No screen found, serial: " + serial);
            hasScreen = false;
            //e.printStackTrace();
        }

        if (hasScreen) {
            try {
                screen.setScreenSize(screenSize);
                screen.setBacklight(true);
                screen.setDisplayString(0, " ");
                screen.setDisplayString(1, " ");
            } catch (PhidgetException e) {
                System.err.println("Cant set the screen size, backlight or display string");
                e.printStackTrace();
            }

            addSwedishChars();
        }

    }

    public void setBacklight(boolean on) {
        if (!hasScreen) return;
        try {
            screen.setBacklight(on);
        } catch (PhidgetException e) {
            System.err.println("Can´t turn on backlight on lcd " + serial);
            e.printStackTrace();
        }
    }

    public void setString(int index, String string) {

        if (!hasScreen) return;
        //Adds in åäö in a way the LCD can handle
        StringBuilder swedish = new StringBuilder(string);

        for(int i = 0; i < swedish.length(); i++) {
            if (swedish.charAt(i) == 'å') {
                swedish.setCharAt(i, '\010');
            }
            else if (swedish.charAt(i) == 'ä') {
                swedish.setCharAt(i, '\011');
            }
            else if (swedish.charAt(i) == 'ö') {
                swedish.setCharAt(i, '\012');
            }
        }

        string = swedish.toString();

        int len = 15;
        int splitIndex;

        try {
            if (string.length() <= len) {
                screen.setDisplayString(index, string);
            }
            else {
                splitIndex = len - 1;

                for (int i = 0; i < 5; i++) {
                    if (string.charAt(splitIndex - i) == ' ') {
                        splitIndex = splitIndex - i + 1;
                        break;
                    }
                }

                screen.setDisplayString(index, string.substring(0, splitIndex) + " ");
                screen.setDisplayString(index + 1, string.substring(splitIndex));
            }
        } catch (PhidgetException e) {
            System.err.println("Can´t set display string: '" + string + "'");
            e.printStackTrace();
        }

    }

    public void addSwedishChars() {
        try {
            //å
            screen.setCustomCharacter(8,  0xB804, 0x3E2F);
            //ä
            screen.setCustomCharacter(9,  0xB80A, 0x3E2F);
            //ö
            screen.setCustomCharacter(10, 0x8B80A, 0x3A31);
        } catch (PhidgetException e) {
            System.err.println("could not add swedish characters");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Lcd lcd = new Lcd(141568, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20);
    }

    public void close() {
        if (!hasScreen) return;
        try {
            screen.close();
        } catch (PhidgetException e) {
            System.err.println("Error closing down Lcd: " + serial);
            e.printStackTrace();
        }
        screen = null;
    }
}
