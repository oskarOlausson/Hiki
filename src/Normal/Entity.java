package Normal;
import java.awt.*;

/**
 * All objects
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
    private  boolean remove = false;

    //rotation of object
    protected double direction = 0;

    public int getX(){
        return (int) (x - width/2);
    }
    public int getY(){ return (int) (y - height/2); }

    protected void setSizeFromImage() {
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

    protected Size getSize(){
        return new Size(width, height);
    }

    public Position getCenter(){
        return new Position(x, y);
    }

    protected double getDirection(){
        return direction;
    }

    public void delete() {
        remove = true;
    }

    public boolean ifRemove() {
        return remove;
    }
}
