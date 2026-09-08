package dao;

import conexion.ConexionBD;
import dominio.Veterinario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {
    public int guardar(Veterinario v) throws SQLException {
        String sql = "INSERT INTO veterinarios (nombre, password, especialidad) VALUES (?, ?, ?)";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getPassword());
            ps.setString(3, v.getEspecialidad());
            int filas = ps.executeUpdate();
            if (filas == 0)
                return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next())
                    return rs.getInt(1);
            }
            return -1;
        }
    }

    public List<Veterinario> listar() throws SQLException {
        String sql = "SELECT id, nombre, password, especialidad FROM veterinarios";
        List<Veterinario> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Veterinario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("password"),
                        rs.getString("especialidad")));
            }
        }
        return lista;
    }

    public boolean actualizar(Veterinario v) throws SQLException {
        String sql = "UPDATE veterinarios SET nombre = ?, password = ?, especialidad = ? WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getPassword());
            ps.setString(3, v.getEspecialidad());
            ps.setInt(4, v.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM veterinarios WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
