package LevelClub;
import Normal.*;

/**
 * Created by oskar on 2016-11-23.
 * This classes has some inputs and outputs
 */
class Clubber extends Player {

    private String name;
    private int index = 0;
    private int aimX = 0;

    Clubber(World world, String playerImage, double x, double y, String name) {
        super(world, playerImage);
        this.x = x;
        this.y = y;
        this.name = name;
    }

    void setSensorIndex(int index) {
        this.index = index;
    }

    @Override
    public void move() {
        x += dx;

        if (Math.abs(x - aimX) < 2) {
            x = aimX;
        }
    }

    String getName() {
        return name;
    }

    @Override
    public void inputs(int[] sensorData, boolean[] digitalData) {
        double data = normalize(sensorData[index]) - 0.5;

        aimX = (int) (FrameConstants.WIDTH.value * 0.5 + data * 500);

        x = 0.8 * x + 0.2 * aimX;
    }
}
