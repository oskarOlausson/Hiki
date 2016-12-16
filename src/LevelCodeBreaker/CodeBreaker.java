package LevelCodeBreaker;

import com.phidgets.TextLCDPhidget;

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
    private int playerCount = 4;

    private Image background;

    public CodeBreaker(World world) {
        this.world = world;
        background = Library.loadImage("back");
    }

    @Override
    public void start() {

        String clue = "Small to Large";

        Lock lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.setPlayer(PlayerNumber.P1);
        lock.setInteraction(InputConstants.P1_SLIDE);
        locks.add(lock);

        lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.setPlayer(PlayerNumber.P2);
        lock.setInteraction(InputConstants.P2_SLIDE);
        locks.add(lock);

        lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.setPlayer(PlayerNumber.P3);
        lock.setInteraction(InputConstants.P3_SLIDE);
        locks.add(lock);

        lock = new Lock(100 + locks.size() * 200, FrameConstants.HEIGHT.value / 2);
        lock.setPlayer(PlayerNumber.P4);
        lock.setInteraction(InputConstants.P4_SLIDE);
        locks.add(lock);


        //SCREEN
        Lcd lcd = new Lcd(ScreenNumbers.ONE);
        lcd.setString(0, clue);
        lcd.setString(1, Integer.toString(locks.get(0).getNumber()) + "(" + InputConstants.sensorToString(locks.get(0).getInteraction()) + ")");
        lcd.setBacklight(true);
        screens.add(lcd);

        lcd = new Lcd(ScreenNumbers.TWO);
        lcd.setString(0, clue);
        lcd.setString(1, Integer.toString(locks.get(1).getNumber()) + "(" + InputConstants.sensorToString(locks.get(1).getInteraction()) + ")");
        lcd.setBacklight(true);
        screens.add(lcd);

        lcd = new Lcd(ScreenNumbers.THREE);
        lcd.setString(0, clue);
        lcd.setString(1, Integer.toString(locks.get(2).getNumber()) + "(" + InputConstants.sensorToString(locks.get(1).getInteraction()) + ")");
        lcd.setBacklight(true);
        screens.add(lcd);

        lcd = new Lcd(ScreenNumbers.FOUR);
        lcd.setString(0, clue);
        lcd.setString(1, Integer.toString(locks.get(3).getNumber()) + "(" + InputConstants.sensorToString(locks.get(1).getInteraction()) + ")");
        lcd.setBacklight(true);
        screens.add(lcd);

        timer = new Timer(1);
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
    }

    public void tick(Input input){

        for(Lock lock: locks) {
            if (lock.update(input.sensorData())) {
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

        if (correctCount == playerCount) {
            if (!success) {
                locks.forEach(Lock::success);
            }
            success = true;
        }

        if (success) {
            timer.update();

            if (timer.isDone()) {
                world.nextLevel();
            }
        }
    }

    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        world.drawImage(g2d, background, 0, 0);
        for (Lock lock: locks) {
            world.drawEntity(g2d, lock);
        }
    }

    @Override
    public void doDrawing(Graphics g, GameState gameState) {
        if (gameState.equals(GameState.BETWEEN)) {
            drawBetween(g);
        }
        else doDrawing(g);
    }
}