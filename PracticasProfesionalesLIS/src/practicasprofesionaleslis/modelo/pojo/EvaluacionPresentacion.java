package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

/**
 * Autor: Todos
 * Fecha de creación: 14/06/2025
 * Descripción: Representa las evaluaciones que
 * hacen los profesores evaluadores o del cuerpo
 * colegiado a las presentaciones de los estudiantes.
 */
public class EvaluacionPresentacion {
    
    private int idEvaluacionPresentacion;
    private int numeroEvaluacion;
    private double calificacionFinal;
    private LocalDate fechaEvaluacion;
    private String comentario;
    private int idExpediente; 

    public EvaluacionPresentacion() {
    }

    public EvaluacionPresentacion(int idEvaluacionPresentacion, int numeroEvaluacion, double calificacionFinal, LocalDate fechaEvaluacion, String comentario, int idExpediente) {
        this.idEvaluacionPresentacion = idEvaluacionPresentacion;
        this.numeroEvaluacion = numeroEvaluacion;
        this.calificacionFinal = calificacionFinal;
        this.fechaEvaluacion = fechaEvaluacion;
        this.comentario = comentario;
        this.idExpediente = idExpediente;
    }
    
    public int getIdEvaluacionPresentacion() {
        return idEvaluacionPresentacion;
    }

    public void setIdEvaluacionPresentacion(int idEvaluacionPresentacion) {
        this.idEvaluacionPresentacion = idEvaluacionPresentacion;
    }

    public int getNumeroEvaluacion() {
        return numeroEvaluacion;
    }

    public void setNumeroEvaluacion(int numeroEvaluacion) {
        this.numeroEvaluacion = numeroEvaluacion;
    }

    public double getCalificacionFinal() {
        return calificacionFinal;
    }

    public void setCalificacionFinal(double calificacionFinal) {
        this.calificacionFinal = calificacionFinal;
    }

    public LocalDate getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(LocalDate fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(int idExpediente) {
        this.idExpediente = idExpediente;
    }
    
    @Override
    public String toString() {
        return "Presentación: " + numeroEvaluacion;
    }

    
}
