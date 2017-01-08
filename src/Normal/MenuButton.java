package Normal;

import java.awt.*;

/**
 * Created by oskar on 2017-01-06.
 * This classes has some inputs and outputs
 */
public class MenuButton {

    private PlayerController playerController;
    private final int x;
    private final int y;
    private final Button button;
    private final PlayerInfoList playerInfoList;

    public MenuButton(PlayerController playerController, int x, int y) {
        this.playerController = playerController;
        this.x = x;
        this.y = y;
        button = new Button(playerController, x, y);
        playerInfoList = new PlayerInfoList(playerController);
    }

    public Button getButton() {
        return button;
    }

    public PlayerInfoList getPlayerInfoList() {
        return playerInfoList;
    }

    private PlayerInfo getPlayerInfo() {
        return getPlayerInfoList().getChosen();
    }

    public boolean update() {
        playerInfoList.update();
        if (button.update()) {
            playerInfoList.lockDown(true);
            playerController.setPlayerInfo(getPlayerInfo());
        }
        else {
            playerInfoList.lockDown(false);
        }

        return button.isDone();
    }

    public void draw(Graphics g) {
        button.draw(g);
        playerInfoList.draw(g, x - playerInfoList.getWidth() / 2, y - playerInfoList.getHeight() - 30);
    }
}
