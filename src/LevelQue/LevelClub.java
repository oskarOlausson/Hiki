package LevelQue;

import Normal.*;
import com.phidgets.TextLCDPhidget;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by oskar on 2016-11-23.
 * This classes has some inputs and outputs
 */
public class LevelClub implements Level {

    private List<Clubber> players = new ArrayList<>();
    private List<Lcd>    screens = new ArrayList<>();
    private List<Particle> confettis = new ArrayList<>();
    private Image confettiImage = ImageFunctions.loadImage("Images/confetti.png");
    private String solution;
    private World world;
    private boolean success;
    private Image background;
    private Image foreground;


    public LevelClub (World world) {
        this.world = world;
        background = ImageFunctions.loadImage("Images/backClub.png");
        foreground = ImageFunctions.loadImage("Images/klarade.png");
    }

    @Override
    public void start() {

        success = false;
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

        players.add(new Clubber(world, "p1", FrameConstants.WIDTH.value + 100, y, Character.toString(names.charAt(0))));
        players.get(0).setSensorIndex(InputConstants.P1_SLIDE);

        players.add(new Clubber(world, "p2", FrameConstants.WIDTH.value + 120, y + 5, Character.toString(names.charAt(1))));
        players.get(1).setSensorIndex(InputConstants.P2_SLIDE);

        players.add(new Clubber(world, "p3", FrameConstants.WIDTH.value + 110, y + 10, Character.toString(names.charAt(2))));
        players.get(2).setSensorIndex(InputConstants.P3_SLIDE);

        players.add(new Clubber(world, "p4", FrameConstants.WIDTH.value + 116, y + 15, Character.toString(names.charAt(3))));
        players.get(3).setSensorIndex(InputConstants.P4_SLIDE);

        screens.add(new Lcd(141799, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20));
        screens.get(0).setString(0, "Så här står ni just nu:" + currentOrder());

        screens.add(new Lcd(141627, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x16));
        screens.get(1).setString(0, Character.toString(solution.charAt(0)) + " ska vara längst fram");

        screens.add(new Lcd(141787, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x20));
        screens.get(2).setString(0, Character.toString(solution.charAt(3)) + " ska inte vara brevid " + Character.toString(solution.charAt(0)));


        screens.add(new Lcd(141568, TextLCDPhidget.PHIDGET_TEXTLCD_SCREEN_4x16));
        screens.get(3).setString(0, Character.toString(solution.charAt(2)) + " ska vara direkt bakom " + Character.toString(solution.charAt(1)));

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
        players = new ArrayList<>();
        screens.forEach(Lcd::close);
        screens = new ArrayList<>();
        solution = null;
    }

    @Override
    public void tick(Input input) {

        String currentOrder = currentOrder();

        if (currentOrder.equals(solution)) {
            if (!success) {
                success = true;

                for(Lcd s: screens) {
                    s.setString(0, "Ni klarade det!!!");
                }

                for (int i = 0; i < 120; i++) {
                    confettis.add(new Particle());
                }
            }
        }

        for (Clubber c: players) {
            c.inputs(input.sensorData(), input.digitalData());
            c.move();
        }

        confettis.forEach(Particle::move);

        if (!success) screens.get(0).setString(0, "Så här står ni just nu: " + currentOrder);

    }

    @Override
    public void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        world.drawImage(g2d, background, 0, 0);

        for(Clubber c: players) {
            c.draw(g2d);
        }

        if (success) {
            world.drawImage(g2d, foreground, 0, 0);
        }


        for(Particle p: confettis) {
            g2d.setColor(p.getColor());
            g2d.fillRect(p.getX() - p.getWidth(), p.getY(), p.getWidth() * 2, p.getHeight());
        }
    }
}
