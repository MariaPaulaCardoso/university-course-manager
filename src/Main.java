public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.MenuPrincipalFrame frame = new view.MenuPrincipalFrame();
                frame.setVisible(true);
            }
        });
    }
}
