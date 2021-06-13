import javax.swing.*;
import java.awt.*;

public class MyJFrame
extends JFrame {

    public MyJFrame(){

        setLayout(new BorderLayout());

        FactoryPanel factoryPanel = new FactoryPanel();
        add(factoryPanel, BorderLayout.LINE_START);

        StoragePanel storagePanel = new StoragePanel();
        add(storagePanel, BorderLayout.CENTER);

        TransportPanel transportPanel = new TransportPanel();
        add(transportPanel, BorderLayout.LINE_END);


        setTitle("s22472 - Baloon factory project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setPreferredSize(new Dimension(1400, 900));
        setResizable(false);    //Postaraj się dostosować aplikację tak, aby była "responsywna"
        pack();
    }
}
