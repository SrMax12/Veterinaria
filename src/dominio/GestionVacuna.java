package dominio;

import dao.VacunaDAO;
import java.util.ArrayList;
import java.util.List;

public class GestionVacuna {
    private VacunaDAO vacunaDAO = new VacunaDAO();

    public int guardar(Vacuna vacuna) {
        try {
            return vacunaDAO.guardar(vacuna);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Vacuna> listar() {
        try {
            return vacunaDAO.listar();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean actualizar(Vacuna vacuna) {
        try {
            return vacunaDAO.actualizar(vacuna);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return vacunaDAO.eliminar(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
