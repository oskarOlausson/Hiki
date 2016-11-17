/**
 * Created by oskar on 2016-11-17.
 */
public class Goal extends Entity {

    public Goal(double x, double y) {
        this.x = x;
        this.y = y;
        String string = "Images/goal.png";
        image = ImageFunction.loadImage(string);
    }

    public void move(Player player) {
        Position other = player.getCenter();
        Position me = getCenter();

        double radii = player.getSize().getRadius() + getSize().getRadius();

        if (Math.sqrt(Math.pow(other.getX() - me.getX(), 2) + Math.pow(other.getY() - me.getY(), 2)) < radii) {
            int pad = 40;
            this.x = pad + Math.random() * (FrameConstants.WIDTH.value - pad * 2);
            this.y = pad + Math.random() * (FrameConstants.HEIGHT.value - pad * 2);
        }
    }
}
