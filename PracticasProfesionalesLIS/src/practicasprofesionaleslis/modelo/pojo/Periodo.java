package practicasprofesionaleslis.modelo.pojo;

/**
 * Autor: Todos
 * Fecha de creación: 27/05/2025
 * Descripción: Representa el periodo escolar
 * de las prácticas profesionales (FEB-JUL o
 * AGO-ENE)
 */
public class Periodo {
    private int id;
    private String nombrePeriodo;
    private String fechaInicio;
    private String fechaFin;

    public Periodo() {
    }

    public Periodo(int id, String nombrePeriodo, String fechaInicio, String fechaFin) {
        this.id = id;
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
