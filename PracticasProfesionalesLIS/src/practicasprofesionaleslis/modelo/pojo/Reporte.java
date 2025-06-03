package practicasprofesionaleslis.modelo.pojo;

import java.time.LocalDate;

public class Reporte extends Entregable {
    private int horasCubiertas;
    private int numeroReporte;

    public Reporte() {
    }

    public Reporte(String nombreArchivo, int horasCubiertas, LocalDate fechaEntregado, LocalDate fechaRevisado,
                 int numeroReporte, int puntajeObtenido, String comentario) {
        super(nombreArchivo, fechaEntregado, fechaRevisado, puntajeObtenido, comentario);
        this.horasCubiertas = horasCubiertas;
        this.numeroReporte = numeroReporte;
    }

    public int getHorasCubiertas() {
        return horasCubiertas;
    }

    public void setHorasCubiertas(int horasCubiertas) {
        this.horasCubiertas = horasCubiertas;
    }

    public int getNumeroReporte() {
        return numeroReporte;
    }

    public void setNumeroReporte(int numeroReporte) {
        this.numeroReporte = numeroReporte;
    }
}