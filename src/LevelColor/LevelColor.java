package LevelColor;

import Normal.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Oskar on 2016-11-30.
 * This classes has some inputs and outputs
 */
public class LevelColor extends Level {

    private List<ColorBlob> colorBlobs = new ArrayList<>();
    private List<ColorSlider> colorSliders = new ArrayList<>();
    private List<ColorParticle> colorParticles = new ArrayList<>();
    private ColorSlider colorSlider1;
    private ColorSlider colorSlider2;
    private Colors colors = new Colors();
    private World world;
    private int timer;
    private boolean moving = false;
    private boolean success = false;
    private int level = 0;
    private int levelMax = 10;

    private Random random = new Random();
    private int randMax = (int) (FrameConstants.SECOND.value * 0.5);

    //are arrays because you they change dependent on level
    private int[] timerMax;
    private boolean[] mixColors;
    private boolean[] addNopes;

    private Position screenShake = new Position(0, 0);

    private Font fontNormal = new Font("Sans-Serif", Font.PLAIN, 50);
    private Font fontUpsideDown = new Font("Sans-Serif", Font.PLAIN, -50);

    private double score = 0;
    private double scoreAnim = 0;
    private final int scoreMax = 10;
    private WinEffect winEffect = null;
    private Timer successTimer = null;
    private Input input;

    private boolean pressed = false;
    private int screenShakeAmount = 0;
    private Image gImage = Library.loadImage("colorGoal");


    public LevelColor(World world) {
        super(new ColorExplanation());
        this.world = world;
        getLevelInfo();
    }

    @Override
    public void start(Input input) {
        random.setSeed(System.nanoTime());
        this.input = input;
        colorSlider1 = new ColorSlider(world, FrameConstants.WIDTH.value / 2, 100);
        colorSlider1.addController(input.getController(0), "Du kontrollerar\npositionen");
        colorSlider1.addController(input.getController(1), "Du kontrollerar\nfärgen");

        colorSlider2 = new ColorSlider(world, FrameConstants.WIDTH.value / 2, FrameConstants.HEIGHT.value - 115);
        colorSlider2.addController(input.getController(2), "Du kontrollerar\npositionen");
        colorSlider2.addController(input.getController(3), "Du kontrollerar\nfärgen");

        colorSliders.add(colorSlider1);
        colorSliders.add(colorSlider2);

        timer = timerMax[0] - 1;
        score = 0;
        scoreAnim = score;

        ColorBlob c = new ColorBlob(0);

        c.moveTo(FrameConstants.WIDTH.value / 4, FrameConstants.HEIGHT.value / 2);
        colorBlobs.add(c);

        c = new ColorBlob(0, 1);
        c.moveTo(FrameConstants.WIDTH.value * 3 / 4, FrameConstants.HEIGHT.value / 2);
        colorBlobs.add(c);

        c = new ColorBlob(1);
        c.moveTo(FrameConstants.WIDTH.value / 2, FrameConstants.HEIGHT.value / 2);
        colorBlobs.add(c);

        winEffect = new WinEffect();
        success = false;
        successTimer = new Timer(3);
    }

    @Override
    public void end() {
        colorBlobs      = new ArrayList<>();
        colorSlider1    = null;
        colorSlider2    = null;
        colorSliders    = new ArrayList<>();
        colorParticles  = new ArrayList<>();
        score = 0;
        scoreAnim = 0;
        winEffect = null;
        setDone(false);
        successTimer = null;
    }

    @Override
    public void tick() {
        int index;
        int index2;
        boolean overLaps;

        updateScreenShaker();
        if (!moving && colorBlobs.isEmpty()) {
            moving = true;
        }

        for (ColorSlider c: colorSliders) {
            c.inputs();
            c.move();
        }

        colorParticles.forEach(ColorParticle::move);

        overLaps = colorSlider1.overLaps(colorSlider2);
        double lowPoint = Math.min(colorSlider1.lowPoint(), colorSlider2.lowPoint());
        double highPoint = Math.max(colorSlider1.highPoint(), colorSlider2.highPoint());

        //cheat
        if (input.keyPressed(KeyEvent.VK_E)) {
            if (!pressed) {
                score = scoreMax + 1;
            }
            pressed = true;
        }
        else pressed = false;

        //creates new blobs
        if (moving) {
            if (timer >= timerMax[level]) {
                timer = random.nextInt(randMax);
                if (addNopes[level] && fiftyFifty()) {
                    colorBlobs.add(new ColorBlob(-1));
                } else {
                    index = (int) Math.round(Math.random() * 2);
                    if (fiftyFifty() && mixColors[level]) {
                        do {
                            index2 = (int) Math.round(Math.random() * 2);
                        }
                        while (index == index2);
                        colorBlobs.add(new ColorBlob(index, index2));
                    }
                    else colorBlobs.add(new ColorBlob(index));
                }
            } else timer += 1;
        }

        for (ColorBlob c: colorBlobs) {
            if (c.ifRemove()) {
                continue;
            }
            if (moving) c.update();

            int cType = 1;
            if (c.isEvil()) cType = -1;

            if (overLaps) {
                if (c.collision(lowPoint, highPoint)) {
                    if (!c.isMix()) {
                        if (colorSlider1.getIndex() == colorSlider2.getIndex()) {
                            if (c.match(colorSlider1.getIndex())) {
                                score += cType;
                                if (cType == -1) {
                                    setScreenShaker(10);
                                }
                                c.delete();
                                continue;
                            }
                        }
                    }
                    else if (c.match(colorSlider1.getIndex(), colorSlider2.getIndex())) {
                        score += cType;
                        if (cType == -1) {
                            setScreenShaker(10);
                        }
                        c.delete();
                        continue;
                    }
                }
            }
            else if (!c.isMix()){
                for (ColorSlider slider: colorSliders) {
                    if (c.collision(slider.lowPoint(), slider.highPoint())) {
                        if (c.match(slider.getIndex())) {
                            score += cType;
                            if (cType == -1) {
                                setScreenShaker(10);
                            }
                            c.delete();
                            continue;
                        }
                    }
                }
            }

            if (c.getCenter().getX() > FrameConstants.WIDTH.value) {
                if (c.isEvil()) score++;
                else {
                    score--;
                    setScreenShaker(10);
                }
                c.delete();
                continue;
            }
        }

        if (winEffect != null) {
            winEffect.tick();
        }

        boolean isMix;
        boolean nopes;
        if (score > scoreMax) {
            score = 0;
            if (level < levelMax - 1) {
                isMix = mixColors[level];
                nopes = addNopes[level];
                level++;

                if (!isMix) {
                    if (mixColors[level]) {
                        input.getControllers().forEach(c -> c.setString("Ni kan mixa\nere färger"));
                    }
                }

                if (!nopes) {
                    if (addNopes[level]) {
                        input.getControllers().forEach(c -> c.setString("Matcha inte\nx-blobben"));
                    }
                }

            }
            else {
                success = true;
                if (winEffect != null) {
                    winEffect.start();
                }
            }
        }
        else if (score < 0) score = 0;

        if (success) {
            successTimer.update();
            if (successTimer.isDone()) {
                setDone(true);
            }
        }

        scoreAnim = scoreAnim * 0.95 + score * 0.05;
    }

    private boolean fiftyFifty() {
        return Math.random() > 0.5;
    }

    @Override
    public void doDrawing(Graphics g) {

        int size = 200;

        g.setColor(colors.getScoreColorBack());
        g.drawOval(FrameConstants.WIDTH.value / 2 - size / 2, FrameConstants.HEIGHT.value / 2 - size / 2, size, size);

        int now = (int) (size * Math.min(1, scoreAnim / scoreMax));

        g.setColor(colors.getScoreColor());
        g.fillOval(FrameConstants.WIDTH.value / 2 - now / 2, FrameConstants.HEIGHT.value / 2 - now / 2, now, now);

        drawColorSliders(g, screenShake.drawX(), screenShake.drawY());

        Iterator<ColorParticle> iterColorParticle = colorParticles.iterator();

        while (iterColorParticle.hasNext()) {
            ColorParticle c = iterColorParticle.next();

            if (!c.ifRemove()) {
                c.draw(g);
            }
            else {
                iterColorParticle.remove();
            }
        }

        Iterator<ColorBlob> iterColorBlob = colorBlobs.iterator();

        while (iterColorBlob.hasNext()) {
            ColorBlob c = iterColorBlob.next();

            if (!c.ifRemove()) {
                c.draw(g, colors, screenShake.drawX(), screenShake.drawY());
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

        if (winEffect != null) {
            winEffect.draw(g);
        }

        drawLevel(g, screenShake.drawX(), screenShake.drawY());
    }

    @Override
    public LevelEnum toEnum() {
        return LevelEnum.COLOR;
    }

    private void drawLevel(Graphics g, int dx, int dy) {
        FontMetrics mud = g.getFontMetrics(fontUpsideDown);
        FontMetrics mn = g.getFontMetrics(fontNormal);
        String text = "Level: " + Integer.toString(level + 1) + "/" + Integer.toString(levelMax);
        int pad = 20;
        g.setColor(colors.getTextColor());

        g.setFont(fontUpsideDown);
        g.drawString(text, dx + FrameConstants.WIDTH.value / 2 - mud.stringWidth(text) / 2, dy + pad - mud.getHeight() / 2 + mud.getAscent());

        g.setFont(fontNormal);
        g.drawString(text, dx + FrameConstants.WIDTH.value / 2 - mn.stringWidth(text) / 2, dy + FrameConstants.HEIGHT.value - pad - mud.getHeight() / 2 + mud.getAscent());

        for (int i = 0; i < FrameConstants.HEIGHT.value / 50; i++) {
            DrawFunctions.drawImage(g, gImage, FrameConstants.WIDTH.value - 50, i * 50);
        }
    }

    private void drawColorSliders(Graphics g, int dx, int dy) {
        int pad = 140;
        if (colorSlider1.overLaps(colorSlider2)) {
            int lx = Math.min((int) colorSlider1.lowPoint(), (int) colorSlider2.lowPoint()) + dx;
            int rx = Math.max((int) colorSlider1.highPoint(), (int) colorSlider2.highPoint()) + dy;

            g.setColor(colors.secondaryGet(colorSlider1.getIndex(), colorSlider2.getIndex()));
            g.fillRect(lx, pad, rx - lx, FrameConstants.HEIGHT.value - pad * 2 - 15);
        }
        else {
            for (ColorSlider c : colorSliders) {
                g.setColor(colors.primaryGet(c.getIndex()));
                g.fillRect((int) c.lowPoint() + dx, pad + dy, (int) c.getWidth(), FrameConstants.HEIGHT.value - pad * 2 - 15);
            }
        }

        for (ColorSlider c : colorSliders) {
            c.drawInterface(g, colors, dx, dy);
        }
    }

    public void getLevelInfo() {
        timerMax =  new int[levelMax];
        mixColors = new boolean[levelMax];
        addNopes =  new boolean[levelMax];
        double value;

        //3 seconds on the first level, 2 on the last level
        for (int i = 0; i < levelMax; i++) {

            value = 3;

            if (i > 6) {
                addNopes[i] = true;
                mixColors[i] = true;
                switch (i) {
                    case 7:
                        value = 2.75;
                        break;
                    case 8:
                        value = 2.25;
                        break;
                }
                if (i > 8) {
                    value = 2;
                }
            }
            else if (i > 1) {
                mixColors[i] = true;
                value = 2.5;
            }
            else if (i == 1) {
                value = 2.75;
            }

            timerMax[i] = (int) (value * FrameConstants.SECOND.value);
        }
    }

    @Override
    public boolean hasExplanation() {
        return true;
    }

    public void setScreenShaker(int amount) {
        screenShakeAmount = amount;
    }

    public void updateScreenShaker() {
        if (screenShakeAmount > 1) {
            screenShakeAmount *= 0.95;
            screenShake.randomize(-screenShakeAmount, -screenShakeAmount, screenShakeAmount, screenShakeAmount);
        }
        else {
            screenShake.set(0, 0);
            screenShakeAmount = 0;
        }
    }
}
