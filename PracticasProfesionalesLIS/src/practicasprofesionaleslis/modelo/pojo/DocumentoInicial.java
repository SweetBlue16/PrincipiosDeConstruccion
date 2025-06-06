package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class DocumentoInicial extends Entregable {
    
    public enum TipoDocumentoInicial { CARTA_ACEPTACION, CONSTANCIA_SEGURO, CRONOGRAMA, HORARIO, OFICIO_ASIGNACION }
    
    private TipoDocumentoInicial tipoDocumentoInicial;

    public DocumentoInicial() {
    }

    public DocumentoInicial(String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                          TipoDocumentoInicial tipoDocumentoInicial, int puntajeObtenido, String comentario) {
        super(nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario);
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }

    public TipoDocumentoInicial getTipoDocumentoInicial() {
        return tipoDocumentoInicial;
    }

    public void setTipoDocumentoInicial(TipoDocumentoInicial tipoDocumentoInicial) {
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }
}