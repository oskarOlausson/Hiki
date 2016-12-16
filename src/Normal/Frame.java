package Normal; /**
 * Created by oskar on 2016-11-17.
 * This is the main class
 */
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private World world;
    private Input input;

    private Frame() {

        initUI();
    }

    private void initUI() {
        input = new Input();
        world = new World(input);

        add(world);

        setPreferredSize(new Dimension(FrameConstants.WIDTH.value, FrameConstants.HEIGHT.value));
        setResizable(false);

        pack();

        setTitle("Hikikomori");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void run(){
        world.run();
    }

    public static void main(String[] args) {
        Frame ex = new Frame();

        /*Some piece of code*/
        ex.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ex.input.close();
                ex.input = null;
            }
        });

        ex.setVisible(true);
        //runs the program
        ex.run();
    }
}