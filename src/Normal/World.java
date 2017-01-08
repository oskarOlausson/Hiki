package Normal;

import LevelCodeBreaker.CodeBreaker;
import LevelMat.LevelMat;
import LevelClub.LevelClub;
import LevelColor.LevelColor;
import LevelChoice.LevelChoice;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import java.util.List;

public class World extends JPanel{

    private GameState state = GameState.MENU;
    private Input input;

    @SuppressWarnings("FieldCanBeLocal")
    private boolean running = false;
    private ReentrantLock lock = new ReentrantLock();

    private Image menuImage = Library.loadImage("titleScreen");
    private Image moonGuy = Library.loadImage("moonguy");
    private Position moonGuyPosition = new Position(FrameConstants.WIDTH.value * 0.3, FrameConstants.HEIGHT.value * 0.65);
    private List<Button> buttons = new ArrayList<>();
    private List<MenuButton> menuButtons = new ArrayList<>();

    private ArrayList<Level> levels = new ArrayList<>();
    private int levelIndex = 0;

    public World(Input input) {
        this.input = input;
        setFocusable(true);
        setBackground(Color.WHITE);
        addKeyListener(input);

        int x;
        int y;

        for (int i = 0; i < 4; i++) {
            x = (int) (FrameConstants.WIDTH.value / 5 + (i / 3f) * (FrameConstants.WIDTH.value * 3 / 5));
            y = FrameConstants.HEIGHT.value - 100;
            buttons.add(new Button(input.getController(i), x, y));
            menuButtons.add(new MenuButton(input.getController(i), x, y));
        }

        input.getControllers().forEach(c -> c.setString("Välkommen, välj\nkaraktär"));

        levels.add(new LevelColor(this));

        //levels.add(new LevelChoice(this));
        levels.add(new LevelMat(this));
        levels.add(new LevelClub(this));
        levels.add(new CodeBreaker(this));

        this.levelIndex = 0;
        //levels.get(levelIndex).start(input);
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
                //levels.get(this.levelIndex).start(input);

                input.getControllers().forEach(c -> c.getPlayerInfo().tickPlayed(levels.get(previousLevelIndex).toEnum()));

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
            changeLevel(0);
            input.restart();
            buttons.forEach(Button::reset);
            input.getControllers().forEach(c -> c.setString("Välkommen, välj\nkaraktär"));
        }

        if (state.equals(GameState.PLAY)) {
            levels.get(levelIndex).tick();
            if (levels.get(levelIndex).isDone()) {
                nextLevel();
                input.getControllers().forEach(c -> c.setString("Tryck när ni\när redo"));
            }
        }
        else if (state.equals(GameState.BETWEEN)){
            if (levels.get(levelIndex).hasExplanation()) {
                levels.get(levelIndex).tickBetween(input);
                int sum = 0;
                for (Button b : buttons) {
                    b.update();
                    if (b.isDone()) sum++;
                }

                if (sum >= buttons.size()) {
                    state = GameState.PLAY;
                    levels.get(levelIndex).start(input);
                    buttons.forEach(Button::reset);
                }
            }
            else {
                state = GameState.PLAY;
                levels.get(levelIndex).start(input);
            }
        }
        else {
            int sum = 0;
            for(MenuButton b : menuButtons) {
                if (b.update()) sum++;
            }

            if (sum >= menuButtons.size()) {
                if (!levels.get(this.levelIndex).hasExplanation()) {
                    state = GameState.PLAY;
                }
                else {
                    state = GameState.BETWEEN;
                    input.getControllers().forEach(c -> c.setString("Tryck när ni\när redo"));
                }
                menuButtons.forEach(m -> m.getButton().reset());
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
                    menuButtons.forEach(b -> b.draw(g));
                }
                else {
                    if (levels.get(levelIndex).hasExplanation()) {
                        levels.get(levelIndex).drawBetween(g);
                        buttons.forEach(b -> b.draw(g));
                    }
                }
            }
            else levels.get(levelIndex).doDrawing(g);
        } finally {
            lock.unlock();
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public void updatePlayerInfo() {
        input.getControllers().forEach(c -> {
            c.writeToFile();
        });
    }
}