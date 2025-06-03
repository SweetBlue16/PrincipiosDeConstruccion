package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public abstract class Entregable {
    protected String nombreArchivo;
    protected LocalDate fechaEntregado;
    protected LocalDate fechaRevisado;
    protected int puntajeObtenido;
    protected String comentario;

    public Entregable() {
    }

    public Entregable(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                     int puntajeObtenido, String comentario) {
        this.nombreArchivo = nombreArchivo;
        this.fechaEntregado = fechaEntregado;
        this.fechaRevisado = fechaRevisado;
        this.puntajeObtenido = puntajeObtenido;
        this.comentario = comentario;
    }

    // Getters y Setters comunes
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public LocalDate getFechaEntregado() {
        return fechaEntregado;
    }

    public void setFechaEntregado(LocalDate fechaEntregado) {
        this.fechaEntregado = fechaEntregado;
    }

    public LocalDate getFechaRevisado() {
        return fechaRevisado;
    }

    public void setFechaRevisado(LocalDate fechaRevisado) {
        this.fechaRevisado = fechaRevisado;
    }

    public int getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(int puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return nombreArchivo;
    }
}