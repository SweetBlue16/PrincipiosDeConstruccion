package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;
import javax.validation.constraints.*;

public class EntregaReporte extends Entrega {
    @NotBlank(message = "Nombre no puede estar vacío")
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