package practicasprofesionaleslis.modelo.pojo;

/**
 * Autor: Todos
 * Fecha de creación: 27/05/2025
 * Descripción: Representa al expediente
 * asociado a un estudiante dentro del sistema.
 */
public class Expediente {
    
    public enum Estado { ACTIVO, BAJA, CONCLUIDO }
    
    private int id;
    private String fechaCreacion;
    private Estado estado;
    private int calificacion;
    private int horasAcumuladas;
    private Proyecto proyecto;
    private Estudiante estudiante;
    private ExperienciaEducativa experienciaEducativa;

    public Expediente() {
    }

    public Expediente(int id, String fechaCreacion, Estado estado, int calificacion, int horasAcumuladas, Proyecto proyecto, Estudiante estudiante, ExperienciaEducativa experienciaEducativa) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.calificacion = calificacion;
        this.horasAcumuladas = horasAcumuladas;
        this.proyecto = proyecto;
        this.estudiante = estudiante;
        this.experienciaEducativa = experienciaEducativa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Enum getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public int getHorasAcumuladas() {
        return horasAcumuladas;
    }

    public void setHorasAcumuladas(int horasAcumuladas) {
        this.horasAcumuladas = horasAcumuladas;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public ExperienciaEducativa getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(ExperienciaEducativa experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }
    
    
}
