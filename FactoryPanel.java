import javax.swing.*;
import java.awt.*;

public class FactoryPanel
extends JPanel{

    FactoryPanel(){

        /*
            Wstępne ustawianie layoutu
         */
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 900));
        setBackground(new Color(106, 104, 115));

        JPanel panelForFactories = new JPanel();
        panelForFactories.setBackground(Color.white);

        /*
            Panel "dekoracyjny"
         */
        JLabel description = new JLabel("Factories", SwingConstants.CENTER);
        description.setFont(new Font("Serif", Font.BOLD, 80));
        description.setPreferredSize(new Dimension(300, 100));
        add(description, BorderLayout.PAGE_START);

        /*
            "Wsadzenie" panelu dla fabryk do JScrollPane
         */
        JScrollPane jsp = new JScrollPane(panelForFactories);
        add(jsp, BorderLayout.CENTER);

        /*
            Przycisk dodawania fabryki
         */
        JButton addJB = new JButton("Add");
        addJB.setPreferredSize(new Dimension(300, 90));
        addJB.addActionListener(
                (e) -> {
                    System.out.println("Factory created!\n");
                    panelForFactories.add(new Factory(panelForFactories));
                    panelForFactories.setPreferredSize(new Dimension(350, FactoriesCounter.getPanelForFactoriesHeight()));
                    this.revalidate();
                }
        );
        add(addJB, BorderLayout.PAGE_END);
    }
}
