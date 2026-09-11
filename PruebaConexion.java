import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Prueba de conexion: abre la base de datos, ejecuta un SELECT
 * sobre la tabla duenos y muestra los registros encontrados.
 *
 * Compilar (desde la raiz del proyecto):
 * javac -encoding UTF-8 -cp "lib\mysql-connector-j-8.0.33.jar" -d bin
 * src\dominio\Consulta.java src\dominio\Dueno.java src\dominio\Mascota.java
 * src\dominio\Vacuna.java src\dominio\Veterinario.java
 * src\conexion\ConexionBD.java src\dao\ConsultaDAO.java
 * src\dao\DuenoDAO.java src\dao\MascotaDAO.java
 * src\dao\VacunaDAO.java src\dao\VeterinarioDAO.java
 * PruebaConexion.java
 *
 * Ejecutar:
 * java -cp "bin;lib\mysql-connector-j-8.0.33.jar" PruebaConexion
 *
 * Requisitos: MySQL (XAMPP) iniciado y veterinaria_db.sql importado.
 */
public class PruebaConexion {

    private static Connection obtenerConexion() throws Exception {
        Class<?> claseConexion = Class.forName("conexion.ConexionBD");
        return (Connection) claseConexion
                .getMethod("obtenerConexion")
                .invoke(null);
    }

    public static void main(String[] args) {
        String sql = "SELECT id, nombre, email, telefono, ci FROM dueño";

        // try-with-resources: cierra solo la conexion, el Statement y el ResultSet
        try (Connection cn = obtenerConexion();
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            System.out.println("Conexion OK a veterinaria\n");
            System.out.println("Registros en la tabla dueño:\n");

            int contador = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                String ci = rs.getString("ci");

                System.out.println("ID: " + id
                        + " | Nombre: " + nombre
                        + " | Email: " + email
                        + " | Telefono: " + telefono
                        + " | CI: " + ci);
                contador++;
            }

            if (contador == 0) {
                System.out.println("(La tabla duenos esta vacia)");
            } else {
                System.out.println("\nTotal: " + contador + " dueno(s)");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Revisa que MySQL (XAMPP) este iniciado y que hayas importado veterinaria.sql");
            e.printStackTrace();
        }
    }
}
