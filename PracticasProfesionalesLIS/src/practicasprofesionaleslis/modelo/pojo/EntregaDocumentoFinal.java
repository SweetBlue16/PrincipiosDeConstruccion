package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class EntregaDocumentoFinal extends Entrega {
    private String tipoDocumentoFinal;

    public EntregaDocumentoFinal(int aInt, LocalDate fechaInicio1, LocalDate fechaFin1, String tipoDocumento, int puntaje1) {
    }

    public EntregaDocumentoFinal(int id, LocalDate fechaInicio, LocalDate fechaFin, 
                               int puntaje, String tipoDocumentoFinal) {
        super(id, fechaInicio, fechaFin, puntaje);
        this.tipoDocumentoFinal = tipoDocumentoFinal;
    }

    public String getTipoDocumentoFinal() {
        return tipoDocumentoFinal;
    }

    public void setTipoDocumentoFinal(String tipoDocumentoFinal) {
        this.tipoDocumentoFinal = tipoDocumentoFinal;
    }

    @Override
    public String toString() {
        return tipoDocumentoFinal + " (" + getFechaInicio() + " a " + getFechaFin() + ")";
    }
}