/**
 * Created by oskar on 2016-11-17.
 */

import java.awt.*;
import java.util.List;

/**
 * Created by oskar on 2016-10-16.
 */
public abstract class Entity {

    protected double x;
    protected double y;

    protected double dx = 0;
    protected double dy = 0;

    protected double width;
    protected double height;

    protected Image image;

    protected double degrees = 0;


    public int getX(){
        return (int) (x - width/2);
    }

    public void setSizeFromImage() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public boolean collision(Entity entity) {
        Position other = entity.getCenter();
        Position me = getCenter();

        double radii = entity.getSize().getRadius() + getSize().getRadius();

        return (Math.abs(other.getX() - me.getX()) < radii && Math.abs(other.getY() - me.getY()) < radii);
    }

    public int getY(){ return (int) (y - height/2); }

    public Image getImage(){
        return image;
    }

    public Size getSize(){
        return new Size(width, height);
    }

    public Position getCenter(){
        return new Position(x, y);
    }

    public double getDegrees(){
        return degrees;
    }
}
