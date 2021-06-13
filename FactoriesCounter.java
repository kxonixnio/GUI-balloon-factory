public class FactoriesCounter {

    private static int numberOfFactories = 0;
    private static int panelForFactoriesHeight = 80;

    public static int getIDForFactory() {
        numberOfFactories += 1;
        panelForFactoriesHeight += 81;  //+1, bo jest minimalna przerwa pomiędzy każdą fabryką
        return numberOfFactories;
    }

    public static int getPanelForFactoriesHeight() {
        return panelForFactoriesHeight;
    }

    public static void decreasePanelForFactoriesHeight() {
        panelForFactoriesHeight -= 81;
    }
}
