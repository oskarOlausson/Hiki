package Normal;

/**
 * Created by oskar on 2017-01-02.
 * Connects a player controller to the information of the player
 */

public class PlayerController {

    private PlayerInfo playerInfo = null;
    private final Lcd screen;
    private final int digitalIndex;
    private final int sensorIndex;
    private boolean digitalDown = false;
    //zero to one
    private double sensorValue = 0.5;

    private boolean digitalPressed = false;
    private boolean digitalReleased = false;
    private Integer index;

    public PlayerController(Lcd screen, int digitalIndex, int sensorIndex) {
        this.screen = screen;
        screen.reset();
        screen.setBacklight(true);
        this.digitalIndex = digitalIndex;
        this.sensorIndex = sensorIndex;
        this.index = sensorIndex;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Lcd getScreen() {
        return screen;
    }

    public int getDigitalIndex() {
        return digitalIndex;
    }

    public int getSensorIndex() {
        return sensorIndex;
    }

    public void writeToFile() {
        if (playerInfo != null) {
            playerInfo.writeToFile();
        }
    }

    public void update(boolean value) {
        digitalPressed = (!digitalDown && value);
        digitalReleased = (digitalDown && !value);
        digitalDown = value;
    }

    public void update(int value) {
        sensorValue = (double) value / 1000;
    }

    public void update(boolean[] digital) {
        update(digital[digitalIndex]);
    }

    public void update(int[] sensorData) {
        update(sensorData[sensorIndex]);
    }

    public void update(Input input) {
        update(input.digitalData());
        update(input.sensorData());
    }

    public double getSliderValue() {
        return sensorValue;
    }

    public boolean isButtonDown() {
        return digitalDown;
    }

    public boolean isButtonPressed() {
        return digitalPressed;
    }

    public boolean isButtonReleased() {
        return digitalReleased;
    }


    public Integer getIndex() {
        return index;
    }

    /**
     * Really slow, should not be used 60 times a second
     * @param string, string to write
     */
    public void setString(String string) {
        screen.setString(string);
    }
}
