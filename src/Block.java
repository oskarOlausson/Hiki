/**
 * Created by oskar on 2016-11-17.
 * This classes has some inputs and outputs
 */
public class Block extends Entity {

    public Block(double x, double y) {
        this.x = x;
        this.y = y;
        image = ImageFunction.loadImage("Images/block.png");
        setSizeFromImage();
    }

    private boolean remove = false;

    public void delete() {
        remove = true;
    }

    public boolean ifRemove() {
        return remove;
    }
}
