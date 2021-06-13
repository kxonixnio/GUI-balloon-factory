import javax.swing.*;
import java.awt.*;

public class Transport
        extends JPanel {

    private boolean isTransportOpen;
    private final int transportID;
    private String currentStatus;
    private boolean isPaused;

    Transport(JPanel panelForTransports){

        isPaused = false;
        isTransportOpen = true;
        transportID = TransportCounter.getNumberOfTransports();
        currentStatus = "waiting...";

        /*
            Wstępne ustawienie layoutu
         */
        setPreferredSize(new Dimension(350, 80));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BorderLayout());

        /*
            Podział na upperHalf i lowerHalf
         */
        JPanel upperHalf = new JPanel();
        upperHalf.setLayout(new BorderLayout());
        upperHalf.add(new JPanel(), BorderLayout.LINE_START);

        JPanel lowerHalf = new JPanel();
        lowerHalf.setPreferredSize(new Dimension(350, 60));

        /*
            status Label nieustannie aktualizuje informację o statusie transportu
         */
        JLabel statusLabel = new JLabel();
        Runnable t = () -> {
            while(true) {
                statusLabel.setText("Transport: " + transportID + " status: " + currentStatus);
            }
        };
        Thread statusThread = new Thread(t);
        statusThread.start();

        upperHalf.add(statusLabel, BorderLayout.CENTER);

        /*
            JProgressBar pokazuje jedną z informacji:
            -liczba załadowanych balonów
            -odliczanie do końca finalizowania dostawy
         */
        JProgressBar loadedBalloonsProgress = new JProgressBar(0, 10);
        loadedBalloonsProgress.setPreferredSize(new Dimension(300, 15));
        loadedBalloonsProgress.setString("0/10");
        loadedBalloonsProgress.setStringPainted(true);
        lowerHalf.add(loadedBalloonsProgress, BorderLayout.CENTER);

        /*
            Przycisk rozpoczynający całą procedurę transportu
            1. Zmiana statusu na loading
            2. Usunięcie pierwotnego listenera na przycisku, zmiana tekstu na nim na "Pause" i dodanie funkcjonalności
            zmieniającej pole isPaused na true
            3. Pętla odpowiadająca za:
                -zaktualizowanie JProgressBara
                -usunięcie z kolejki najdłużej składowanego balonu i wyświetlenie stosownej informacji
                4. Jeżeli został wciśnięty przycisk sprecyzowany w punkcie 2 (Pause) to:
                    -dekrementujemy i (zatrzymując pętlę w "martwym punkcie")
                    -zmieniamy opis i funkcjonalność przycisku na "continue". Teraz przycisk po wciśnięciu zmienia wartość
                    isPaused na false tym samym "wyjmując" pętlę z "martwego punktu" ORAZ przywraca ten sam opis i te same
                    funkcjonalności przycisku co w punkcie 2 (Pause)
                5. Jeżeli nie ma balonów do pobrania pętla się dekrementuje zatrzymując się w "martwym punkcie" dopóki
                nie dostanie balonów
                6. Jeżeli zostanie załadowane 10 balonów zaczyna się odliczanie 10 sekund do finalizacji dostawy
                7. Dostawa również może zostać przerwana - powtórzone jest rozwiązanie z przyciskiem tak samo jak
                w punkcie 4
                8. Jeżeli dostawa przebiegła pomyślnie transport samoczynnie usuwa się po 3 sekundach
                9. Jeżeli transport zostanie w międzyczasie gdziekolwiek zamknięty, wartość i ustawiana jest na 11
                automatycznie kończąc działanie pętli
         */
        JButton StartJB = new JButton("Start");
        StartJB.setPreferredSize(new Dimension(150, 25));
        StartJB.addActionListener(
                (e) -> {
                    //1
                    System.out.println("Start!\n");
                    currentStatus = "loading...";

                    Runnable r = () -> {
                        //2
                        StartJB.removeActionListener(StartJB.getActionListeners()[0]);
                        StartJB.setText("Pause");

                        StartJB.addActionListener(
                                (f)->{
                                    System.out.println("Paused");
                                    isPaused = true;
                                }
                        );
                        //3
                        for(int i = 1; i <= 10; i++){

                            if(Storage.balloonQueue.size() >= 1 && !isPaused) {

                                loadedBalloonsProgress.setValue(i);
                                loadedBalloonsProgress.remove(this);
                                loadedBalloonsProgress.repaint();
                                loadedBalloonsProgress.revalidate();
                                loadedBalloonsProgress.setString( i + "/10");

                                System.out.println("Last loaded balloon:");
                                System.out.println("Balloon ID: " + Storage.balloonQueue.peek().getBalloonID());
                                System.out.println("Balloon color: " + Storage.balloonQueue.peek().baloonColor + "\n");

                                Storage.balloonQueue.poll();
                            }else if(isPaused){ //4
                                i--;
                                System.out.println("Paused");

                                StartJB.removeActionListener(StartJB.getActionListeners()[0]);
                                StartJB.setText("Continue");
                                StartJB.addActionListener(
                                        (f)->{
                                            System.out.println("Continued");
                                            isPaused = false;

                                            StartJB.removeActionListener(StartJB.getActionListeners()[0]);
                                            StartJB.setText("Pause");

                                            StartJB.addActionListener(
                                                    (k)->{
                                                        System.out.println("Paused");
                                                        isPaused = true;
                                                    }
                                            );
                                        }
                                );
                            }else{ //5
                                i--;
                                System.out.println("No balloons to load! Create some more!");
                            }

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }

                            if(i == 10){ //6
                                System.out.println("Balloons loaded");
                                currentStatus = "in delivery...";

                                for (int j = 1; j <= 10; j++) {
                                    if(!isPaused) {
                                        loadedBalloonsProgress.setValue(j);
                                        loadedBalloonsProgress.remove(this);
                                        loadedBalloonsProgress.repaint();
                                        loadedBalloonsProgress.revalidate();
                                        loadedBalloonsProgress.setString(j + "/10");
                                    }else{ //7
                                        j--;
                                        System.out.println("Paused");

                                        StartJB.removeActionListener(StartJB.getActionListeners()[0]);
                                        StartJB.setText("Continue");
                                        StartJB.addActionListener(
                                                (f)->{
                                                    System.out.println("Continued");
                                                    isPaused = false;

                                                    StartJB.removeActionListener(StartJB.getActionListeners()[0]);
                                                    StartJB.setText("Pause");

                                                    StartJB.addActionListener(
                                                            (k)->{
                                                                System.out.println("Paused");
                                                                isPaused = true;
                                                            }
                                                    );
                                                }
                                        );
                                    }
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException interruptedException) {
                                        interruptedException.printStackTrace();
                                    }
                                }

                                if(isTransportOpen) {
                                    System.out.println("Delivered");
                                }
                                currentStatus = "delivered!";

                                try {//8
                                    for (int j = 3; j > 0; j--) {
                                        System.out.println("Transport will be deleted in " + j);
                                        currentStatus = "Delete in " + j;
                                        Thread.sleep(1000);
                                    }

                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }

                                panelForTransports.remove(this);
                                panelForTransports.revalidate();
                                panelForTransports.repaint();
                                TransportCounter.decreasePanelForTransportsHeight();
                                panelForTransports.setPreferredSize(new Dimension(350, TransportCounter.getPanelForTransportsHeight()));
                            }

                            if(!isTransportOpen){ //9
                                i = 11;
                            }
                        }
                    };
                    Thread loadedBalloonsProgressThread = new Thread(r);
                    loadedBalloonsProgressThread.start();
                }
        );

        lowerHalf.add(StartJB, BorderLayout.LINE_START);

        /*
            Przycisk odpowiedzialny za usunięcie transportu
         */
        JButton StopJB = new JButton("Stop");
        StopJB.setPreferredSize(new Dimension(150, 25));
        StopJB.addActionListener(
                (e) -> {
                    System.out.println("Transport stopped\n");
                    panelForTransports.remove(this);
                    panelForTransports.revalidate();
                    panelForTransports.repaint();
                    TransportCounter.decreasePanelForTransportsHeight();
                    panelForTransports.setPreferredSize(new Dimension(350, TransportCounter.getPanelForTransportsHeight()));
                    isTransportOpen = false;
                }
        );
        lowerHalf.add(StopJB, BorderLayout.LINE_END);

        add(upperHalf, BorderLayout.PAGE_START);
        add(lowerHalf, BorderLayout.PAGE_END);
    }
}