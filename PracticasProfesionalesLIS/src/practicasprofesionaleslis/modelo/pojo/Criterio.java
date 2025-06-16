/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practicasprofesionaleslis.modelo.pojo;

/**
 *
 * @author acrca
 */
public class Criterio {
    
    private int id;
    private int idEvaluacion;
    private String criterio;
    private double calificacion;

    public Criterio() {
    }

    public Criterio(int idEvaluacion, String criterio, double calificacion) {
        this.idEvaluacion = idEvaluacion;
        this.criterio = criterio;
        this.calificacion = calificacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return criterio + ": " + calificacion;
    }
    
    
}
