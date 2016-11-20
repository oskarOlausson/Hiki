package Normal;

import Codebreaker.CodeBreaker;
import MatMover.LevelMat;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;

public class World extends JPanel{

    private GameState state = GameState.PLAY;
    private Input input;
    private boolean running = false;
    private ReentrantLock lock = new ReentrantLock();

    private ArrayList<Level> levels = new ArrayList<>();
    private int levelIndex = 0;

    public World(Input input) {
        this.input = input;
        setFocusable(true);
        setBackground(Color.BLACK);
        levels.add(new CodeBreaker(this));
        levels.add(new LevelMat(this));

        levels.get(levelIndex).start();
    }

    public void restartLevel() {
        levels.get(levelIndex).end();
        levels.get(levelIndex).start();
    }

    public void nextLevel() {
        levelIndex += 1;
        changeLevel(levelIndex);
    }

    public boolean changeLevel(int levelIndex) {
        if (levelIndex >= 0 && levelIndex <= levels.size()) {
            //ends previous level
            levels.get(this.levelIndex).end();
            this.levelIndex = levelIndex;
            //starts next level
            levels.get(levelIndex).start();
            return true;
        } else {
            System.err.println("LevelIndex is out of range, levelIndex: " + levelIndex);
            return false;
        }
    }

    public synchronized void run(){
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / FrameConstants.SECOND.value;

        long secondTimer = System.currentTimeMillis();
        double delta = 0;
        long now;
        running = true;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while(delta >=1){
                lock.lock();
                try {
                    tick();
                }finally {
                    lock.unlock();
                }
                delta -= 1;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            repaint();

            if (System.currentTimeMillis() - secondTimer > 1000){
                secondTimer += 1000;
            }
        }
    }

    public void tick(){
        levels.get(levelIndex).tick(input);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lock.lock();
        try {
            levels.get(levelIndex).doDrawing(g);
        } finally {
            lock.unlock();
        }
        Toolkit.getDefaultToolkit().sync();
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