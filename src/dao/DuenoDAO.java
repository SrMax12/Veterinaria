package dao;

import conexion.ConexionBD;
import dominio.Dueno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DuenoDAO {
    public int guardar(Dueno d) throws SQLException {
        String sql = "INSERT INTO dueño (nombre, ci, telefono, email) VALUES (?, ?, ?, ?)";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getCi());
            ps.setString(3, d.getTelefono());
            ps.setString(4, d.getEmail());
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

    public List<Dueno> listar() throws SQLException {
        String sql = "SELECT id, nombre, ci, telefono, email FROM dueño";
        List<Dueno> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Dueno(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("ci"),
                        rs.getString("telefono"),
                        rs.getString("email")));
            }
        }
        return lista;
    }

    public boolean actualizar(Dueno d) throws SQLException {
        String sql = "UPDATE dueño SET nombre = ?, ci = ?, telefono = ?, email = ? WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getCi());
            ps.setString(3, d.getTelefono());
            ps.setString(4, d.getEmail());
            ps.setInt(5, d.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM dueño WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
