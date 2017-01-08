package LevelMat;

import Normal.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class LevelMat extends Level {

    private World world;
    private Mat player;
    private Goal goal;
    private List<Block> blocks = new ArrayList<>();
    private Image background;
    private Timer timer;
    private WinEffect winEffect;
    private Comparator<Block> comparator = new Comparator<Block>() {
        @Override
        public int compare(Block o1, Block o2) {
            return o1.getY() - o2.getY();
        }
    };

    public LevelMat(World world) {
        this.world = world;
        background = Library.loadImage("back");
    }

    @Override
    public void start(Input input) {
        player = new Mat(FrameConstants.WIDTH.value / 2, FrameConstants.HEIGHT.value / 2);
        input.getControllers().forEach(c -> player.addController(c));

        Position center = player.getCenter();
        Position pos;
        for (int i = 0; i < 90; i ++) {
            do {
                //create random position
                pos = new Position();
                pos.randomize(0, 0, FrameConstants.WIDTH.value, FrameConstants.HEIGHT.value);
                pos.snap(32, 32);
            }
            while(center.distance(pos) < 50);
            createBlock(pos.getX(), pos.getY());
        }

        blocks.sort(comparator);
        goal = new Goal(400, 400, blocks);
        timer = new Timer(1);
        winEffect = new WinEffect();
    }

    @Override
    public void end() {
        player = null;
        goal = null;
        blocks = new ArrayList<>();
        timer = null;
        winEffect = null;
    }

    public void tick(){
        player.inputs();
        player.move(blocks);
        goal.move(player);

        if (player.getPoints() > 0) {
            if (!winEffect.isStarted()) winEffect.start();
            winEffect.tick();
            timer.update();
            if (timer.isDone()) {
                world.nextLevel();
            }
        }
    }

    private void createBlock(double x, double y){
        Block block = new Block(x, y);
        blocks.add(block);
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        DrawFunctions.drawImage(g, background,  0, 0);
        DrawFunctions.drawImage(g, goal.getImage(),  goal.getX(), goal.getY());

        Iterator<Block> iter = blocks.iterator();

        while (iter.hasNext()) {
            Block block = iter.next();

            if (!block.ifRemove()) {
                block.draw(g);
            }
            else {
                iter.remove();
            }
        }
        player.draw(g2d);

        if (winEffect != null) {
            if (winEffect.isStarted()) winEffect.draw(g);
        }
    }

    @Override
    public LevelEnum toEnum() {
        return LevelEnum.MAT;
    }
}