package LevelClub;

import Normal.*;

import java.awt.*;

/**
 * Created by oskar on 2016-12-17.
 * This classes has some inputs and outputs
 */
public class ClubExplanation extends BetweenLevels{

    private final int sliderHeight;
    private double progress = 0;
    private Image avatar;
    private Image slider;
    private Image sliderButton;
    private Timer[] timers = {new Timer(1), new Timer(2), new Timer(1), new Timer(2)};
    private int index = 0;
    private int topPad = 15;
    private int bottomPad = 30;
    private Position sliderPosition = new Position(FrameConstants.WIDTH.value * 0.2,  FrameConstants.HEIGHT.value * 0.3);

    public ClubExplanation() {
        super("clubInstructions");

        avatar = Library.loadImage("p1");
        slider = Library.loadImage("sliderSlider");
        sliderHeight = slider.getHeight(null);
        sliderButton = Library.loadImage("sliderButton");
    }

    @Override
    public void tick(Input input) {
        Timer timerNow = timers[index];
        if (index == 1) {
            progress = (timerNow.getProgress());
        }
        else if (index == 3) {
            progress = (1- timerNow.getProgress());
        }
        timerNow.update();

        if (timerNow.isDone()) {
            index++;
            if (index >= timers.length) {
                index = 0;
            }
            timerNow.restart();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, 0, 0, null);
        g.drawImage(slider, sliderPosition.drawX(), sliderPosition.drawY(), null);
        g.drawImage(sliderButton, sliderPosition.drawX(), (int) (topPad + sliderPosition.drawY() + progress * (sliderHeight - topPad - bottomPad)), null);
        g.drawImage(avatar, (int) (FrameConstants.WIDTH.value * 0.4 + progress * FrameConstants.WIDTH.value * 0.2), FrameConstants.HEIGHT.value / 2, null);
    }
}
