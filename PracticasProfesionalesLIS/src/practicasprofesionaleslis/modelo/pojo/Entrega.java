package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public abstract class Entrega {
    protected int id;
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected int puntaje;

    public Entrega() {
    }

    public Entrega(int id, LocalDate fechaInicio, LocalDate fechaFin, int puntaje) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.puntaje = puntaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "Entrega [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", puntaje=" + puntaje + "]";
    }
}