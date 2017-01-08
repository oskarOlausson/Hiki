package Normal;

/**
 * Created by oskar on 2016-11-17.
 * Simple position class, useful when returning from function
 */
public class Position {

    private double x;
    private double y;

    public Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Position() {

    }

    public void addToY(int dy) {
        this.y += dy;
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

    public void randomize(int xLow, int yLow, int xHigh, int yHigh) {
        this.x = xLow + Math.random() * (xHigh - xLow);
        this.y = yLow + Math.random() * (yHigh - yLow);
    }

    public double distance(Position pos) {
        return Math.sqrt(Math.pow(x - pos.getX(), 2) + Math.pow(y - pos.getY(), 2));
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void snap(int width, int height) {
        this.x = Math.floor(this.x / width) * width;
        this.y = Math.floor(this.y / height) * height;
    }
}
