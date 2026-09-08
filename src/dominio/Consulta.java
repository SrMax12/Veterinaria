package dominio;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Consulta implements Serializable {
    private int id;
    private String procedimiento;
    private LocalDateTime fechaHora;

    public Consulta(int id, String procedimiento, LocalDateTime fechaHora) {
        this.id = id;
        this.procedimiento = procedimiento;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
