public class TransportCounter {

    private static int numberOfTransports = 0;
    private static int panelForTransportsHeight = 80;

    public static int getNumberOfTransports() {
        numberOfTransports += 1;
        panelForTransportsHeight += 81;  //+1, bo jest minimalna przerwa pomiędzy każdym transportem
        return numberOfTransports;
    }

    public static int getPanelForTransportsHeight() {
        return panelForTransportsHeight;
    }

    public static void decreasePanelForTransportsHeight() {
        panelForTransportsHeight -= 81;
    }
}