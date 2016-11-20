package Codebreaker;

import Normal.Entity;
import Normal.ImageFunction;
import Normal.PlayerNumber;

import java.awt.*;

/**
 * Created by oskar on 2016-11-18.
 * This classes has some inputs and outputs
 */
public class Lock extends Entity {

    private PlayerNumber playerNumber;
    private int interaction = -1;
    private int number;
    private Image imgOpen;
    private Image imgClosed;
    private Image imgPick;
    private int state = 0;


    public Lock(int x, int y) {
        this.x = x;
        this.y = y;
        number = (int) (Math.random() * 100);

        imgOpen = ImageFunction.loadImage("Images/lock_opened.png");
        imgClosed = ImageFunction.loadImage("Images/lock.png");
        imgPick = ImageFunction.loadImage("Images/lock_pick.png");
        image = imgClosed;
        setSizeFromImage();
    }

    public boolean update(int[] input) {
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

    public void success() {
        state = 2;
    }

    public void setPlayer(PlayerNumber playerNumber) {
        this.playerNumber = playerNumber;
    }

    public PlayerNumber getPlayer() {
        return playerNumber;
    }

    public int getInteraction() {
        return interaction;
    }

    public void setInteraction(int interaction) {
        this.interaction = interaction;
    }

    public int getNumber() {
        return number;
    }
}
