package dao;

import conexion.ConexionBD;
import dominio.Mascota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {
public int guardar(Mascota m) throws SQLException {
String sql = "INSERT INTO mascotas (nombre, edad, especie, raza, peso, genero) VALUES (?, ?, ?, ?, ?, ?)";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, m.getNombre());
ps.setInt(2, m.getEdad());
ps.setString(3, m.getEspecie());
ps.setString(4, m.getRaza());
ps.setDouble(5, m.getPeso());
ps.setString(6, m.getGenero());
int filas = ps.executeUpdate();
if (filas == 0) return -1;
try (ResultSet rs = ps.getGeneratedKeys()) {
if (rs.next()) return rs.getInt(1);
}
return -1;
}
}

public List<Mascota> listar() throws SQLException {
String sql = "SELECT id, nombre, edad, especie, raza, peso, genero FROM mascotas";
List<Mascota> lista = new ArrayList<>();
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql);
ResultSet rs = ps.executeQuery()) {
while (rs.next()) {
lista.add(new Mascota(
rs.getInt("id"),
rs.getString("nombre"),
rs.getInt("edad"),
rs.getString("especie"),
rs.getString("raza"),
rs.getDouble("peso"),
rs.getString("genero")
));
}
}
return lista;
}

public boolean actualizar(Mascota m) throws SQLException {
String sql = "UPDATE mascotas SET nombre = ?, edad = ?, especie = ?, raza = ?, peso = ?, genero = ? WHERE id = ?";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setString(1, m.getNombre());
ps.setInt(2, m.getEdad());
ps.setString(3, m.getEspecie());
ps.setString(4, m.getRaza());
ps.setDouble(5, m.getPeso());
ps.setString(6, m.getGenero());
ps.setInt(7, m.getId());
return ps.executeUpdate() > 0;
}
}

public boolean eliminar(int id) throws SQLException {
String sql = "DELETE FROM mascotas WHERE id = ?";
try (Connection cn = ConexionBD.obtenerConexion();
PreparedStatement ps = cn.prepareStatement(sql)) {
ps.setInt(1, id);
return ps.executeUpdate() > 0;
}
}
}
