import forms.FormMain;
import javax.swing.SwingUtilities;

/**
 * Arranque basico nativo sin dependencias externas de librerias visuales.
 */
public class App {

    public static void main(String[] args) {
        try {
            // Lanza tu interfaz grafica directo en el hilo seguro de Swing
            SwingUtilities.invokeLater(() -> {
                FormMain dashboard = new FormMain();
                dashboard.setVisible(true);
            });
        } catch (Throwable t) {
            System.err.println("Error al inicializar la interfaz grafica:");
            t.printStackTrace();
            System.exit(1);
        }
    }
}
