package LevelCodeBreaker;

import Normal.Entity;
import Normal.Library;
import Normal.PlayerNumber;

import java.awt.*;

/**
 * Created by oskar on 2016-11-18.
 * This classes has some inputs and outputs
 */
class Lock extends Entity {

    private PlayerNumber playerNumber;
    private int interaction = -1;
    private int number;
    private Image imgOpen;
    private Image imgClosed;
    private Image imgPick;

    private int state = 0;

     Lock(int x, int y) {
         this.x = x;
         this.y = y;
         number = (int) (Math.random() * 100);
         imgOpen = Library.loadImage("lock_opened");
         imgClosed = Library.loadImage("lock");
         imgPick = Library.loadImage("lock_pick");
         image = imgClosed;
         setSizeFromImage();
    }

    boolean update(int[] input) {
        int in = input[interaction];

        if (state == 2) {
            image = imgOpen;
            return true;
        } else {
            if (in > 800) {
                state = 1;
                image = imgPick;
                return true;
            } else {
                state = 0;
                image = imgClosed;
                return false;
            }
        }
    }

    void success() {
        state = 2;
    }

    void setPlayer(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
    }

    PlayerNumber getPlayer() {
        return playerNumber;
    }

    int getInteraction() {
        return interaction;
    }

    void setInteraction(int interaction) {
        this.interaction = interaction;
    }

    int getNumber() {
        return number;
    }
}
