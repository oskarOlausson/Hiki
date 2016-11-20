package Normal;

import java.util.List;

/**
 * Created by oskar on 2016-11-17.
 */
public class Goal extends Entity {

    public Goal(double x, double y, List<Block> blocks) {
        this.x = x;
        this.y = y;
        String string = "Images/goal.png";
        image = ImageFunction.loadImage(string);
        setSizeFromImage();
        boolean collide;

        do {
            collide = false;
            for (Block b : blocks) {
                if (collision(b)) {
                    collide = true;
                    move_to_random();
                    break;
                }
            }
        }
        while(collide);
    }

    public void move(Player player) {
        if (collision(player))
        {
            move_to_random();
        }
    }

    private void move_to_random() {
        int pad = 40;
        this.x = pad + Math.random() * (FrameConstants.WIDTH.value - pad * 2);
        this.y = pad + Math.random() * (FrameConstants.HEIGHT.value - pad * 2);
    }
}
