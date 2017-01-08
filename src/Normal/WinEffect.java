package Normal;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oskar on 2016-12-19.
 * This classes has some inputs and outputs
 */
public class WinEffect {
    private List<Particle> confetti = new ArrayList<>();
    private boolean init = false;

    public WinEffect() {

    }

    public void start() {
        for (int i = 0; i < 120; i++) {
            confetti.add(new Particle());
        }
        init = true;
    }

    public void tick() {
        if (init) {
            confetti.forEach(Particle::move);
        }
    }

    public void draw(Graphics g) {
        if (init) {
            for (Particle p : confetti) {
                g.setColor(p.getColor());
                g.fillRect(p.getX() - p.getWidth(), p.getY(), p.getWidth() * 2, p.getHeight());
            }
        }
    }

    public boolean isStarted() {
        return init;
    }
}
