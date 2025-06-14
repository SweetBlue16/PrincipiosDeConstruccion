package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoIntermedio extends Entregable {
    
    public enum TipoDocumentoIntermedio { REPORTE_PARCIAL, PRESENTACION_210_HORAS, EVALUACION_PARCIAL_OV }
    
    private TipoDocumentoIntermedio tipoDocumentoIntermedio;

    public DocumentoIntermedio() {
    }

    public DocumentoIntermedio(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                             TipoDocumentoIntermedio tipoDocumentoIntermedio, int puntajeObtenido, String comentario, byte[] archivo) {
        super(nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario, archivo);
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }

    public TipoDocumentoIntermedio getTipoDocumentoIntermedio() {
        return tipoDocumentoIntermedio;
    }

    public void setTipoDocumentoIntermedio(TipoDocumentoIntermedio tipoDocumentoIntermedio) {
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }
}