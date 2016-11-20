package Normal;

/**
 * Created by oskar on 2016-11-17.
 */
public class Enemy extends Entity{

    private boolean remove = false;

    public void move() {

    }

    public void delete() {
        remove = true;
    }

    public boolean ifRemove() {
        return remove;
    }
}
