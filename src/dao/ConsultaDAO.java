package dao;

import conexion.ConexionBD;
import dominio.Consulta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
public int guardar(Consulta c) throws SQLException {
String sql = "INSERT INTO consultas (procedimiento, fecha_hora) VALUES (?, ?)";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, c.getProcedimiento());
ps.setTimestamp(2, Timestamp.valueOf(c.getFechaHora()));
int filas = ps.executeUpdate();
if (filas == 0) return -1;
try (ResultSet rs = ps.getGeneratedKeys()) {
if (rs.next()) return rs.getInt(1);
}
return -1;
}
}

public List<Consulta> listar() throws SQLException {
String sql = "SELECT id, procedimiento, fecha_hora FROM consultas";
List<Consulta> lista = new ArrayList<>();
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql);
ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
lista.add(new Consulta(
rs.getInt("id"),
rs.getString("procedimiento"),
rs.getTimestamp("fecha_hora").toLocalDateTime()
));
}
}
return lista;
}

public boolean actualizar(Consulta c) throws SQLException {
String sql = "UPDATE consultas SET procedimiento = ?, fecha_hora = ? WHERE id = ?";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setString(1, c.getProcedimiento());
ps.setTimestamp(2, Timestamp.valueOf(c.getFechaHora()));
ps.setInt(3, c.getId());
return ps.executeUpdate() > 0;
}
}

public boolean eliminar(int id) throws SQLException {
String sql = "DELETE FROM consultas WHERE id = ?";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setInt(1, id);
return ps.executeUpdate() > 0;
}
}
}
