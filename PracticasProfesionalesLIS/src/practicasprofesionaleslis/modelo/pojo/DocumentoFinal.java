package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoFinal extends Entregable {
    
    public enum TipoDocumentoFinal { CARTALIBERACION, REPORTEFINAL, EVALUACIONOV, AUTOEVALUACION, CONSTANCIACUMPLIMIENTO, PRESENTACION420HORAS }
    
    private TipoDocumentoFinal tipoDocumentoFinal;

    public DocumentoFinal() {
    }

    public DocumentoFinal(int id, String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                        TipoDocumentoFinal tipoDocumentoFinal, int puntajeObtenido, String comentario, byte[] archivo) {
        super(id, nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario, archivo);
        this.tipoDocumentoFinal = tipoDocumentoFinal;
    }

    public TipoDocumentoFinal getTipoDocumentoFinal() {
        return tipoDocumentoFinal;
    }

    public void setTipoDocumentoFinal(TipoDocumentoFinal tipoDocumentoFinal) {
        this.tipoDocumentoFinal = tipoDocumentoFinal;
    }
}