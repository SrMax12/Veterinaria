package dominio;

import dao.MascotaDAO;
import java.util.ArrayList;
import java.util.List;

public class GestionMascota {
    private MascotaDAO mascotaDAO = new MascotaDAO();

    public int guardar(Mascota mascota) {
        try {
            return mascotaDAO.guardar(mascota);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Mascota> listar() {
        try {
            return mascotaDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Mascota mascota) {
        try {
            return mascotaDAO.actualizar(mascota);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return mascotaDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
