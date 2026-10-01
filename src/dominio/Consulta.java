package dominio;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Consulta implements Serializable {
    private int id;
    private String procedimiento;
    private LocalDateTime fechaHora;
    private int id_mascota; // FOREIGN KEY
    private int id_veterinario; // FOREIGN KEY

    public Consulta(int id, String procedimiento, LocalDateTime fechaHora, int id_mascota, int id_veterinario) {
        this.id = id;
        this.procedimiento = procedimiento;
        this.fechaHora = fechaHora;
        this.id_mascota = id_mascota;
        this.id_veterinario = id_veterinario;
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

    public int getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }

    public int getId_veterinario() {
        return id_veterinario;
    }

    public void setId_veterinario(int id_veterinario) {
        this.id_veterinario = id_veterinario;
    }
}
