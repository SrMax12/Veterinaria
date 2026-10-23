package forms;

import dominio.GestionVeterinario;
import dominio.Veterinario;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormVeterinario extends JPanel {
    private JTable tablaVeterinarios;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

    private JTextField txtNombre, txtPassword, txtEspecialidad, txtIdEliminar;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private GestionVeterinario gestionVeterinario; // Intermediario del profesor

    public FormVeterinario() {
        gestionVeterinario = new GestionVeterinario();
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = { "ID", "Nombre Médico", "Contraseña", "Especialidad" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVeterinarios = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaVeterinarios);

        txtNombre = new JTextField(15);
        txtPassword = new JTextField(15);
        txtEspecialidad = new JTextField(15);
        txtIdEliminar = new JTextField(5);

        btnGuardar = new JButton("Registrar Veterinario");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Cuerpo Médico"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Distribución horizontal simétrica con pesos equilibrados para evitar
        // compresión
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        panelSuperior.add(new JLabel("Nombre Completo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panelSuperior.add(txtNombre, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        panelSuperior.add(new JLabel("Contraseña Acceso:"), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        panelSuperior.add(txtPassword, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.0;
        panelSuperior.add(new JLabel("Especialidad:"), gbc);
        gbc.gridx = 5;
        gbc.weightx = 1.0;
        panelSuperior.add(txtEspecialidad, gbc);

        // Barra inferior de acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        // Reset de pesos para la barra de botones inferior
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios (Nombre y Contraseña).",
                        "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nombre = txtNombre.getText().trim();
            String password = txtPassword.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();

            Veterinario v = new Veterinario(0, nombre, password, especialidad);
            int resultadoId = gestionVeterinario.guardar(v); // Llama a la gestión, aislando SQLExceptions

            if (resultadoId != -1) {
                JOptionPane.showMessageDialog(this, "¡Médico registrado con éxito!", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el registro en MySQL.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escriba el ID del veterinario que desea eliminar.", "Campo Vacío",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                int respuesta = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que desea eliminar al médico con ID " + id + "?", "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (respuesta == JOptionPane.YES_OPTION) {
                    if (gestionVeterinario.eliminar(id)) {
                        JOptionPane.showMessageDialog(this, "Veterinario dado de baja.", "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);
                        txtIdEliminar.setText("");
                        cargarDatos();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró ningún registro con ese ID.",
                                "No Encontrado", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID de eliminación debe ser un número entero válido.",
                        "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Veterinario> lista = gestionVeterinario.listar(); // Consume la lista limpia sin try-catches visuales
        for (Veterinario v : lista) {
            Object[] fila = { v.getId(), v.getNombre(), v.getPassword(), v.getEspecialidad() };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPassword.setText("");
        txtEspecialidad.setText("");
        txtIdEliminar.setText("");
    }
}
