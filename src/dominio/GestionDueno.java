package dominio;

import dao.DuenoDAO;
import java.util.ArrayList;
import java.util.List;

public class GestionDueno {
    private DuenoDAO duenoDAO = new DuenoDAO();

    public int guardar(Dueno dueno) {
        try {
            return duenoDAO.guardar(dueno);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Dueno> listar() {
        try {
            return duenoDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Dueno dueno) {
        try {
            return duenoDAO.actualizar(dueno);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return duenoDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
