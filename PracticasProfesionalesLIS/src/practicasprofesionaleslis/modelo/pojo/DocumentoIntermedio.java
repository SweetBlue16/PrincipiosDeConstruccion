package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoIntermedio extends Entregable {
    
    public enum TipoDocumentoIntermedio { REPORTEPARCIAL, PRESENTACION210HORAS, EVALUACIONPARCIALOV }
    
    private TipoDocumentoIntermedio tipoDocumentoIntermedio;

    public DocumentoIntermedio() {
    }

    public DocumentoIntermedio(int id, String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                             TipoDocumentoIntermedio tipoDocumentoIntermedio, int puntajeObtenido, String comentario, byte[] archivo) {
        super(id, nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario, archivo);
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }

    public TipoDocumentoIntermedio getTipoDocumentoIntermedio() {
        return tipoDocumentoIntermedio;
    }

    public void setTipoDocumentoIntermedio(TipoDocumentoIntermedio tipoDocumentoIntermedio) {
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }
}