import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Factory
extends JPanel {

    private long counter;
    private boolean isFactoryOpen;
    private int factoryID;

    Factory(JPanel panelForFactories){

        isFactoryOpen = true;
        factoryID = FactoriesCounter.getIDForFactory();

        /*
            Wstępne ustawienie layoutu
         */
        setPreferredSize(new Dimension(350, 80));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BorderLayout());

        JPanel upperHalf = new JPanel();
        JPanel lowerHalf = new JPanel();

        upperHalf.setLayout(new BorderLayout());
        upperHalf.add(new JPanel(), BorderLayout.LINE_START);

        /*
            Panel oraz wątek odpowiadający za prezentowanie ID fabryki i ilości wyprodukowanych balonów
         */
        JLabel factoryLabel = new JLabel();
        upperHalf.add(factoryLabel, BorderLayout.CENTER);
        Runnable t = () -> {
            while(true) {
                factoryLabel.setText("Factory: " + factoryID + " counter: " + counter);
            }
        };
        Thread counterThread = new Thread(t);
        counterThread.start();

        /*
            Przycisk zamknięcia fabryki. Automatycznie aktualizuje obszar jaki zajmują fabryki.
            Korzysta z tego JScrollPane, który "obejmuje" panel, gdzie pojawiają się fabryki
         */
        JButton exitButton = new JButton("X");
        exitButton.addActionListener(
                (e)->{
                    System.out.println("Factory closed\n");
                    panelForFactories.remove(this);
                    panelForFactories.revalidate();
                    panelForFactories.repaint();
                    FactoriesCounter.decreasePanelForFactoriesHeight();
                    panelForFactories.setPreferredSize(new Dimension(350, FactoriesCounter.getPanelForFactoriesHeight()));
                    isFactoryOpen = false;
                }
        );
        upperHalf.add(exitButton, BorderLayout.LINE_END);

        /*
            JSlider odpowiadający za częstotliwość tworzenia balonów
            Dopóki fabryka jest otwarta "usypia" regularnie tworzenie balonów o wartość uzyskaną z JSlidera
            Kolor balonu jest losowany spośród jednego z 16 dostępnych (enumów)

         */
        JSlider slider = new JSlider(100, 3000, 3000);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(725);
        slider.setMajorTickSpacing(725);

        Random randomColor = new Random();

        Runnable r = () -> {
            while(isFactoryOpen) {

                try {
                    Thread.sleep(slider.getValue());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isFactoryOpen) {
                    if(Storage.balloonQueue.size() != 99){

                        Balloon newBalloon = new Balloon(BaloonColor.values()[randomColor.nextInt(16)]);
                        Storage.balloonQueue.offer(newBalloon);

                        System.out.println("Last produced balloon:");
                        System.out.println("Balloon ID: " + newBalloon.getBalloonID());
                        System.out.println("Balloon color: " + newBalloon.baloonColor + "\n");

                        counter += 1;
                    }
                }
            }
        };
        Thread createNewBalloons = new Thread(r);
        createNewBalloons.start();

        lowerHalf.add(slider);

        add(upperHalf, BorderLayout.PAGE_START);
        add(lowerHalf, BorderLayout.PAGE_END);
    }
}


