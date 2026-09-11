package forms;

import java.awt.*;
import javax.swing.*;

public class FormMain extends JFrame {
    // Declaración de los componentes reales del Dashboard
    private JPanel PanelMenu;
    private JPanel PanelContenedor;
    private JButton BtnMascota;
    private JButton BtnDueño;
    private JButton BtnConsulta;
    private JButton BtnVacuna;
    private JButton BtnVeterinario;

    public FormMain() {
        // Configuraciones de la ventana principal
        this.setTitle("Sistema de Gestión Veterinaria - Dashboard");
        this.setSize(1100, 650); // Tamaño ideal estirado para el panel derecho
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Centra la ventana en tu pantalla
        
        // El contenedor principal usa BorderLayout para dividir izquierda y derecha
        this.setLayout(new BorderLayout());

        inicializarComponentes();
        organizarDiseno();
        configurarNavegacion();
    }

    private void inicializarComponentes() {
        // Crear los contenedores
        PanelMenu = new JPanel();
        PanelContenedor = new JPanel(new BorderLayout());
        
        // Le damos un fondo gris claro al contenedor para saber que está vacío al inicio
        PanelContenedor.setBackground(new Color(240, 242, 245));
        PanelContenedor.add(new JLabel("Bienvenido al Sistema. Seleccione una opción del menú.", SwingConstants.CENTER));

        // Crear tus 5 JButtons de selección
        BtnMascota = new JButton("Mascotas");
        BtnDueño = new JButton("Dueños");
        BtnConsulta = new JButton("Consultas");
        BtnVacuna = new JButton("Vacunas");
        BtnVeterinario = new JButton("Veterinarios");

        // Estilizar un poco los botones para que se vean prolijos y grandes
        Dimension tamanoBoton = new Dimension(160, 40);
        BtnMascota.setPreferredSize(tamanoBoton);
        BtnDueño.setPreferredSize(tamanoBoton);
        BtnConsulta.setPreferredSize(tamanoBoton);
        BtnVacuna.setPreferredSize(tamanoBoton);
        BtnVeterinario.setPreferredSize(tamanoBoton);
    }

    private void organizarDiseno() {
        // El panel de la izquierda organiza los botones hacia abajo (GridLayout de 5 filas)
        PanelMenu.setLayout(new GridLayout(5, 1, 10, 10));
        PanelMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        PanelMenu.setBackground(new Color(45, 52, 71)); // Color oscuro elegante

        // Añadir botones al menú lateral
        PanelMenu.add(BtnMascota);
        PanelMenu.add(BtnDueño);
        PanelMenu.add(BtnConsulta);
        PanelMenu.add(BtnVacuna);
        PanelMenu.add(BtnVeterinario);

        // Ensamblar los dos bloques grandes en la ventana principal
        this.add(PanelMenu, BorderLayout.WEST);       // Menú a la izquierda
        this.add(PanelContenedor, BorderLayout.CENTER); // Formularios a la derecha expansiva
    }

    private void configurarNavegacion() {
        // Listeners asignados a tus variables para intercambiar los JPanels
        BtnMascota.addActionListener(e -> mostrarFormulario(new FormMascota()));
        BtnDueño.addActionListener(e -> mostrarFormulario(new FormDueno()));
        BtnConsulta.addActionListener(e -> mostrarFormulario(new FormConsulta()));
        BtnVacuna.addActionListener(e -> mostrarFormulario(new FormVacuna()));
        BtnVeterinario.addActionListener(e -> mostrarFormulario(new FormVeterinario()));
    }

    private void mostrarFormulario(JPanel formularioSecundario) {
        // Limpieza quirúrgica y repintado dinámico del lado derecho
        PanelContenedor.removeAll();
        PanelContenedor.add(formularioSecundario, BorderLayout.CENTER);
        PanelContenedor.revalidate();
        PanelContenedor.repaint();
    }

    // El método main para iniciar la aplicación completa
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormMain().setVisible(true);
        });
    }
}
