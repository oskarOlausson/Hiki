package Normal;


import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by oskar on 2017-01-01.
 * This classes has some inputs and outputs
 */
public class PlayerInfoList {

    private java.util.List<PlayerInfo> playerInformation = new ArrayList<>();
    private Font font = new Font("Sans-serif", Font.PLAIN, 20);
    private int width = 150;
    private int chosenIndex = 0;
    private double slider = 0;
    private PlayerController playerController;
    private boolean isChosen = false;
    private int height = -1;
    private Stroke bigStroke = new BasicStroke(3);
    private Stroke smallStroke = new BasicStroke(1);

    public PlayerInfoList(PlayerController playerController) {
        this.playerController = playerController;
        listOfPlayerInfoFromFolder();
    }

    private void listOfPlayerInfoFromFolder() {
        playerInformation.clear();
        listOfPlayerInfoFromFolder(new File("./PlayerFiles"));
    }

    private void listOfPlayerInfoFromFolder(File folder) {
        File[] listOfFiles = folder.listFiles();
        String fileName;
        File fileAtIndex;
        PlayerInfo pi;
        PlayerInfo pi2;

        for (int i = 0; i < listOfFiles.length; i++) {
            fileAtIndex = listOfFiles[i];
            if (fileAtIndex.isFile()) {
                fileName = listOfFiles[i].getName();
                if (fileName.length() > 4 || fileName.substring(fileName.length() - 4).equals(".plf")) {
                    pi = new PlayerInfo(fileAtIndex, true);

                    if (pi.getName().equals("noName")) {
                        pi2 = new PlayerInfo(fileAtIndex, false);
                        if (!pi2.getName().equals("noName")) pi = pi2;
                    }

                    playerInformation.add(pi);
                    //System.out.println(pi.getPlayerInfoAsString());
                }
                else {
                    System.err.println("Found file that is not .plf");
                }
            } else if (fileAtIndex.isDirectory()) {
                //RECURSION, reads the files from the subfolders as well
                listOfPlayerInfoFromFolder(fileAtIndex);
            }
        }
    }

    public List<PlayerInfo> getList() {
        return playerInformation;
    }

    public void draw(Graphics g, int x, int y) {

        g.setFont(font);
        int fontHeight = g.getFontMetrics().getHeight();

        if (height == -1) {
            height = fontHeight * (playerInformation.size() + 2);
        }

        Rectangle rect = new Rectangle(x, y, width, fontHeight);

        drawTextBlock(g, "Välj spelare:", rect, false);

        int count = 0;
        for (PlayerInfo playerInfo : playerInformation) {
            drawTextBlock(g, playerInfo.getName(), rect, count == chosenIndex);
            count++;
        }

        //drawTextBlock(g, "ny spelare", rect, count == chosenIndex);
    }

    public void update() {
        if (!isChosen) {
            slider = playerController.getSliderValue();
            chosenIndex = (int) (slider * (playerInformation.size()));
        }
    }

    private void drawTextBlock(Graphics g, String text, Rectangle rect, boolean chosen) {
        Graphics2D g2d = (Graphics2D) g;
        if (isChosen) {
            g2d.setStroke(bigStroke);
        }
        else g2d.setStroke(smallStroke);

        if (chosen) g2d.setColor(new Color(142, 19, 72, 200));
        else g2d.setColor(new Color(248, 31, 129, 200));
        g2d.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        g2d.setColor(new Color(248, 211, 228));
        g2d.drawRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
        DrawFunctions.drawCenteredText(g, text, rect);
        rect.setLocation((int) rect.getX(), (int) (rect.getY() + rect.getHeight()));
    }

    public void tickLevel(LevelEnum levelEnum) {
        playerInformation.forEach(p -> p.tickPlayed(levelEnum));
    }

    public int getWidth() {
        return width;
    }

    public void lockDown(boolean lock) {
        isChosen = lock;
    }

    public PlayerInfo getChosen() {
        return playerInformation.get(chosenIndex);
    }

    public int getHeight() {
        return height;
    }
}
