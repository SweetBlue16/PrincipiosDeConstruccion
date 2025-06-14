package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public abstract class Entregable {
    protected String nombreArchivo;
    protected LocalDate fechaEntregado;
    protected LocalDate fechaRevisado;
    protected int puntajeObtenido;
    protected String comentario;
    protected byte[] archivo;

    public Entregable() {
    }

    public Entregable(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                     int puntajeObtenido, String comentario, byte[] archivo) {
        this.nombreArchivo = nombreArchivo;
        this.fechaEntregado = fechaEntregado;
        this.fechaRevisado = fechaRevisado;
        this.puntajeObtenido = puntajeObtenido;
        this.comentario = comentario;
        this.archivo = archivo;
    }

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

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return nombreArchivo;
    }
}