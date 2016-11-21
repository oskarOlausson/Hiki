package Normal; /**
 * Created by oskar on 2016-11-17.
 * This is the main class
 */
import javax.swing.*;

public class Frame extends JFrame {

    private World world;
    private Input input;

    public Frame() {

        initUI();
    }

    private void initUI() {
        input = new Input();
        world = new World(input);

        add(world);

        setSize(FrameConstants.WIDTH.value, FrameConstants.HEIGHT.value);
        setResizable(false);

        setTitle("Hikikomori");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }




    public void run(){
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