package Normal;

/**
 * Created by oskar on 2016-11-21.
 * This class has a timer and will count down 60 ticks per second
 */
public class Timer {

    private int ticks;
    private boolean done;

    public Timer(int ticks) {
        this.ticks = ticks;
    }

    public int update() {
        if (!done) {
            ticks--;
            if (ticks <= 0) done = true;
        }
        return ticks;
    }

    public boolean isDone() {
        return done;
    }
}
