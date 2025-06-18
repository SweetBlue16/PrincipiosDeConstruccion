package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

/**
 * Autor: Todos
 * Fecha de creación: 03/06/2025
 * Descripción: Representa las entregas de
 * documentos intermedios a las que sube el estudiante
 * sus archivos dentro del sistema.
 */
public class EntregaDocumentoIntermedio extends Entrega {
    private String tipoDocumentoIntermedio;

    public EntregaDocumentoIntermedio() {
    }

    public EntregaDocumentoIntermedio(int id, LocalDate fechaInicio, LocalDate fechaFin, 
                                    int puntaje, String tipoDocumentoIntermedio) {
        super(id, fechaInicio, fechaFin, puntaje);
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }

    public String getTipoDocumentoIntermedio() {
        return tipoDocumentoIntermedio;
    }

    public void setTipoDocumentoIntermedio(String tipoDocumentoIntermedio) {
        this.tipoDocumentoIntermedio = tipoDocumentoIntermedio;
    }

    @Override
    public String toString() {
        return tipoDocumentoIntermedio + " (" + getFechaInicio() + " a " + getFechaFin() + ")";
    }
}