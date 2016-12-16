package LevelColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
class Colors {

    private final Color r = new Color(180, 40, 40);
    private final Color y = new Color(220, 220, 20);
    private final Color b = new Color(30, 70, 180);
    private final Color[] PRIMARY = {r, y, b};

    private final Color o = new Color(200, 110, 30);
    private final Color g = new Color(0, 120, 0);
    private final Color l = new Color(170, 20, 130);

    private final Color textColor = new Color(130, 130, 130);

    private final Color[] SECONDARY = {o, g, l};

    Color getTextColor() {
        return textColor;
    }

    Color primaryGet(int index) {
        return PRIMARY[index];
    }

    private Color secondaryGet(int index) {
        return SECONDARY[index];
    }

    Color secondaryGet(int index, int index2) {
        if (index == index2) {
            return primaryGet(index);
        }

        int i = Math.min(index, index2);
        int j = Math.max(index, index2);

        if (i == 0) {
            if (j == 1) return secondaryGet(0); //orange
            else if (j == 2) return secondaryGet(2); //purple
        }
        else if (i == 1) {
            if (j == 2) return secondaryGet(1); //green
        }

        System.err.println("Unreachable state i thought, index: " + index + ", index2: " +  index2);
        return null;
    }
}
