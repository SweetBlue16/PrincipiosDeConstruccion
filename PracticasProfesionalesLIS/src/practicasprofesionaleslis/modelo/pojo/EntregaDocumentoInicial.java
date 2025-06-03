package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class EntregaDocumentoInicial extends Entrega {
    private String tipoDocumentoInicial;

    public EntregaDocumentoInicial(int idGenerado, LocalDate fechaInicio1, LocalDate fechaFin1, String tipoDocumento, int puntaje1) {
    }

    public EntregaDocumentoInicial(int id, LocalDate fechaInicio, LocalDate fechaFin, 
                                 int puntaje, String tipoDocumentoInicial) {
        super(id, fechaInicio, fechaFin, puntaje);
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }

    public String getTipoDocumentoInicial() {
        return tipoDocumentoInicial;
    }

    public void setTipoDocumentoInicial(String tipoDocumentoInicial) {
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }

    @Override
    public String toString() {
        return tipoDocumentoInicial + " (" + getFechaInicio() + " a " + getFechaFin() + ")";
    }
}