package Normal;

import LevelCodeBreaker.CodeBreaker;
import LevelMat.LevelMat;
import LevelClub.LevelClub;
import LevelWalks.LevelWalk;
import LevelRunner.LevelRunner;
import LevelColor.LevelColor;
import LevelChoice.LevelChoice;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import java.util.List;

public class World extends JPanel{

    private GameState state = GameState.MENU;
    private Timer betweenTimer = new Timer(3);
    private Input input;
    private boolean running = false;
    private ReentrantLock lock = new ReentrantLock();

    private Image menuImage = Library.loadImage("titleScreen");
    private Image moonGuy = Library.loadImage("moonguy");
    private Position moonGuyPosition = new Position(FrameConstants.WIDTH.value * 0.3, FrameConstants.HEIGHT.value * 0.65);
    private List<MenuButton> buttons = new ArrayList<>();

    private ArrayList<Level> levels = new ArrayList<>();
    private int levelIndex = 0;

    public World(Input input) {
        this.input = input;
        setFocusable(true);
        setBackground(Color.WHITE);
        addKeyListener(input);

        for (int i = 0; i < 4; i++) {
            buttons.add(new MenuButton(i, (int) (FrameConstants.WIDTH.value / 5 + (i / 3f) * (FrameConstants.WIDTH.value * 3 / 5)), FrameConstants.HEIGHT.value - 100));
        }

        levels.add(new LevelClub(this));
        levels.add(new LevelChoice(this));
        levels.add(new LevelColor(this));
        levels.add(new LevelMat(this));
        levels.add(new LevelRunner(this));
        levels.add(new CodeBreaker(this));
        levels.add(new LevelWalk(this));

        this.levelIndex = 1;
        levels.get(levelIndex).start();
    }

    public void restartLevel() {
        levels.get(levelIndex).end();
        levels.get(levelIndex).start();
    }

    public void nextLevel() {
        if (levelIndex + 1 >= levels.size()) changeLevel(0);
        else changeLevel(levelIndex + 1);
    }

    public boolean changeLevel(int levelIndex) {

        state = GameState.BETWEEN;
        lock.lock();
        try {
            if (levelIndex >= 0 && levelIndex <= levels.size()) {
                int previousLevelIndex = this.levelIndex;
                this.levelIndex = levelIndex;

                //ends previous level
                levels.get(previousLevelIndex).end();

                //starts next level
                levels.get(this.levelIndex).start();

                return true;
            } else {
                System.err.println("LevelIndex is out of range, levelIndex: " + levelIndex);
                return false;
            }
        } finally {
            lock.unlock();
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

        if (input.ifRestart()) {
            state = GameState.MENU;
            levelIndex = 0;
            input.restart();
            buttons.forEach(MenuButton::reset);
        }

        if (state.equals(GameState.PLAY)) {
            levels.get(levelIndex).tick(input);
        }
        else if (state.equals(GameState.BETWEEN)){
            levels.get(levelIndex).tickBetween(input);
            int sum = 0;
            for(MenuButton b : buttons) {
                b.update(input.digitalData());
                if (b.isDone()) sum++;
            }

            if (sum >= buttons.size()) {
                state = GameState.PLAY;
                buttons.forEach(MenuButton::reset);
            }
        }
        else {
            int sum = 0;
            for(MenuButton b : buttons) {
                b.update(input.digitalData());
                if (b.isDone()) sum++;
            }

            if (sum >= buttons.size()) {
                state = GameState.BETWEEN;
                buttons.forEach(MenuButton::reset);
            }
        }

        input.reset();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lock.lock();
        try {
            if (!state.equals(GameState.PLAY)) {
                if (state.equals(GameState.MENU)) {
                    g.drawImage(menuImage, 0, 0, null);
                    DrawFunctions.drawImage(g, moonGuy, moonGuyPosition.drawX(), moonGuyPosition.drawY(), .5, .5, 0);
                }
                else {
                    levels.get(levelIndex).drawBetween(g);
                }

                buttons.forEach(b -> b.draw(g));
            }
            else levels.get(levelIndex).doDrawing(g);
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

        rotation = Math.toRadians(entity.getDirection());
        tx = AffineTransform.getScaleInstance(scalex, scaley);
        tx.concatenate(AffineTransform.getRotateInstance(rotation, width / 2, height / 2));

        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g2d.drawImage(op.filter((BufferedImage) image, null), x, y, null);
    }

    public void drawImage(Graphics2D g2d, Image img, int x, int y) {

        g2d.drawImage(img, x, y, this);
    }
}