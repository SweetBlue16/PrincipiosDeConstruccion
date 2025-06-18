package practicasprofesionaleslis.modelo.pojo;

/**
 * Autor: Todos
 * Fecha de creación: 27/05/2025
 * Descripción: Representa la experiencia
 * educativa que cursa el estudiante al momento
 * de realizar sus prácticas profesionales.
 */
public class ExperienciaEducativa {
    private int id;
    private int nrc;
    private String nombre;
    private int creditos;
    private int numHoras;
    private String bloque;
    private String seccion;
    private Coordinador coordinador;
    private ProfesorEE profesorEE;
    private Periodo periodo;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int id, int nrc, String nombre, int creditos, int numHoras, String bloque, String seccion, Coordinador coordinador, ProfesorEE profesorEE, Periodo periodo) {
        this.id = id;
        this.nrc = nrc;
        this.nombre = nombre;
        this.creditos = creditos;
        this.numHoras = numHoras;
        this.bloque = bloque;
        this.seccion = seccion;
        this.coordinador = coordinador;
        this.profesorEE = profesorEE;
        this.periodo = periodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }

    public ProfesorEE getProfesorEE() {
        return profesorEE;
    }

    public void setProfesorEE(ProfesorEE profesorEE) {
        this.profesorEE = profesorEE;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
