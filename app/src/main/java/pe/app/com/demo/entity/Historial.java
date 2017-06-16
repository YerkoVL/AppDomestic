package pe.app.com.demo.entity;

public class Historial {
    private String Id;
    private String FechaInicio;
    private String FechaFin;
    private String Servicio;
    private String Calificacion;
    private String FechaSolicitud;
    private String Rubro;
    private String IdRubro;
    private String DescripcionRubro;
    private String IdEstadoRubro;
    private String Desc_EstadoRubro;
    private String IdSocio;
    private String Socio;
    private String Foto;
    private String Comentario;
    private String IdEstado;
    private String Desc_Estado;

    public Historial() {
    }

    public Historial(String id, String fechaInicio, String fechaFin, String servicio, String calificacion, String fechaSolicitud, String rubro, String idRubro, String descripcionRubro, String idEstadoRubro, String desc_EstadoRubro, String idSocio, String socio, String foto, String comentario, String idEstado, String desc_Estado) {
        Id = id;
        FechaInicio = fechaInicio;
        FechaFin = fechaFin;
        Servicio = servicio;
        Calificacion = calificacion;
        FechaSolicitud = fechaSolicitud;
        Rubro = rubro;
        IdRubro = idRubro;
        DescripcionRubro = descripcionRubro;
        IdEstadoRubro = idEstadoRubro;
        Desc_EstadoRubro = desc_EstadoRubro;
        IdSocio = idSocio;
        Socio = socio;
        Foto = foto;
        Comentario = comentario;
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

    public String getIdRubro() {
        return IdRubro;
    }

    public void setIdRubro(String idRubro) {
        IdRubro = idRubro;
    }

    public String getDescripcionRubro() {
        return DescripcionRubro;
    }

    public void setDescripcionRubro(String descripcionRubro) {
        DescripcionRubro = descripcionRubro;
    }

    public String getIdEstadoRubro() {
        return IdEstadoRubro;
    }

    public void setIdEstadoRubro(String idEstadoRubro) {
        IdEstadoRubro = idEstadoRubro;
    }

    public String getDesc_EstadoRubro() {
        return Desc_EstadoRubro;
    }

    public void setDesc_EstadoRubro(String desc_EstadoRubro) {
        Desc_EstadoRubro = desc_EstadoRubro;
    }

    public String getIdSocio() {
        return IdSocio;
    }

    public void setIdSocio(String idSocio) {
        IdSocio = idSocio;
    }

    public String getSocio() {
        return Socio;
    }

    public void setSocio(String socio) {
        Socio = socio;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
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
