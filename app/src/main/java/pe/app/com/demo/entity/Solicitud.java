package pe.app.com.demo.entity;

public class Solicitud {
    private String Id;
    private String FechaInicio;
    private String FechaFin;
    private String Servicio;
    private String Calificacion;
    private String FechaSolicitud;
    private String Rubro;
    private String IdEstado;
    private String Desc_Estado;

    public Solicitud() {}

    public Solicitud(String id, String fechaInicio, String fechaFin, String servicio, String calificacion, String fechaSolicitud, String rubro, String idEstado, String desc_Estado) {
        Id = id;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        Servicio = servicio;
        Calificacion = calificacion;
        FechaSolicitud = fechaSolicitud;
        Rubro = rubro;
        IdEstado = idEstado;
        Desc_Estado = desc_Estado;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public String getCalificacion() {
        return Calificacion;
    }

    public void setCalificacion(String calificacion) {
        Calificacion = calificacion;
    }

    public String getFechaSolicitud() {
        return FechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        FechaSolicitud = fechaSolicitud;
    }

    public String getRubro() {
        return Rubro;
    }

    public void setRubro(String rubro) {
        Rubro = rubro;
    }

    public String getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(String idEstado) {
        IdEstado = idEstado;
    }

    public String getDesc_Estado() {
        return Desc_Estado;
    }

    public void setDesc_Estado(String desc_Estado) {
        Desc_Estado = desc_Estado;
    }
}
