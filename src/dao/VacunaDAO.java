package dao;

import conexion.ConexionBD;
import dominio.Vacuna;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacunaDAO {
public int guardar(Vacuna v) throws SQLException {
String sql = "INSERT INTO vacunas (tipo, fecha_hora, vencimiento) VALUES (?, ?, ?)";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, v.getTipo());
ps.setTimestamp(2, Timestamp.valueOf(v.getFechaHora()));
ps.setDate(3, Date.valueOf(v.getVencimiento()));
int filas = ps.executeUpdate();
if (filas == 0) return -1;
try (ResultSet rs = ps.getGeneratedKeys()) {
if (rs.next()) return rs.getInt(1);
}
return -1;
}
}

public List<Vacuna> listar() throws SQLException {
String sql = "SELECT id, tipo, fecha_hora, vencimiento FROM vacunas";
List<Vacuna> lista = new ArrayList<>();
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql);
ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
lista.add(new Vacuna(
rs.getInt("id"),
rs.getString("tipo"),
rs.getTimestamp("fecha_hora").toLocalDateTime(),
rs.getDate("vencimiento").toLocalDate()
));
}
}
return lista;
}

public boolean actualizar(Vacuna v) throws SQLException {
String sql = "UPDATE vacunas SET tipo = ?, fecha_hora = ?, vencimiento = ? WHERE id = ?";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setString(1, v.getTipo());
ps.setTimestamp(2, Timestamp.valueOf(v.getFechaHora()));
ps.setDate(3, Date.valueOf(v.getVencimiento()));
ps.setInt(4, v.getId());
return ps.executeUpdate() > 0;
}
}

public boolean eliminar(int id) throws SQLException {
String sql = "DELETE FROM vacunas WHERE id = ?";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setInt(1, id);
return ps.executeUpdate() > 0;
}
}
}
