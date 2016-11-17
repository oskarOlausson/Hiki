/**
 * Created by oskar on 2016-11-17.
 */
public class Block extends Entity {

    public Block(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private boolean remove = false;
    @Override
    public void move() {

    }

    public void delete() {
        remove = true;
    }

    public boolean ifRemove() {
        return remove;
    }
}
