package practicasprofesionaleslis.modelo.pojo;

/**
 * Autor: Todos
 * Fecha de creación: 27/05/2025
 * Descripción: Representa al responsable de proyecto
 * asociado a una organización vinculada.
 */
public class ResponsableProyecto {
    private int id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String puesto;
    private String correoElectronico;
    private int idOrganizacionVinculada;

    public ResponsableProyecto() {
    }

    public ResponsableProyecto(int id, String nombre, String apellidoPaterno, String apellidoMaterno, String puesto, String correoElectronico) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.puesto = puesto;
        this.correoElectronico = correoElectronico;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getIdOrganizacionVinculada() {
        return idOrganizacionVinculada;
    }

    public void setIdOrganizacionVinculada(int idOrganizacionVinculada) {
        this.idOrganizacionVinculada = idOrganizacionVinculada;
    }
    
    @Override
    public String toString() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    
}
