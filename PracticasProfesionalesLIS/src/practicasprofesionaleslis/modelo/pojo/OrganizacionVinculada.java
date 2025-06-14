package practicasprofesionaleslis.modelo.pojo;

public class OrganizacionVinculada {
    private int id;
    private int numProyectos;
    private String razonSocial;
    private String correoElectronico;
    private String telefono;
    private String domicilioFiscal;

    public OrganizacionVinculada() {
    }

    public OrganizacionVinculada(int id, int numProyectos, String razonSocial, String correoElectronico, String telefono, String domicilioFiscal) {
        this.id = id;
        this.numProyectos = numProyectos;
        this.razonSocial = razonSocial;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.domicilioFiscal = domicilioFiscal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumProyectos() {
        return numProyectos;
    }

    public void setNumProyectos(int numProyectos) {
        this.numProyectos = numProyectos;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilioFiscal() {
        return domicilioFiscal;
    }

    public void setDomicilioFiscal(String domicilioFiscal) {
        this.domicilioFiscal = domicilioFiscal;
    }

    @Override
    public String toString() {
        return razonSocial;
    }
}
