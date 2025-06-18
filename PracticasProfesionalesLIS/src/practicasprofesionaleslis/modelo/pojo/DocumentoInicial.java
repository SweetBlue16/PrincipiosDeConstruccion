package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

/**
 * Autor: Todos
 * Fecha de creación: 03/06/2025
 * Descripción: Representa los documentos
 * iniciales que sube el estudiante a su expediente
 * dentro del sistema.
 */
public class DocumentoInicial extends Entregable {
    
    public enum TipoDocumentoInicial { CARTAACEPTACION, CONSTANCIASEGURO, CRONOGRAMA, HORARIOUV, OFICIOASIGNACION }
    
    private TipoDocumentoInicial tipoDocumentoInicial;

    public DocumentoInicial() {
    }

    public DocumentoInicial(int id, String nombreArchivo, LocalDate fechaEntregado, LocalDate fechaRevisado,
                          TipoDocumentoInicial tipoDocumentoInicial, int puntajeObtenido, String comentario, byte[] archivo) {
        super(id, nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario, archivo);
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }

    public TipoDocumentoInicial getTipoDocumentoInicial() {
        return tipoDocumentoInicial;
    }

    public void setTipoDocumentoInicial(TipoDocumentoInicial tipoDocumentoInicial) {
        this.tipoDocumentoInicial = tipoDocumentoInicial;
    }
}