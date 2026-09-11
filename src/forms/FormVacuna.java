package forms;

import dao.VacunaDAO;
import dominio.Vacuna;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormVacuna extends JPanel {
    private JTable tablaVacunas;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    
    private JTextField txtTipo, txtFechaHora, txtVencimiento, txtIdEliminar;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private VacunaDAO vacunaDAO;
    private DateTimeFormatter fmtFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter fmtFechaSola = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FormVacuna() {
        vacunaDAO = new VacunaDAO();
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = {"ID", "Componente / Tipo", "Fecha Aplicación", "Vencimiento"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaVacunas = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaVacunas);

        txtTipo = new JTextField(12);
        txtFechaHora = new JTextField(LocalDateTime.now().format(fmtFechaHora), 12);
        txtVencimiento = new JTextField(LocalDate.now().plusYears(1).format(fmtFechaSola), 10);
        txtIdEliminar = new JTextField(5);

        btnGuardar = new JButton("Registrar Vacuna");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Inmunizaciones (Vacunas)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila horizontal de campos
        gbc.gridx = 0; gbc.gridy = 0; panelSuperior.add(new JLabel("Vacuna / Tipo:"), gbc);
        gbc.gridx = 1; panelSuperior.add(txtTipo, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Aplicación (AAAA-MM-DD HH:MM):"), gbc);
        gbc.gridx = 3; panelSuperior.add(txtFechaHora, gbc);
        gbc.gridx = 4; panelSuperior.add(new JLabel("Vence (AAAA-MM-DD):"), gbc);
        gbc.gridx = 5; panelSuperior.add(txtVencimiento, gbc);

        // Barra inferior de botones
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 6;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtTipo.getText().trim().isEmpty() || txtFechaHora.getText().trim().isEmpty() || txtVencimiento.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete todos los campos obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String tipo = txtTipo.getText().trim();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim(), fmtFechaHora);
                LocalDate vencimiento = LocalDate.parse(txtVencimiento.getText().trim(), fmtFechaSola);

                Vacuna v = new Vacuna(0, tipo, fechaHora, vencimiento);
                vacunaDAO.guardar(v);
                JOptionPane.showMessageDialog(this, "¡Vacuna registrada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha u hora incorrecto.\nAplicación: AAAA-MM-DD HH:MM\nVencimiento: AAAA-MM-DD", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de comunicación con MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escriba el ID del registro que desea eliminar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar de forma permanente la vacuna con ID " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    boolean borrado = vacunaDAO.eliminar(id);
                    if (borrado) {
                        JOptionPane.showMessageDialog(this, "Registro de vacuna eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
            List<Vacuna> lista = vacunaDAO.listar();
            for (Vacuna v : lista) {
                Object[] fila = {
                    v.getId(), 
                    v.getTipo(), 
                    v.getFechaHora().format(fmtFechaHora), 
                    v.getVencimiento().format(fmtFechaSola)
                };
                modeloTabla.addRow(fila);
            }
        } catch (SQLException ex) {
            System.err.println("Error automático al refrescar JTable de vacunas: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtTipo.setText("");
        txtFechaHora.setText(LocalDateTime.now().format(fmtFechaHora));
        txtVencimiento.setText(LocalDate.now().plusYears(1).format(fmtFechaSola));
        txtIdEliminar.setText("");
    }
}
