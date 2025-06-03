package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoFinal extends Entregable {
    private String tipoDocumentoFinal;

    public DocumentoFinal() {
    }

    public DocumentoFinal(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                        String tipoDocumentoFinal, int puntajeObtenido, String comentario) {
        super(nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario);
        this.tipoDocumentoFinal = tipoDocumentoFinal;
    }

    public String getTipoDocumentoFinal() {
        return tipoDocumentoFinal;
    }

    public void setTipoDocumentoFinal(String tipoDocumentoFinal) {
        this.tipoDocumentoFinal = tipoDocumentoFinal;
    }
}