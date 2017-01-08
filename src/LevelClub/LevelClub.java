package LevelClub;

import Normal.*;
import Normal.Timer;
import com.phidgets.TextLCDPhidget;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by oskar on 2016-11-23.
 * This classes has some inputs and outputs
 */
public class LevelClub extends Level {

    private List<Clubber> players = new ArrayList<>();
    private List<Lcd>    screens = new ArrayList<>();
    private List<Particle> confetti = new ArrayList<>();
    private String solution;
    private World world;
    private boolean success;
    private Image background;
    private Image foreground;
    private Font font = new Font("Sans-Serif", Font.PLAIN, 36);
    private String[] explanation = {"Ställ er i rätt ordning",  "lås sedan er gissning."};
    private WinEffect win;

    private List<Double> wobble = makeWobbleList();
    private double wobbleTimer = 0;
    private Timer successTimer;

    private List<Double> makeWobbleList() {
        List<Double> wobble = new LinkedList<>();
        int amount = 8;
        int length = 0;
        for (int i = 0; i < explanation.length; i++) {
            length += explanation[i].length();
        }
        double step =  (Math.PI * 2) / length;

        for (int i = 0; i < length; i++) {
            wobble.add(amount * Math.cos(step * i));
        }

        return wobble;
    }

    private void updateWobble() {
        wobbleTimer += 0.5;
        if (wobbleTimer >= 1) {
            double top = wobble.get(0);
            wobble.remove(0);
            wobble.add(top);
            wobbleTimer--;
        }
    }

    public LevelClub (World world) {
        super(new ClubExplanation());
        this.world = world;
        background = Library.loadImage("backClub");
        foreground = Library.loadImage("madeIt");
    }

    @Override
    public void start(Input input) {

        win = new WinEffect();

        success = false;
        successTimer = new Timer(2);
        int y =  FrameConstants.HEIGHT.value / 2;

        List<Character> nameList = new ArrayList<>();
        nameList.add('A');
        nameList.add('B');
        nameList.add('C');
        nameList.add('D');

        Collections.shuffle(nameList);

        String names = "";

        for (Character c : nameList) {
            names += c.toString();
        }

        List<Character> solved = new ArrayList<>();
        solved.add('A');
        solved.add('B');
        solved.add('C');
        solved.add('D');

        do {
            Collections.shuffle(solved);
            solution = "";

            for (Character c : solved) {
                solution += c.toString();
            }
        }
        while(solution.equals(names));

        players.add(new Clubber("p1", FrameConstants.WIDTH.value + 100, y, Character.toString(names.charAt(0))));
        players.add(new Clubber("p2", FrameConstants.WIDTH.value + 120, y + 5, Character.toString(names.charAt(1))));
        players.add(new Clubber("p3", FrameConstants.WIDTH.value + 110, y + 10, Character.toString(names.charAt(2))));
        players.add(new Clubber("p4", FrameConstants.WIDTH.value + 116, y + 15, Character.toString(names.charAt(3))));

        for (int i = 0; i < players.size(); i++) {
            players.get(i).addController(input.getController(i));
        }

        input.getController(0).setString("Så står ni nu:\n" + currentOrder());
        input.getController(1).setString(Character.toString(solution.charAt(0)) + " ska vara\nlängst fram");
        input.getController(2).setString(Character.toString(solution.charAt(3)) + " ska inte\nvara brevid " + Character.toString(solution.charAt(0)));
        input.getController(3).setString(Character.toString(solution.charAt(2)) + " ska vara\ndirekt bakom " + Character.toString(solution.charAt(1)));
    }

    private String currentOrder() {
        List<Integer> listI = new ArrayList<>();
        List<Clubber>  listP = new ArrayList<>();

        int x;
        int index;

        for(Clubber clubber: players) {
            x = clubber.getX();

            for(index = 0; index < listI.size(); index ++) {
                if (x <= listI.get(index)) {
                    break;
                }
            }
            listI.add(index, x);
            listP.add(index, clubber);
        }

        String string = "";

        for(Clubber clubber: listP) {
            string += clubber.getName();
        }

        return string;
    }

    @Override
    public void end() {
        int fourOrFive = 4 + (int) Math.round((Math.random()));

        for (Player player: players) {
            player.firstController().getPlayerInfo().setAttributes(PlayerModelEnum.PUZZLE, fourOrFive);
        }

        players = new ArrayList<>();
        for (Lcd screen : screens) {
            screen.reset();
            screen.close();
        }
        screens = new ArrayList<>();
        solution = null;
        successTimer = null;
        setDone(false);
        win = null;
    }

    @Override
    public void tick() {
        String currentOrder = currentOrder();

        updateWobble();

        boolean allPressed = true;

        for(Clubber clubber: players) {
            if (!clubber.isLocked()) {
                allPressed = false;
                break;
            }
        }

        if (currentOrder.equals(solution) && allPressed) {
            if (!success) {
                success = true;

                for(Lcd s: screens) {
                    s.setString("Ni klarade det!!!");
                }

                win.start();
            }
        }

        for (Clubber c: players) {
            c.inputs();
            c.move();
        }

        if (win != null) {
            win.tick();
        }


        if (!success) {
            players.get(0).firstController().getScreen().fastSetString(1, currentOrder);
        }
        else {
            successTimer.update();
            if (successTimer.isDone()) {
                setDone(true);
            }
        }

    }

    @Override
    public void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        DrawFunctions.drawImage(g, background, 0, 0);

        for(Clubber c: players) {
            c.draw(g2d);
        }

        if (success) {
            DrawFunctions.drawImage(g, foreground, 0, 0);
        }
        else {
            Color[] colors= {Color.WHITE};
            String s;
            int offsetX = 0;
            int count = 0;
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics(font);

            for (int c = 0; c < colors.length; c++) {
                g2d.setColor(colors[c]);
                for (int y = 0; y < explanation.length; y++) {
                    offsetX = 0;
                    for (int x = 0; x < explanation[y].length(); x++) {
                        s = explanation[y].substring(x, x + 1);
                        g2d.drawString(s, (int) (FrameConstants.WIDTH.value * 0.5 - fm.stringWidth(explanation[y]) / 2 + offsetX), (int) (FrameConstants.HEIGHT.value * 0.1 + fm.getHeight() * y + ((.5 * c + 1) * wobble.get(count))));
                        offsetX += fm.stringWidth(s);
                        count++;
                    }
                }
                count = 0;
            }
        }

        if (win != null) {
            win.draw(g);
        }
    }

    @Override
    public boolean hasExplanation() {
        return true;
    }

    @Override
    public LevelEnum toEnum() {
        return LevelEnum.CLUB;
    }
}
