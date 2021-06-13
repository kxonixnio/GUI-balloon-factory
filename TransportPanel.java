import javax.swing.*;
import java.awt.*;

public class TransportPanel
extends JPanel {

    TransportPanel(){
        /*
            Wstępne ustawienie layoutu
         */
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 900));
        setBackground(new Color(106, 104, 115));

        /*
            Panel gdzie umieszczane będą transporty
         */
        JPanel panelForTransports = new JPanel();
        panelForTransports.setBackground(Color.white);

        /*
            Panel "dekoracyjny"
         */
        JLabel description = new JLabel("Transport", SwingConstants.CENTER);
        description.setFont(new Font("Serif", Font.BOLD, 80));
        description.setPreferredSize(new Dimension(300, 100));
        add(description, BorderLayout.PAGE_START);

        /*
            "Wsadzenie" panelu dla transportów do JScrollPane
         */
        JScrollPane jsp = new JScrollPane(panelForTransports);
        add(jsp, BorderLayout.CENTER);

        /*
            Przycisk dodawania transportu
         */
        JButton JB = new JButton("Add");
        JB.setPreferredSize(new Dimension(300, 90));
        JB.addActionListener(
                (e) -> {
                    System.out.println("Transport created!\n");
                    panelForTransports.add(new Transport(panelForTransports));
                    panelForTransports.setPreferredSize(new Dimension(350, TransportCounter.getPanelForTransportsHeight()));
                    this.revalidate();
                }
        );
        add(JB, BorderLayout.PAGE_END);

    }


}
