package dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Vacuna implements Serializable {
    private int id;
    private String tipo;
    private LocalDateTime fechaHora;
    private LocalDate vencimiento;

    public Vacuna(int id, String tipo, LocalDateTime fechaHora, LocalDate vencimiento) {
        this.id = id;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
        this.vencimiento = vencimiento;
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
}
