import javax.swing.*;
import java.awt.*;

public class StoragePanel
extends JComponent {

    StoragePanel(){
        /*
            Wtępne ustawianie layoutu
         */
        setPreferredSize(new Dimension(600, 900));
        setBackground(new Color(214, 252, 255));
        setLayout(new BorderLayout());

        /*
            Panel odpowiedzialny za prezencję balonów
         */
        BalloonsStoragePanel balloonsPanel = new BalloonsStoragePanel();
        add(balloonsPanel, BorderLayout.CENTER);

        /*
            JProgressBar - nieustannie aktualizowany pokazuje ilość przechowywanych balonów
         */
        JProgressBar balloonsProgress = new JProgressBar(0, 99);
        Runnable r = () -> {
            while(true) {
                balloonsProgress.setValue(balloonsPanel.getNumberOfBalloons());
                balloonsProgress.remove(this);
                balloonsProgress.repaint();
                balloonsProgress.revalidate();      //ktory jest "poprawniejszy"? Oba dzialaja
            }
        };
        Thread balloonsProgressThread = new Thread(r);
        balloonsProgressThread.start();

        balloonsProgress.setPreferredSize(new Dimension(600, 50));
        balloonsProgress.setString("Number of balloons");
        balloonsProgress.setStringPainted(true);
        add(balloonsProgress, BorderLayout.PAGE_END);

    }

}
