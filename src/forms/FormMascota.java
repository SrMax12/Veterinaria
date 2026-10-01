package forms;

import dominio.Dueno;
import dominio.GestionDueno;
import dominio.GestionMascota;
import dominio.Mascota;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormMascota extends JPanel {
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

    private JTextField txtNombre, txtEdad, txtEspecie, txtRaza, txtPeso, txtGenero, txtIdEliminar;
    private JComboBox<Dueno> comboDuenos; // Selector desplegable inteligente
    private JButton btnGuardar, btnEliminar, btnLimpiar;

    private GestionMascota gestionMascota;
    private GestionDueno gestionDueno;

    public FormMascota() {
        gestionMascota = new GestionMascota();
        gestionDueno = new GestionDueno();

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        cargarComboDuenos(); // Llena el combo antes de dibujar la pantalla
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = { "ID", "Nombre", "Edad", "Especie", "Raza", "Peso", "Género", "ID Dueño" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaMascotas = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaMascotas);

        txtNombre = new JTextField(10);
        txtEdad = new JTextField(5);
        txtEspecie = new JTextField(10);
        txtRaza = new JTextField(10);
        txtPeso = new JTextField(5);
        txtGenero = new JTextField(10);
        txtIdEliminar = new JTextField(5);
        comboDuenos = new JComboBox<>();

        btnGuardar = new JButton("Registrar Mascota");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void cargarComboDuenos() {
        comboDuenos.removeAllItems();
        List<Dueno> lista = gestionDueno.listar();
        for (Dueno d : lista) {
            comboDuenos.addItem(d); // Inyecta el objeto dueño completo
        }
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Mascotas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtNombre, gbc);
        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 3;
        panelSuperior.add(txtEdad, gbc);
        gbc.gridx = 4;
        panelSuperior.add(new JLabel("Especie:"), gbc);
        gbc.gridx = 5;
        panelSuperior.add(txtEspecie, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(new JLabel("Raza:"), gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtRaza, gbc);
        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Peso (Kg):"), gbc);
        gbc.gridx = 3;
        panelSuperior.add(txtPeso, gbc);
        gbc.gridx = 4;
        panelSuperior.add(new JLabel("Género:"), gbc);
        gbc.gridx = 5;
        panelSuperior.add(txtGenero, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelSuperior.add(new JLabel("Dueño Responsable:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 5;
        panelSuperior.add(comboDuenos, gbc);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || comboDuenos.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.", "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String nombre = txtNombre.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim());
                String especie = txtEspecie.getText().trim();
                String raza = txtRaza.getText().trim();
                double peso = Double.parseDouble(txtPeso.getText().trim());
                String genero = txtGenero.getText().trim();

                // Extrae dinámicamente el objeto seleccionado del combo para heredar su ID real
                Dueno d = (Dueno) comboDuenos.getSelectedItem();

                Mascota m = new Mascota(0, nombre, edad, especie, raza, peso, genero, d.getId());
                int resultadoId = gestionMascota.guardar(m);

                if (resultadoId != -1) {
                    JOptionPane.showMessageDialog(this, "¡Mascota registrada con ID " + resultadoId + "!");
                    limpiarCampos();
                    cargarDatos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Edad y Peso deben ser numéricos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty())
                return;
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar mascota con ID " + id + "?", "Confirmar",
                        JOptionPane.YES_NO_OPTION);
                if (conf == JOptionPane.YES_OPTION) {
                    if (gestionMascota.eliminar(id)) {
                        JOptionPane.showMessageDialog(this, "Mascota eliminada.");
                        txtIdEliminar.setText("");
                        cargarDatos();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Mascota> lista = gestionMascota.listar();
        for (Mascota m : lista) {
            Object[] fila = { m.getId(), m.getNombre(), m.getEdad(), m.getEspecie(), m.getRaza(), m.getPeso(),
                    m.getGenero(), m.getId_dueño() };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtEdad.setText("");
        txtEspecie.setText("");
        txtRaza.setText("");
        txtPeso.setText("");
        txtGenero.setText("");
        txtIdEliminar.setText("");
        if (comboDuenos.getItemCount() > 0)
            comboDuenos.setSelectedIndex(0);
    }
}
