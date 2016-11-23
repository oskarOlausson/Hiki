package LevelMat;

import Normal.*;
import Normal.Frame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LevelMat implements Level {

    private World world;
    private Player player;
    private Goal goal;
    private List<Block> blocks = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Image background;
    private Timer timer;

    public LevelMat(World world) {
        this.world = world;
        background = ImageFunction.loadImage("Images/back.png");
        start();
    }

    @Override
    public void start() {

        player = new Mat(world, FrameConstants.WIDTH.value / 2, FrameConstants.HEIGHT.value / 2);
        Position center = player.getCenter();
        Position pos;
        for (int i = 0; i < 40; i ++) {
            do {
                //create random position
                pos = new Position();
                pos.randomize(0, 0, FrameConstants.WIDTH.value, FrameConstants.HEIGHT.value);
            }
            while(center.distance(pos) < 50);
            createBlock(pos.getX(), pos.getY());
        }
        goal = new Goal(400, 400, blocks);
        timer = new Timer(FrameConstants.SECOND.value);
    }

    @Override
    public void end() {
        player = null;
        goal = null;
        blocks = new ArrayList<>();
        timer = null;
    }

    public void tick(Input input){
        player.inputs(input.sensorData(), input.digitalData());
        player.move(blocks);

        goal.move(player);

        enemies.forEach(Enemy::move);

        if (player.getPoints() > 0) {
            timer.update();

            if (timer.isDone()) {
                world.nextLevel();
            }
        }
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