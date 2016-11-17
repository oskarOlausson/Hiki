/**
 * Created by oskar on 2016-11-17.
 */
import javax.swing.*;

public class Frame extends JFrame {

    private World world;
    public Frame() {

        initUI();
    }

    private void initUI() {
        world = new World();

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
        ex.setVisible(true);
        //runs the program
        ex.run();
    }
}