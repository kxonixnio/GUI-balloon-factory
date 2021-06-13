import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Storage {
    /*
        ArrayBlockingQueue, bo dopuszczamy maksymalnie 90 balonów
     */
    public static Queue<Balloon> balloonQueue = new ArrayBlockingQueue<>(99);
    public static int uniqueID = 0;

    /*
        Usunięcie balonów bo przekroczeniu dopuszczalnego limitu
     */
    public static void deleteBalloons(){

        if(balloonQueue.size() >= 90){
            for (int i = 0; i < 90; i++) {
                Storage.balloonQueue.poll();
            }
            System.out.println("The balloons are gone.\n");
        }
    }

    /*
        Przypisanie unikalnego ID każdemu balonowi
     */
    public static int getUniqueID(){
        return uniqueID += 1;
    }

}
