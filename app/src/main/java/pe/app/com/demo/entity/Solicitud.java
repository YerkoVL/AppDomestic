package pe.app.com.demo.entity;

public class Solicitud {
    private String FechaSolicitud;
    private String Servicio;
    private String FechaInicio;
    private String FechaFin;

    public Solicitud() {}

    public Solicitud(String fechaSolicitud, String servicio, String fechaInicio, String fechaFin) {
        FechaSolicitud = fechaSolicitud;
        Servicio = servicio;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
    }

    public String getFechaSolicitud() {
        return FechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        FechaSolicitud = fechaSolicitud;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(String fechaFin) {
        FechaFin = fechaFin;
    }
}
