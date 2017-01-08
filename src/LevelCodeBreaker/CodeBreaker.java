package LevelCodeBreaker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Normal.*;


public class CodeBreaker extends Level {

    private World world;

    private List<Lock> locks = new ArrayList<>();
    private List<Lock> unlocked = new ArrayList<>();
    private List<Lcd> screens = new ArrayList<>();
    private boolean success = false;
    private Timer timer;

    private Image background;
    private WinEffect winEffect = null;

    public CodeBreaker(World world) {
        this.world = world;
        background = Library.loadImage("back");
    }

    @Override
    public void start(Input input) {

        String clue = "Liten till stor";

        Lock lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.addController(input.getController(0));
        locks.add(lock);

        lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.addController(input.getController(1));
        locks.add(lock);

        lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.addController(input.getController(2));
        locks.add(lock);

        lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.addController(input.getController(3));
        locks.add(lock);

        locks.forEach(l -> l.getController().setString(String.format("%s\n%d", clue, l.getNumber())));

        timer = new Timer(1);

        winEffect = new WinEffect();
    }

    @Override
    public void end() {
        locks = new ArrayList<>();
        for (Lcd screen : screens) {
            screen.reset();
            screen.close();
        }

        screens = new ArrayList<>();
        timer = null;
        winEffect = null;
    }

    public void tick(){

        for(Lock lock: locks) {
            if (lock.update()) {
                if (!unlocked.contains(lock)) unlocked.add(lock);
            }
            else unlocked.remove(lock);
        }

        Lock previous = null;
        int correctCount = 0;

        for(Lock lock: unlocked) {
            if (previous == null || previous.getNumber() <= lock.getNumber()) {
                correctCount ++;
            }
            previous = lock;
        }
        System.out.println(correctCount);

        if (correctCount == locks.size()) {
            if (!success) {
                locks.forEach(Lock::success);
            }
            success = true;
        }

        if (success) {
            timer.update();

            if (winEffect != null) {
                if (winEffect.isStarted()) winEffect.start();
                winEffect.tick();
            }

            if (timer.isDone()) {
                world.nextLevel();
            }
        }
    }

    public void doDrawing(Graphics g) {
        DrawFunctions.drawImage(g, background, 0, 0);
        for (Lock lock: locks) {

            DrawFunctions.drawImage(g, lock.getImage(), lock.getX(), lock.getY());
        }

        if (winEffect != null) {
            if (winEffect.isStarted()) winEffect.draw(g);
        }

    }

    @Override
    public LevelEnum toEnum() {
        return LevelEnum.CODE;
    }
}