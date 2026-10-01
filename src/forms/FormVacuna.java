package forms;

import dominio.Consulta;
import dominio.GestionConsulta;
import dominio.GestionMascota;
import dominio.GestionVacuna;
import dominio.Mascota;
import dominio.Vacuna;
import java.awt.*;
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
    private JComboBox<Mascota> comboMascotas;
    private JComboBox<Consulta> comboConsultas;
    private JButton btnGuardar, btnEliminar, btnLimpiar;

    private GestionVacuna gestionVacuna;
    private GestionMascota gestionMascota;
    private GestionConsulta gestionConsulta;
    private DateTimeFormatter fmtFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private DateTimeFormatter fmtFechaSola = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FormVacuna() {
        gestionVacuna = new GestionVacuna();
        gestionMascota = new GestionMascota();
        gestionConsulta = new GestionConsulta();

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        cargarCombos();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = { "ID", "Vacuna", "Fecha Aplicación", "Vencimiento", "ID Mascota", "ID Consulta" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVacunas = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaVacunas);

        txtTipo = new JTextField(10);
        txtFechaHora = new JTextField(LocalDateTime.now().format(fmtFechaHora), 12);
        txtVencimiento = new JTextField(LocalDate.now().plusYears(1).format(fmtFechaSola), 10);
        txtIdEliminar = new JTextField(5);
        comboMascotas = new JComboBox<>();
        comboConsultas = new JComboBox<>();

        btnGuardar = new JButton("Registrar Vacuna");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void cargarCombos() {
        comboMascotas.removeAllItems();
        for (Mascota m : gestionMascota.listar())
            comboMascotas.addItem(m);

        comboConsultas.removeAllItems();
        for (Consulta c : gestionConsulta.listar())
            comboConsultas.addItem(c);
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Vacunas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(new JLabel("Tipo Vacuna:"), gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtTipo, gbc);
        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Aplicación:"), gbc);
        gbc.gridx = 3;
        panelSuperior.add(txtFechaHora, gbc);
        gbc.gridx = 4;
        panelSuperior.add(new JLabel("Vencimiento:"), gbc);
        gbc.gridx = 5;
        panelSuperior.add(txtVencimiento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelSuperior.add(new JLabel("Mascota:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelSuperior.add(comboMascotas, gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        panelSuperior.add(new JLabel("Asociar Consulta:"), gbc);
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        panelSuperior.add(comboConsultas, gbc);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtTipo.getText().trim().isEmpty() || comboMascotas.getSelectedItem() == null
                    || comboConsultas.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String tipo = txtTipo.getText().trim();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim(), fmtFechaHora);
                LocalDate vencimiento = LocalDate.parse(txtVencimiento.getText().trim(), fmtFechaSola);

                Mascota m = (Mascota) comboMascotas.getSelectedItem();
                Consulta c = (Consulta) comboConsultas.getSelectedItem();

                Vacuna v = new Vacuna(0, tipo, fechaHora, vencimiento, m.getId(), c.getId());
                int resultadoId = gestionVacuna.guardar(v);
                if (resultadoId != -1) {
                    JOptionPane.showMessageDialog(this, "¡Vacuna registrada con ID " + resultadoId + "!");
                    limpiarCampos();
                    cargarDatos();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error de formato de fecha.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty())
                return;
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                if (gestionVacuna.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Vacuna eliminada.");
                    txtIdEliminar.setText("");
                    cargarDatos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Vacuna> lista = gestionVacuna.listar();
        for (Vacuna v : lista) {
            Object[] fila = { v.getId(), v.getTipo(), v.getFechaHora().format(fmtFechaHora),
                    v.getVencimiento().format(fmtFechaSola), v.getId_mascota(), v.getId_consulta() };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtTipo.setText("");
        txtFechaHora.setText(LocalDateTime.now().format(fmtFechaHora));
        txtVencimiento.setText(LocalDate.now().plusYears(1).format(fmtFechaSola));
        txtIdEliminar.setText("");
    }
}
