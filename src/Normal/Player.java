package Normal;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by oskar on 2016-11-18.
 * This class is a parent class of moving avatars that you control
 * is not needed but is a good foundation of what is needed of a typical player
 */
public abstract class Player extends Entity{

    protected List<PlayerController> playerControllers = new ArrayList<>();
    protected int points = 0;
    private Sorter sorter = new Sorter();

    public Player(String imagePath) {
        if (imagePath != null) {
            image = Library.loadImage(imagePath);
            setSizeFromImage();
        }
        else image = null;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void move(List<Block> blocks) {
        double previousX = x;
        double previousY = y;

        int halfSize = getSize().getHeight() / 2;

        x = Math.max(Math.min(x + dx, FrameConstants.WIDTH.value - halfSize), halfSize);

        boolean collide = false;

        for (Block block : blocks) {
            if (collision(block)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            x = previousX;
        }

        y = Math.max(Math.min(y + dy, FrameConstants.HEIGHT.value - halfSize), halfSize);

        collide = false;

        for (Block block : blocks) {
            if (collision(block)) {
                collide = true;
                break;
            }
        }

        if (collide) {
            y = previousY;
        }
    }

    public void draw(Graphics2D g2d) {
        DrawFunctions.drawImage(g2d, image, getX(), getY(), 1, 1, Math.toRadians(direction));
    }

    public double normalize(int value) {
        return (double) value / 1000;
    }

    public abstract void inputs();

    public void givePoint() {
        points ++;
    }

    public int getPoints() {
        return points;
    }

    public void addController(PlayerController playerController) {
        playerControllers.add(playerController);

        //in player order (0, 1, 2, 3)
        playerControllers.sort(sorter);
    }

    public void addController(PlayerController playerController, String displayText) {
        addController(playerController);
        playerController.setString(displayText);
    }

    public PlayerController firstController() {
        return playerControllers.get(0);
    }

    private class Sorter implements Comparator<PlayerController>  {
        @Override
        public int compare(PlayerController o1, PlayerController o2) {
            return o1.getIndex() -  o2.getIndex();
        }
    }
}
