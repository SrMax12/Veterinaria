package forms;

import dao.DuenoDAO;
import dominio.Dueno;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormDueno extends JPanel {
    private JTable tablaDuenos;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    
    private JTextField txtNombre, txtCi, txtTelefono, txtEmail, txtIdEliminar;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private DuenoDAO duenoDAO;

    public FormDueno() {
        duenoDAO = new DuenoDAO();
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = {"ID", "Nombre", "CI", "Teléfono", "Email"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaDuenos = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaDuenos);

        txtNombre = new JTextField(12);
        txtCi = new JTextField(10);
        txtTelefono = new JTextField(10);
        txtEmail = new JTextField(15);
        txtIdEliminar = new JTextField(5);

        btnGuardar = new JButton("Registrar Dueño");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Dueños"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Distribución horizontal de campos
        gbc.gridx = 0; gbc.gridy = 0; panelSuperior.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panelSuperior.add(txtNombre, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Cédula (CI):"), gbc);
        gbc.gridx = 3; panelSuperior.add(txtCi, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelSuperior.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1; panelSuperior.add(txtTelefono, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3; gbc.gridwidth = 3; panelSuperior.add(txtEmail, gbc);

        // Barra inferior de acciones (botones y baja)
        gbc.gridwidth = 1; // Reseteo de ancho
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 6;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtCi.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios (Nombre y CI).", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String nombre = txtNombre.getText().trim();
                String ci = txtCi.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String email = txtEmail.getText().trim();

                Dueno d = new Dueno(0, nombre, ci, telefono, email);
                duenoDAO.guardar(d);
                JOptionPane.showMessageDialog(this, "¡Dueño registrado con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de comunicación con MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escriba el ID del dueño que desea eliminar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar al dueño con ID " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    boolean borrado = duenoDAO.eliminar(id);
                    if (borrado) {
                        JOptionPane.showMessageDialog(this, "Dueño eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        txtIdEliminar.setText("");
                        cargarDatos();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró ningún registro con ese ID.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID de eliminación debe ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Fallo al procesar la baja en MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<Dueno> lista = duenoDAO.listar();
            for (Dueno d : lista) {
                Object[] fila = {d.getId(), d.getNombre(), d.getCi(), d.getTelefono(), d.getEmail()};
                modeloTabla.addRow(fila);
            }
        } catch (SQLException ex) {
            System.err.println("Error automático al refrescar JTable de dueños: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtCi.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtIdEliminar.setText("");
    }
}
