package Normal; /**
 * Created by oskar on 2016-11-17.
 */

import java.awt.*;

/**
 * Created by oskar on 2016-10-16.
 */
public abstract class Entity {

    //position variables
    protected double x;
    protected double y;

    //speed variables
    protected double dx = 0;
    protected double dy = 0;

    protected double width;
    protected double height;

    protected Image image;

    //if the object should be removed
    protected boolean remove = false;

    //rotation of object
    protected double degrees = 0;

    public int getX(){
        return (int) (x - width/2);
    }
    public int getY(){ return (int) (y - height/2); }

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

    public void delete() {
        remove = true;
    }

    public boolean ifRemove() {
        return remove;
    }
}
