package LevelCodeBreaker;

import Normal.Entity;
import Normal.Library;
import Normal.PlayerController;
import Normal.PlayerNumber;

import java.awt.*;

/**
 * Created by oskar on 2016-11-18.
 * This classes has some inputs and outputs
 */
class Lock extends Entity {

    private int number;
    private Image imgOpen;
    private Image imgClosed;
    private Image imgPick;

    private State state = State.Locked;
    private PlayerController controller;

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

    boolean update() {
        if (state == State.Open) {
            image = imgOpen;
            return true;
        } else {
            if (controller.getSliderValue() > 0.8) {
                state = State.Active;
                image = imgPick;
                return true;
            } else {
                state = State.Active;
                image = imgClosed;
                return false;
            }
        }
    }

    void success() {
        state = State.Open;
    }

    int getNumber() {
        return number;
    }

    public void addController(PlayerController playerController) {
        controller = playerController;
    }

    public PlayerController getController() {
        return controller;
    }

    private enum State {
        Locked, Open, Active
    }
}
