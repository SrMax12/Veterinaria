package forms;

import dao.MascotaDAO;
import dominio.Mascota;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormMascota extends JPanel {
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    
    private JTextField txtNombre, txtEdad, txtEspecie, txtRaza, txtPeso, txtGenero, txtIdEliminar;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private MascotaDAO mascotaDAO;

    public FormMascota() {
        mascotaDAO = new MascotaDAO();
        // Diseño principal: La tabla va al centro y el formulario arriba estirado
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarComponentes();
        organizarDiseño();
        configurarAcciones();
        cargarDatos();
    }

    private void inicializarComponentes() {
        // Configurar la grilla de datos (no editable)
        String[] columnas = {"ID", "Nombre", "Edad", "Especie", "Raza", "Peso", "Género"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaMascotas = new JTable(modeloTabla);
        scrollTabla = new JScrollPane(tablaMascotas);

        // Campos de texto
        txtNombre = new JTextField(10);
        txtEdad = new JTextField(5);
        txtEspecie = new JTextField(10);
        txtRaza = new JTextField(10);
        txtPeso = new JTextField(5);
        txtGenero = new JTextField(10);
        txtIdEliminar = new JTextField(5);

        // Botones estilo Dashboard
        btnGuardar = new JButton("Registrar Mascota");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
    }

    private void organizarDiseño() {
        // Panel superior para campos distribuidos horizontalmente (aprovecha el ancho)
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel de Control - Mascotas"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1 de campos
        gbc.gridx = 0; gbc.gridy = 0; panelSuperior.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1; panelSuperior.add(txtNombre, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 3; panelSuperior.add(txtEdad, gbc);
        gbc.gridx = 4; panelSuperior.add(new JLabel("Especie:"), gbc);
        gbc.gridx = 5; panelSuperior.add(txtEspecie, gbc);

        // Fila 2 de campos
        gbc.gridx = 0; gbc.gridy = 1; panelSuperior.add(new JLabel("Raza:"), gbc);
        gbc.gridx = 1; panelSuperior.add(txtRaza, gbc);
        gbc.gridx = 2; panelSuperior.add(new JLabel("Peso (Kg):"), gbc);
        gbc.gridx = 3; panelSuperior.add(txtPeso, gbc);
        gbc.gridx = 4; panelSuperior.add(new JLabel("Género:"), gbc);
        gbc.gridx = 5; panelSuperior.add(txtGenero, gbc);

        // Barra de herramientas inferior dentro del panel superior (Botones y remoción)
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

        // Ensamblar en el JPanel base
        this.add(panelSuperior, BorderLayout.NORTH);
        this.add(scrollTabla, BorderLayout.CENTER);
    }

    private void configurarAcciones() {
        // Lógica del botón guardar blindada contra campos vacíos y errores numéricos
        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtEdad.getText().trim().isEmpty() || txtPeso.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor complete los campos obligatorios (Nombre, Edad, Peso).", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String nombre = txtNombre.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim());
                String especie = txtEspecie.getText().trim();
                String raza = txtRaza.getText().trim();
                double peso = Double.parseDouble(txtPeso.getText().trim());
                String genero = txtGenero.getText().trim();

                if (edad < 0 || peso <= 0) {
                    JOptionPane.showMessageDialog(this, "La edad y el peso deben ser valores positivos.", "Datos Inválidos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Mascota m = new Mascota(0, nombre, edad, especie, raza, peso, genero);
                mascotaDAO.guardar(m);
                JOptionPane.showMessageDialog(this, "¡Mascota registrada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Asegúrese de ingresar números válidos en Edad y Peso.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error de comunicación con MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Lógica de eliminación segura con cartel de confirmación explícita
        btnEliminar.addActionListener(e -> {
            if (txtIdEliminar.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escriba el ID de la mascota que desea eliminar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtIdEliminar.getText().trim());
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar de forma permanente la mascota con ID " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    boolean borrado = mascotaDAO.eliminar(id);
                    if (borrado) {
                        JOptionPane.showMessageDialog(this, "Mascota eliminada de la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        txtIdEliminar.setText("");
                        cargarDatos();
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró ningún registro con el ID especificado.", "No Encontrado", JOptionPane.WARNING_MESSAGE);
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
            List<Mascota> lista = mascotaDAO.listar();
            for (Mascota m : lista) {
                Object[] fila = {m.getId(), m.getNombre(), m.getEdad(), m.getEspecie(), m.getRaza(), m.getPeso(), m.getGenero()};
                modeloTabla.addRow(fila);
            }
        } catch (SQLException ex) {
            System.err.println("Error automático al refrescar JTable de mascotas: " + ex.getMessage());
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
    }
}
