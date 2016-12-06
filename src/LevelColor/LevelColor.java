package LevelColor;

import Normal.*;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
public class LevelColor implements Level {

    private List<ColorBlob> colorBlobs = new ArrayList<>();
    private List<ColorSlider> colorSliders = new ArrayList<>();
    private List<ColorParticle> colorParticles = new ArrayList<>();
    private ColorSlider colorSlider1;
    private ColorSlider colorSlider2;
    private Colors colors = new Colors();
    private World world;
    private int timerMax = FrameConstants.SECOND.value * 2;
    private int timer;

    private Font fontNormal = new Font("Sans-Serif", Font.PLAIN, 50);
    private Font fontUpsideDown = new Font("Sans-Serif", Font.PLAIN, -50);

    private int score;
    private int life;

    public LevelColor(World world) {
        this.world = world;
    }

    @Override
    public void start() {
        colorSlider1 = new ColorSlider(world, FrameConstants.WIDTH.value / 2 + 10, 30);
        colorSlider1.setControl(InputConstants.P1_SLIDE, InputConstants.P2_SLIDE);
        colorSlider1.changeColor();
        colorSlider2 = new ColorSlider(world, FrameConstants.WIDTH.value / 2 - 10, FrameConstants.HEIGHT.value - 60);
        colorSlider2.setControl(InputConstants.P3_SLIDE, InputConstants.P4_SLIDE);

        colorSliders.add(colorSlider1);
        colorSliders.add(colorSlider2);
        timer = timerMax;
        score = 0;
        life = 3;
    }

    @Override
    public void end() {
        colorBlobs      = new ArrayList<>();
        colorSlider1    = null;
        colorSlider2    = null;
        colorSliders    = new ArrayList<>();
        colorSliders    = new ArrayList<>();
        colorParticles  = new ArrayList<>();
    }

    @Override
    public void tick(Input input) {
        int index;
        int index2;
        boolean overLaps;

        for (ColorSlider c: colorSliders) {
            c.inputs(input.sensorData(), input.digitalData());
            c.move();
        }

        colorParticles.forEach(ColorParticle::move);

        overLaps = colorSlider1.overLaps(colorSlider2);
        double lowPoint = Math.min(colorSlider1.lowPoint(), colorSlider2.lowPoint());
        double highPoint = Math.max(colorSlider1.highPoint(), colorSlider2.highPoint());

        //TIMED
        if (timer < 0) {
            timer = timerMax;
            if (fiftyFifty()) {
                index = (int) Math.round(Math.random() * 2);
                do {
                    index2 = (int) Math.round(Math.random() * 2);
                }
                while (index == index2);
                colorBlobs.add(new ColorBlob(index, index2));
            }
            else {
                colorBlobs.add(new ColorBlob(-1));
            }
        }
        else timer -= 1;

        for (ColorBlob c: colorBlobs) {
            c.update();
            if (overLaps) {
                if (c.collision(lowPoint, highPoint)) {
                    if (c.match(colorSlider1.getIndex(), colorSlider2.getIndex())) {
                        c.delete();
                        score += 5;
                    }
                }
            }
            else if (!c.isMix()){
                for (ColorSlider slider: colorSliders) {
                    if (c.collision(slider.lowPoint(), slider.highPoint())) {
                        if (c.match(slider.getIndex())) {
                            c.delete();
                            score += 1;
                        }
                    }
                }
            }

            if (c.getCenter().getX() > FrameConstants.WIDTH.value) {
                if (life > 0) life -= 1;
                c.delete();
            }
        }
    }

    private boolean fiftyFifty() {
        return Math.random() > 0.5;
    }

    @Override
    public void doDrawing(Graphics g) {

        drawColorSliders(g);

        Iterator<ColorParticle> iterColorParticle = colorParticles.iterator();

        while (iterColorParticle.hasNext()) {
            ColorParticle c = iterColorParticle.next();

            if (!c.ifRemove()) {
                c.draw(g, world);
            }
            else {
                iterColorParticle.remove();
            }
        }

        Iterator<ColorBlob> iterColorBlob = colorBlobs.iterator();

        while (iterColorBlob.hasNext()) {
            ColorBlob c = iterColorBlob.next();

            if (!c.ifRemove()) {
                c.draw(g, colors);
            }
            else {
                Color color;
                if (c.isMix()) {
                    for (int i = 0; i < 30; i++){
                        if (fiftyFifty()) color = colors.primaryGet(c.getIndex());
                        else color = colors.primaryGet(c.getIndex2());
                        colorParticles.add(new ColorParticle(c.getX(), c.getY(), color));
                    }
                }
                else {
                    color = colors.primaryGet(c.getIndex());
                    for (int i = 0; i < 30; i++) colorParticles.add(new ColorParticle(c.getX(), c.getY(), color));
                }
                iterColorBlob.remove();
            }
        }

        FontMetrics mud = g.getFontMetrics(fontUpsideDown);
        FontMetrics mn = g.getFontMetrics(fontNormal);
        String text = "Your score: " + Integer.toString(score);
        int pad = 40;
        g.setColor(colors.getTextColor());

        g.setFont(fontUpsideDown);
        g.drawString(text, FrameConstants.WIDTH.value / 2 - mud.stringWidth(text) / 2, pad - mud.getHeight() / 2 + mud.getAscent());

        g.setFont(fontNormal);
        g.drawString(text, FrameConstants.WIDTH.value / 2 - mn.stringWidth(text) / 2, FrameConstants.HEIGHT.value - pad - mud.getHeight() / 2 + mud.getAscent());

    }

    private void drawColorSliders(Graphics g) {
        if (colorSlider1.overLaps(colorSlider2)) {
            int lx = Math.min((int) colorSlider1.lowPoint(), (int) colorSlider2.lowPoint());
            int rx = Math.max((int) colorSlider1.highPoint(), (int) colorSlider2.highPoint());

            g.setColor(colors.secondaryGet(colorSlider1.getIndex(), colorSlider2.getIndex()));
            g.fillRect(lx, 85, rx - lx, FrameConstants.HEIGHT.value - 200);
        }
        else {
            for (ColorSlider c : colorSliders) {
                g.setColor(colors.primaryGet(c.getIndex()));

                g.fillRect((int) c.lowPoint(), 85, (int) c.getWidth(), FrameConstants.HEIGHT.value - 200);
            }
        }

        for (ColorSlider c : colorSliders) {
            c.drawInterface(g, colors);
        }
    }
}
