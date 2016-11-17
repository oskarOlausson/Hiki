import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
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


public class World extends JPanel implements ActionListener {

    private GameState state = GameState.PLAY;
    private Player player;
    private List<Block> blocks = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private boolean running = false;
    private List<Integer> keys = new ArrayList<>();
    private Image background;


    public World() {

        initBoard();
    }

    private void initBoard() {

        background = ImageFunction.loadImage("images/back.png");

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);

        player = new Player(this);

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
                System.out.println("frames: "+ frames +", ticks: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public void tick(){
        player.inputs(keys);
        player.move();

        enemies.forEach(Enemy::move);
    }

    public void createBlock(double x, double y){
        Block block = new Block(x, y);
        blocks.add(block);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        //Toolkit.getDefaultToolkit().sync(); TODO, this ought to be here but causes problems
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(background, 0, 0, this);

        drawEntity(g2d, player);

        Iterator<Block> iter = blocks.iterator();

        while (iter.hasNext()) {
            Block block = iter.next();

            if (!block.ifRemove()) {
                drawEntity(g2d, block);
            }
            else {
                iter.remove();
            }
        }

        Iterator<Enemy> iter2 = enemies.iterator();

        while (iter2.hasNext()) {
            Enemy enemy = iter2.next();
            if (!enemy.ifRemove()) {
                drawEntity(g2d, enemy);
            }
            else {
                iter2.remove();
            }
        }
    }

    /**
     * Draws a scaled and rotated version of an entity
     * @param g2d
     * @param entity
     */
    private void drawEntity(Graphics2D g2d, Entity entity) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO i dont know what this is for
    }

    /**
     * This handles keyboard input
     */
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("before: " + keys);
            int key;
            for (int i = 0; i< keys.size(); i++){
                key = keys.get(i);
                if (key == e.getKeyCode()){
                    keys.remove(i);
                    i -= 1;
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keys.add(e.getKeyCode());
        }
    }
}