package forms;

import dominio.Consulta;
import dominio.GestionConsulta;
import dominio.GestionMascota;
import dominio.GestionVeterinario;
import dominio.Mascota;
import dominio.Veterinario;
import java.awt.*;
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
    private JComboBox<Mascota> comboMascotas;
    private JComboBox<Veterinario> comboVeterinarios;
    private JButton btnGuardar, btnEliminar, btnLimpiar;

    private GestionConsulta gestionConsulta;
    private GestionMascota gestionMascota;
    private GestionVeterinario gestionVeterinario;
    private DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public FormConsulta() {
        gestionConsulta = new GestionConsulta();
        gestionMascota = new GestionMascota();
        gestionVeterinario = new GestionVeterinario();

        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        cargarCombos();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        String[] columnas = { "ID", "Procedimiento", "Fecha y Hora", "ID Mascota", "ID Veterinario" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaConsultas = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaConsultas);

        txtProcedimiento = new JTextField(15);
        txtFechaHora = new JTextField(LocalDateTime.now().format(formateador), 12);
        txtIdEliminar = new JTextField(5);
        comboMascotas = new JComboBox<>();
        comboVeterinarios = new JComboBox<>();

        btnGuardar = new JButton("Registrar Consulta");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void cargarCombos() {
        comboMascotas.removeAllItems();
        for (Mascota m : gestionMascota.listar())
            comboMascotas.addItem(m);

        comboVeterinarios.removeAllItems();
        for (Veterinario v : gestionVeterinario.listar())
            comboVeterinarios.addItem(v);
    }

    private void organizarDiseño() {
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Consultas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelSuperior.add(new JLabel("Procedimiento:"), gbc);
        gbc.gridx = 1;
        panelSuperior.add(txtProcedimiento, gbc);
        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Fecha (AAAA-MM-DD HH:MM):"), gbc);
        gbc.gridx = 3;
        panelSuperior.add(txtFechaHora, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelSuperior.add(new JLabel("Paciente (Mascota):"), gbc);
        gbc.gridx = 1;
        panelSuperior.add(comboMascotas, gbc);
        gbc.gridx = 2;
        panelSuperior.add(new JLabel("Médico Veterinario:"), gbc);
        gbc.gridx = 3;
        panelSuperior.add(comboVeterinarios, gbc);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnLimpiar);
        panelAcciones.add(new JSeparator(JSeparator.VERTICAL));
        panelAcciones.add(new JLabel("ID a borrar:"));
        panelAcciones.add(txtIdEliminar);
        panelAcciones.add(btnEliminar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(12, 6, 6, 6);
        panelSuperior.add(panelAcciones, gbc);

        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        btnGuardar.addActionListener(e -> {
            if (txtProcedimiento.getText().trim().isEmpty() || comboMascotas.getSelectedItem() == null
                    || comboVeterinarios.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String procedimiento = txtProcedimiento.getText().trim();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim(), formateador);

                Mascota m = (Mascota) comboMascotas.getSelectedItem();
                Veterinario v = (Veterinario) comboVeterinarios.getSelectedItem();

                Consulta c = new Consulta(0, procedimiento, fechaHora, m.getId(), v.getId());
                int resultadoId = gestionConsulta.guardar(c);
                if (resultadoId != -1) {
                    JOptionPane.showMessageDialog(this, "¡Consulta agendada con ID " + resultadoId + "!");
                    limpiarCampos();
                    cargarDatos();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido (AAAA-MM-DD HH:MM).", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty())
                return;
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                if (gestionConsulta.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Consulta removida.");
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
        List<Consulta> lista = gestionConsulta.listar();
        for (Consulta c : lista) {
            Object[] fila = { c.getId(), c.getProcedimiento(), c.getFechaHora().format(formateador), c.getId_mascota(),
                    c.getId_veterinario() };
            modeloTabla.addRow(fila);
        }
    }

    private void limpiarCampos() {
        txtProcedimiento.setText("");
        txtFechaHora.setText(LocalDateTime.now().format(formateador));
        txtIdEliminar.setText("");
    }
}
