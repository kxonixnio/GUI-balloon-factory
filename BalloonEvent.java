import java.util.EventObject;

public
class BalloonEvent
        extends EventObject {
    private int x;
    private int y;

    /*
        Ustawienie koordynatów dla poszczególnego balonu
     */
    public BalloonEvent(Balloon source, int x, int y) {
        super(source);
        this.x = x;
        this.y = y;
    }

    @Override
    public Balloon getSource() {
        return (Balloon)super.getSource();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
