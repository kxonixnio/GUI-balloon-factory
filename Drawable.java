import java.awt.*;
import java.util.Random;

public
abstract class Drawable {

    protected int x, y;

    public Drawable(){
        Random random = new Random();
        this.x = 20 + random.nextInt(541);
        this.y = 750;
    }

    public abstract void draw(Graphics g);
}
