import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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

public class World extends JPanel{

    private GameState state = GameState.PLAY;
    private Input input;
    private boolean running = false;

    private Level level;

    public World(Input input) {
        this.input = input;
        setFocusable(true);
        setBackground(Color.BLACK);
        level = new CodeBreaker(this);
    }

    public synchronized void run(){
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / FrameConstants.SECOND.value;

        int ticks = 0;
        int frames = 0;

        long secondTimer = System.currentTimeMillis();
        double delta = 0;
        long now;
        running = true;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while(delta >=1){
                ticks ++;
                tick();
                delta -= 1;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            frames += 1;
            repaint();

            if (System.currentTimeMillis() - secondTimer > 1000){
                secondTimer += 1000;
                //System.out.println("frames: "+ frames +", ticks: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick(){
        level.tick(input);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        level.doDrawing(g);
        //Toolkit.getDefaultToolkit().sync(); TODO, this ought to be here but causes problems
    }

    /**
     * Draws a scaled and rotated version of an entity
     * @param g2d
     * @param entity
     */
    public void drawEntity(Graphics2D g2d, Entity entity) {
        AffineTransform tx;
        AffineTransformOp op;

        double rotation;
        Image image = entity.getImage();

        int x = entity.getX();
        int y = entity.getY();

        int width = entity.image.getWidth(null);
        int height = entity.image.getHeight(null);

        Size size = entity.getSize();
        double scalex = size.getWidth() / (double) width;
        double scaley = size.getHeight() / (double) height;

        rotation = Math.toRadians(entity.getDegrees());
        tx = AffineTransform.getScaleInstance(scalex, scaley);
        tx.concatenate(AffineTransform.getRotateInstance(rotation, width / 2, height / 2));

        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2d.drawImage(op.filter((BufferedImage) image, null), x, y, null);
    }

    public void drawImage(Graphics2D g2d, Image img, int x, int y) {
        g2d.drawImage(img, x, y, this);
    }
}