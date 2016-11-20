/**
 * Created by oskar on 2016-11-17.
 */
public class Position {

    private double x;
    private double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int drawX() {
        return (int) Math.round(x);
    }

    public int drawY() {
        return (int) Math.round(y);
    }
}
