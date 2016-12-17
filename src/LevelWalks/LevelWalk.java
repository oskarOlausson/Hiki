package LevelWalks;

import Normal.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LevelWalk extends Level {
    private World world;
    private Player player;
    private Goal goal;
    private List<Block> blocks = new ArrayList<>();
    private Image background;

    public LevelWalk(World world) {
        this.world = world;
        background = Library.loadImage("back");
    }

    @Override
    public void start() {
        player = new Walker(FrameConstants.WIDTH.value / 2, FrameConstants.HEIGHT.value / 2);

        Position center = player.getCenter();
        Position pos;
        for (int i = 0; i < 50; i ++) {
            do {
                //create random position
                pos = new Position();
                pos.randomize(0, 0, FrameConstants.WIDTH.value, FrameConstants.HEIGHT.value);
            }
            while(center.distance(pos) < 50);
            createBlock(pos.getX(), pos.getY());
        }

        goal = new Goal(400, 400, blocks);
    }

    @Override
    public void end() {
        player = null;
        goal = null;
    }

    public void tick(Input input){
        player.inputs(input.sensorData(), input.digitalData());
        player.move(blocks);

        goal.move(player);
    }

    public void createBlock(double x, double y){
        Block block = new Block(x, y);
        blocks.add(block);
    }

    public void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        DrawFunctions.drawImage(g, background,  0, 0);

        DrawFunctions.drawImage(g, goal.getImage(), goal.getX(), goal.getY());


        Iterator<Block> iter = blocks.iterator();

        while (iter.hasNext()) {
            Block block = iter.next();

            if (!block.ifRemove()) {
                DrawFunctions.drawImage(g, block.getImage(), block.getX(), block.getY());
            }
            else {
                iter.remove();
            }
        }

        player.draw(g2d);
    }


}