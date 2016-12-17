package LevelColor;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-12-17.
 * This classes has some inputs and outputs
 */
public class ColorExplanation extends BetweenLevels {
    private final int sliderHeight;
    private double progress = 0;
    private double progress2 = 1;
    private ColorSlider colorSlider;
    private Image slider;
    private Image sliderButton;
    private Timer[] timers = {new Timer(1), new Timer(2), new Timer(1), new Timer(2)};
    private Timer[] timers2 = {new Timer(2), new Timer(1), new Timer(2), new Timer(1)};
    private int index = 0;
    private int index2 = 0;
    private int topPad = 15;
    private int bottomPad = 30;
    private Position sliderPosition;
    private Position sliderPosition2;
    private Colors colors = new Colors();

    public ColorExplanation() {
        super("colorInstructions");
        slider = Library.loadImage("sliderSlider");
        sliderHeight = slider.getHeight(null);
        sliderButton = Library.loadImage("sliderButton");
        colorSlider = new ColorSlider(null, FrameConstants.WIDTH.value / 2, 180);
        colorSlider.setControl(0, 1);
        colorSlider.setPad(200);

        sliderPosition = new Position(FrameConstants.WIDTH.value * 0.1,  FrameConstants.HEIGHT.value * 0.3);
        sliderPosition2 = new Position(FrameConstants.WIDTH.value * 0.9 - slider.getWidth(null),  FrameConstants.HEIGHT.value * 0.3);
    }

    @Override
    public void tick(Input input) {
        Timer timerNow = timers[index];
        if (index == 1) {
            progress = (timerNow.getProgress());
        }
        else if (index == 3) {
            progress = (1 - timerNow.getProgress());
        }
        timerNow.update();

        if (timerNow.isDone()) {
            index++;
            if (index >= timers.length) {
                index = 0;
            }
            timerNow.restart();
        }

        Timer timerNow2 = timers2[index2];
        if (index2 == 1) {
            progress2 = (1 - timerNow2.getProgress());
        }
        else if (index2 == 3) {
            progress2 = (timerNow2.getProgress());
        }

        timerNow2.update();
        if (timerNow2.isDone()) {
            index2++;
            if (index2 >= timers2.length) {
                index2 = 0;
            }
            timerNow2.restart();
        }

        int[] sensorArray = {(int) (progress * 1000), (int) (progress2 * 1000)};
        colorSlider.inputs(sensorArray, null);

        colorSlider.move();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, 0, 0, null);

        g.drawImage(slider, sliderPosition.drawX(), sliderPosition.drawY(), null);
        g.drawImage(sliderButton, sliderPosition.drawX(), (int) (topPad + sliderPosition.drawY() + progress * (sliderHeight - topPad - bottomPad)), null);

        g.drawImage(slider, sliderPosition2.drawX(), sliderPosition2.drawY(), null);
        g.drawImage(sliderButton, sliderPosition2.drawX(), (int) (topPad + sliderPosition2.drawY() + progress2 * (sliderHeight - topPad - bottomPad)), null);

        colorSlider.drawInterface(g, colors);
        g.setColor(colors.primaryGet(colorSlider.getIndex()));
        g.fillRect(colorSlider.getX() + 10, 220, 40, 210);
    }
}
