package practicasprofesionaleslis.modelo.pojo;

/**
 * Autor: Todos
 * Fecha de creación: 27/05/2025
 * Descripción: Representa el proyecto que
 * desarrolla un estudiante dentro de una organización vinculada.
 */
public class Proyecto {
    private int id;
    private String nombre;
    private int numIntegrantes;
    private String descripcion;
    private ResponsableProyecto responsableProyecto;
    private OrganizacionVinculada organizacionVinculada;

    public Proyecto() {
    }

    public Proyecto(int id, String nombre, int numIntegrantes, String descripcion, ResponsableProyecto responsableProyecto, OrganizacionVinculada organizacionVinculada) {
        this.id = id;
        this.nombre = nombre;
        this.numIntegrantes = numIntegrantes;
        this.descripcion = descripcion;
        this.responsableProyecto = responsableProyecto;
        this.organizacionVinculada = organizacionVinculada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumIntegrantes() {
        return numIntegrantes;
    }

    public void setNumIntegrantes(int numIntegrantes) {
        this.numIntegrantes = numIntegrantes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ResponsableProyecto getResponsableProyecto() {
        return responsableProyecto;
    }

    public void setResponsableProyecto(ResponsableProyecto responsableProyecto) {
        this.responsableProyecto = responsableProyecto;
    }

    public OrganizacionVinculada getOrganizacionVinculada() {
        return organizacionVinculada;
    }

    public void setOrganizacionVinculada(OrganizacionVinculada organizacionVinculada) {
        this.organizacionVinculada = organizacionVinculada;
    }
}
