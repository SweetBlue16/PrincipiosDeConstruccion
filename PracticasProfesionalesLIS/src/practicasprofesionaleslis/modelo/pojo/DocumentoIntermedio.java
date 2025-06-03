package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoIntermedio extends Entregable {
    private String tipoDocumentoIntermedio;


    public DocumentoIntermedio() {
    }

    public DocumentoIntermedio(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                             String tipoDocumentoIntermedio, int puntajeObtenido, String comentario) {
        super(nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario);
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }

    public String getTipoDocumentoIntermedio() {
        return tipoDocumentoIntermedio;
    }

    public void setTipoDocumentoIntermedio(String tipoDocumentoIntermedio) {
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }
}