package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

/**
 * Autor: Todos
 * Fecha de creación: 03/06/2025
 * Descripción: Representa las entregas de
 * reportes mensuales a las que sube el estudiante
 * sus archivos dentro del sistema.
 */
public class EntregaReporte extends Entrega {
    private int numeroReporte;

    public EntregaReporte() {
    }

    public EntregaReporte(int id, LocalDate fechaInicio, LocalDate fechaFin, 
                        int puntaje, int numeroReporte) {
        super(id, fechaInicio, fechaFin, puntaje);
        this.numeroReporte = numeroReporte;
    }

    public int getNumeroReporte() {
        return numeroReporte;
    }

    public void setNumeroReporte(int numeroReporte) {
        this.numeroReporte = numeroReporte;
    }

    @Override
    public String toString() {
        return "Reporte #" + numeroReporte + " (" + getFechaInicio() + " a " + getFechaFin() + ")";
    }
}