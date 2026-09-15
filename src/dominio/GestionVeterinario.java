package dominio;

import dao.VeterinarioDAO;
import java.util.ArrayList;
import java.util.List;

public class GestionVeterinario {
    private VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    public int guardar(Veterinario v) {
        try {
            return veterinarioDAO.guardar(v);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Veterinario> listar() {
        try {
            return veterinarioDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Veterinario v) {
        try {
            return veterinarioDAO.actualizar(v);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return veterinarioDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
