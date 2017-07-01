package pe.app.com.demo.entity;

public class SolicitudesEnviadas {
    private String Id;
    private String IdTipoDocumento;
    private String TipoDocumento;
    private String NroDocumento;
    private String Nombres;
    private String Apellidos;
    private String Correo;
    private String Telefono;
    private String Direccion;
    private String NombreUsuario;
    private String Password;
    private String Latitud;
    private String Longitud;
    private String IdPerfil;
    private String Perfil;
    private String IdDpto;
    private String Dpto;
    private String IdDistrito;
    private String Distrito;
    private String FechaLogueo;
    private String Bienvenida;
    private String Atenciones;
    private String rating;
    private String imagen;
    private String SERVICIO;
    private String RazonSocial;
    private String NombreComercial;
    private String Key;
    private String IdEstado;
    private String Desc_Estado;

    public SolicitudesEnviadas() {
    }

    public SolicitudesEnviadas(String id, String idTipoDocumento, String tipoDocumento, String nroDocumento, String nombres, String apellidos, String correo, String telefono, String direccion, String nombreUsuario, String password, String latitud, String longitud, String idPerfil, String perfil, String idDpto, String dpto, String idDistrito, String distrito, String fechaLogueo, String bienvenida, String atenciones, String rating, String imagen, String SERVICIO, String razonSocial, String nombreComercial, String key, String idEstado, String desc_Estado) {
        Id = id;
        IdTipoDocumento = idTipoDocumento;
        TipoDocumento = tipoDocumento;
        NroDocumento = nroDocumento;
        Nombres = nombres;
        Apellidos = apellidos;
        Correo = correo;
        Telefono = telefono;
        Direccion = direccion;
        NombreUsuario = nombreUsuario;
        Password = password;
        Latitud = latitud;
        Longitud = longitud;
        IdPerfil = idPerfil;
        Perfil = perfil;
        IdDpto = idDpto;
        Dpto = dpto;
        IdDistrito = idDistrito;
        Distrito = distrito;
        FechaLogueo = fechaLogueo;
        Bienvenida = bienvenida;
        Atenciones = atenciones;
        this.rating = rating;
        this.imagen = imagen;
        this.SERVICIO = SERVICIO;
        RazonSocial = razonSocial;
        NombreComercial = nombreComercial;
        Key = key;
        IdEstado = idEstado;
        Desc_Estado = desc_Estado;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIdTipoDocumento() {
        return IdTipoDocumento;
    }

    public void setIdTipoDocumento(String idTipoDocumento) {
        IdTipoDocumento = idTipoDocumento;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getNroDocumento() {
        return NroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        NroDocumento = nroDocumento;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getIdPerfil() {
        return IdPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        IdPerfil = idPerfil;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }

    public String getIdDpto() {
        return IdDpto;
    }

    public void setIdDpto(String idDpto) {
        IdDpto = idDpto;
    }

    public String getDpto() {
        return Dpto;
    }

    public void setDpto(String dpto) {
        Dpto = dpto;
    }

    public String getIdDistrito() {
        return IdDistrito;
    }

    public void setIdDistrito(String idDistrito) {
        IdDistrito = idDistrito;
    }

    public String getDistrito() {
        return Distrito;
    }

    public void setDistrito(String distrito) {
        Distrito = distrito;
    }

    public String getFechaLogueo() {
        return FechaLogueo;
    }

    public void setFechaLogueo(String fechaLogueo) {
        FechaLogueo = fechaLogueo;
    }

    public String getBienvenida() {
        return Bienvenida;
    }

    public void setBienvenida(String bienvenida) {
        Bienvenida = bienvenida;
    }

    public String getAtenciones() {
        return Atenciones;
    }

    public void setAtenciones(String atenciones) {
        Atenciones = atenciones;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getSERVICIO() {
        return SERVICIO;
    }

    public void setSERVICIO(String SERVICIO) {
        this.SERVICIO = SERVICIO;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        NombreComercial = nombreComercial;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
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
