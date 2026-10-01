package dao;

import conexion.ConexionBD;
import dominio.Vacuna;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacunaDAO {
    public int guardar(Vacuna v) throws SQLException {
        String sql = "INSERT INTO vacuna (tipo, fecha_hora, vencimiento, id_mascota, id_consulta) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(v.getFechaHora()));
            ps.setDate(3, Date.valueOf(v.getVencimiento()));
            ps.setInt(4, v.getId_mascota());
            ps.setInt(5, v.getId_consulta());
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

    public List<Vacuna> listar() throws SQLException {
        String sql = "SELECT id, tipo, fecha_hora, vencimiento, id_mascota, id_consulta FROM vacuna ORDER BY vencimiento ASC";
        List<Vacuna> lista = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Vacuna(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getDate("vencimiento").toLocalDate(),
                        rs.getInt("id_mascota"),
                        rs.getInt("id_consulta")));
            }
        }
        return lista;
    }

    public boolean actualizar(Vacuna v) throws SQLException {
        String sql = "UPDATE vacuna SET tipo = ?, fecha_hora = ?, vencimiento = ?, id_mascota = ?, id_consulta = ? WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, v.getTipo());
            ps.setTimestamp(2, Timestamp.valueOf(v.getFechaHora()));
            ps.setDate(3, Date.valueOf(v.getVencimiento()));
            ps.setInt(4, v.getId_mascota());
            ps.setInt(5, v.getId_consulta());
            ps.setInt(6, v.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM vacuna WHERE id = ?";
        try (Connection cn = ConexionBD.obtenerConexion();
                PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
