/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author acrca
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
