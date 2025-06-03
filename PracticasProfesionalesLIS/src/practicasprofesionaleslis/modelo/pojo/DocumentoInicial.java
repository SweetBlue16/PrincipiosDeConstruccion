package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoInicial extends Entregable {
    private String tipoDocumentoInicial;

    public DocumentoInicial() {
    }

    public DocumentoInicial(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                          String tipoDocumentoInicial, int puntajeObtenido, String comentario) {
        super(nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario);
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }

    public String getTipoDocumentoInicial() {
        return tipoDocumentoInicial;
    }

    public void setTipoDocumentoInicial(String tipoDocumentoInicial) {
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }
}