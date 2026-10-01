package dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vacuna implements Serializable {
    private int id;
    private String tipo;
    private LocalDateTime fechaHora;
    private LocalDate vencimiento;
    private int id_mascota; // FOREIGN KEY
    private int id_consulta; // FOREIGN KEY

    public Vacuna(int id, String tipo, LocalDateTime fechaHora, LocalDate vencimiento, int id_mascota,
            int id_consulta) {
        this.id = id;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.vencimiento = vencimiento;
        this.id_mascota = id_mascota;
        this.id_consulta = id_consulta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public int getId_mascota() {
        return id_mascota;
    }

    public void setId_mascota(int id_mascota) {
        this.id_mascota = id_mascota;
    }

    public int getId_consulta() {
        return id_consulta;
    }

    public void setId_consulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }
}
