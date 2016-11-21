package Normal;

/**
 * Created by oskar on 2016-11-17.
 */

public class Size {

    private int width;
    private int height;

    public Size(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Size(double width, double height){
        this((int) width, (int) height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRadius(){
        return (width + height) / 4;
    }
}
