package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
private static final String URL_LOCAL = "jdbc:mysql://localhost:3306/veterinaria?useSSL=false&serverTimezone=UTC";
private static final String USUARIO = "root";
private static final String CONTRASENA = "";
private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

public static Connection obtenerConexion() throws SQLException {
try {
Class.forName(DRIVER);
} catch (ClassNotFoundException e) {
throw new SQLException("Driver JDBC no encontrado", e);
}
return DriverManager.getConnection(URL_LOCAL, USUARIO, CONTRASENA);
}

public static void cerrarConexion(Connection conn) throws SQLException {
if (conn != null) {
conn.close();
}
}
}
