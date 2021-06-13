import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BalloonsStoragePanel
extends JPanel
implements Runnable, BalloonListener{

    Random randomNumber = new Random();

    /*
        Ustawienie layoutu i uruchomienie wątku
     */
    BalloonsStoragePanel(){
        setPreferredSize(new Dimension(600, 810));
        setBackground(new Color(214, 252, 255));

        new Thread(this).start();
    }

    /*
        Rysowanie
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        for(Balloon balloon : Storage.balloonQueue){
            balloon.draw(g);
        }
    }

    /*
        30 klatek na sekundę.
        Na bieżąco dodawaj składowanym balonom listenery
        Przy osiągniętym limicie następuje awaryjne wypuszczenie balonów
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            repaint();
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(Balloon balloon : Storage.balloonQueue){
                balloon.addBalloonListener(this);
            }

            if(Storage.balloonQueue.size() == 90) {
                emergencyReleaseOfBalloons();
            }
        }
    }

    /*
        Odbija balony od ścian
     */
    @Override
    public void balloonMove(BalloonEvent evt) {
        if(evt.getX() < 0 || evt.getX() > this.getWidth()) {
            evt.getSource().bounceX();
        }
    }

    public int getNumberOfBalloons(){
        return Storage.balloonQueue.size();
    }

    /*
        Awaryjne wypuszczenie balonów.
        Polega na nadaniu wszystkim balonom wektorów.
     */
    public void emergencyReleaseOfBalloons(){

        for(Balloon balloon : Storage.balloonQueue){
            if(balloon.getBalloonID() % 2 == 0){
                balloon.setVx(7 + randomNumber.nextInt(6));     //7-12
            }else{
                balloon.setVx((7 + randomNumber.nextInt(6)) * (-1));
            }
            balloon.setVy(-6);
        }
        /*
            Po 7.0s (na oko mierzone) balony zostają usunięte z kolejki
         */
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Storage.deleteBalloons();
                    }
                },
                7000
        );
        //^StackOverFlow
        //https://stackoverflow.com/questions/2258066/java-run-a-function-after-a-specific-number-of-seconds
    }
}
