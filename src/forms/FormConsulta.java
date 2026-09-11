package forms;

import dao.ConsultaDAO;
import dominio.Consulta;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormConsulta extends JPanel {
    private JTable tablaConsultas;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    
    private JTextField txtProcedimiento, txtFechaHora, txtIdEliminar;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private ConsultaDAO consultaDAO;
    private DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FormConsulta() {
        consultaDAO = new ConsultaDAO();
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = {"ID", "Procedimiento", "Fecha y Hora"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaConsultas = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaConsultas);

        txtProcedimiento = new JTextField(20);
        // Dejamos una fecha de ejemplo en la caja de texto para guiar al usuario
        txtFechaHora = new JTextField(LocalDateTime.now().format(formateador), 12);
        txtIdEliminar = new JTextField(5);

        btnGuardar = new JButton("Registrar Consulta");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Consultas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Distribución en el panel superior
        gbc.gridx = 0; gbc.gridy = 0; panelSuperior.add(new JLabel("Procedimiento:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; panelSuperior.add(txtProcedimiento, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; panelSuperior.add(new JLabel("Fecha (AAAA-MM-DD HH:MM):"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.5; panelSuperior.add(txtFechaHora, gbc);

        // Barra inferior de acciones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4; gbc.weightx = 1.0;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtProcedimiento.getText().trim().isEmpty() || txtFechaHora.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String procedimiento = txtProcedimiento.getText().trim();
                // Parsea de forma segura el texto de la caja a LocalDateTime
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim(), formateador);

                Consulta c = new Consulta(0, procedimiento, fechaHora);
                consultaDAO.guardar(c);
                JOptionPane.showMessageDialog(this, "¡Consulta registrada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Respete el formato: AAAA-MM-DD HH:MM\nEjemplo: 2026-09-15 14:30", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de comunicación con MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escriba el ID de la consulta que desea eliminar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar la consulta con ID " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    boolean borrado = consultaDAO.eliminar(id);
                    if (borrado) {
                        JOptionPane.showMessageDialog(this, "Consulta eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
            List<Consulta> lista = consultaDAO.listar();
            for (Consulta c : lista) {
                Object[] fila = {
                    c.getId(), 
                    c.getProcedimiento(), 
                    c.getFechaHora().format(formateador) // Formatea la fecha para mostrarla limpia en la JTable
                };
                modeloTabla.addRow(fila);
            }
        } catch (SQLException ex) {
            System.err.println("Error automático al refrescar JTable de consultas: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtProcedimiento.setText("");
        txtFechaHora.setText(LocalDateTime.now().format(formateador));
        txtIdEliminar.setText("");
    }
}
