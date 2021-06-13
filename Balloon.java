import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public
class Balloon
        extends Drawable
        implements Runnable {

    private int vx, vy;
    private int sleep;
    protected BaloonColor baloonColor;
    private final long balloonID;

    public Balloon(BaloonColor baloonColor) {
        super();    //<---Drawable
        this.baloonColor = baloonColor;
        balloonID = Storage.getUniqueID();
        sleep = 50;

        new Thread(this).start();
    }

    /*
        Rysowanie balonu.
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(
                new Color(
                        baloonColor.getColor_R(),
                        baloonColor.getColor_G(),
                        baloonColor.getColor_B()
                )
        );
        g.fillOval( x-20, y-20, 40, 50);
        g.setColor(Color.black);
        g.drawLine(x, y+30, x, y+80);
        g.drawOval(x-20, y-20, 40, 50);
    }

    /*
        "Przesuwanie" koordynatów balonu o odpowiedni wektor
     */
    public void move(){
        fireBalloonMove(
                new BalloonEvent(this, x+vx, y+vy)
        );

        x += vx;
        y += vy;
    }

    /*
        Odwrócenie kierunku wektora
     */
    public void bounceX(){
        vx *= -1;
        if(sleep - 10 > 10)
            sleep -= 10;
    }

    public void bounceY(){
        vy *= -1;
        if(sleep - 10 > 10)
            sleep -= 10;
    }

    /*
        Nieustannie (dopóki wątek nie jest interrupted) zmieniaj "koordynaty" balona
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){

            try {
                move();
            }catch(ConcurrentModificationException e){
                System.out.println("ConcurrentModificationException");
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        Dodawanie balloonListenerów do ArrayListy listenerów
     */
    ArrayList<BalloonListener> listeners = new ArrayList<>();
    public void addBalloonListener(BalloonListener balloonListener){
        listeners.add(balloonListener);
    }

    /*
        Dla "nasłuchujących" listenerów wykonaj event (patrz move() wyżej)
     */
    public void fireBalloonMove(BalloonEvent evt){
        for(BalloonListener balloonListener : listeners)
            balloonListener.balloonMove(evt);
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getVx() {
        return vx;
    }

    public long getBalloonID() {
        return balloonID;
    }
}
