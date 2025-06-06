package practicasprofesionaleslis.modelo.pojo;

public class ProfesorEE {
    private int id;
    private String numeroPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoInstitucional;
    private String contraseña;
    private byte[] fotoPerfil;

    public ProfesorEE() {
    }

    public ProfesorEE(int id, String numeroPersonal, String nombre, String apellidoPaterno, String apellidoMaterno, String correoInstitucional, String contraseña, byte[] fotoPerfil) {
        this.id = id;
        this.numeroPersonal = numeroPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoInstitucional = correoInstitucional;
        this.contraseña = contraseña;
        this.fotoPerfil = fotoPerfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
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

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public byte[] getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(byte[] fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", nombre, apellidoPaterno, apellidoMaterno);
    }
}
