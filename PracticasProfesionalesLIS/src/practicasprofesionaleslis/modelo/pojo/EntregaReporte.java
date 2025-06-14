package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

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