import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO use mutex in draw and tick
/*
    try {
      mutex.acquire();
      try {
        // do something
      } finally {
        mutex.release();
      }
    } catch(InterruptedException ie) {
      // ...
    }
 */

public class LevelMat implements Level {

    private World world;
    private Player player;
    private Goal goal;
    private List<Block> blocks = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Image background;

    public LevelMat(World world) {
        this.world = world;
        player = new Mat(world);
        background = ImageFunction.loadImage("Images/back.png");
        goal = new Goal(400, 400, blocks);
    }

    public void tick(Input input){
        player.inputs(input.sensorData());
        player.move(blocks);

        goal.move(player);

        enemies.forEach(Enemy::move);
    }

    public void createBlock(double x, double y){
        Block block = new Block(x, y);
        blocks.add(block);
    }

    public void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        world.drawImage(g2d, background, 0, 0);

        world.drawEntity(g2d, goal);

        Iterator<Block> iter = blocks.iterator();

        while (iter.hasNext()) {
            Block block = iter.next();

            if (!block.ifRemove()) {
                world.drawEntity(g2d, block);
            }
            else {
                iter.remove();
            }
        }

        Iterator<Enemy> iter2 = enemies.iterator();

        while (iter2.hasNext()) {
            Enemy enemy = iter2.next();
            if (!enemy.ifRemove()) {
                world.drawEntity(g2d, enemy);
            }
            else {
                iter2.remove();
            }
        }

        player.draw(g2d);
    }


}