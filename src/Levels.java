/**
 * Created by oskar on 2016-11-18.
 * This classes has some inputs and outputs
 */
public class Levels {

    public Levels() {

    }

    public void loadLevelMaze(World world) {

        world.setBackground(ImageFunction.loadImage("images/back.png"));

        Player walker = new Walker(world);
        world.setWalker(walker);

        int pad = 50;
        double xx, yy;

        for (int i = 0; i < 50; i ++) {
            do {
                xx = pad + (Math.random() * FrameConstants.WIDTH.value - pad * 2);
                yy = pad + (Math.random() * FrameConstants.HEIGHT.value - pad * 2);
            }
            while (Math.sqrt(Math.pow(xx - walker.getX(), 2) + Math.sqrt(Math.pow(yy - walker.getY(), 2))) < 100);
            world.createBlock(xx, yy);
        }

        world.setGoal(new Goal(400, 400, world.getBlocks()));
    }

}
