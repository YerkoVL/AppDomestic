package pe.app.com.demo.entity;

public class ResultadoBusqueda {

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
    private String Servicios;
    private String Latitud;
    private String Longitud;
    private String IdPerfil;
    private String Perfil;
    private String IdDistrito;
    private String Distrito;
    private String FechaLogueo;
    private String Bienvenida;
    private String Atenciones;
    private Float rating;
    private int imagen;
    private String IdEstado;
    private String Desc_Estado;

    public ResultadoBusqueda() {}

    public ResultadoBusqueda(String id, String idTipoDocumento, String tipoDocumento, String nroDocumento, String nombres, String apellidos, String correo, String telefono, String direccion, String nombreUsuario, String password, String servicios, String latitud, String longitud, String idPerfil, String perfil, String idDistrito, String distrito, String fechaLogueo, String bienvenida, String atenciones, Float rating, int imagen, String idEstado, String desc_Estado) {
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
        Servicios = servicios;
        Latitud = latitud;
        Longitud = longitud;
        IdPerfil = idPerfil;
        Perfil = perfil;
        IdDistrito = idDistrito;
        Distrito = distrito;
        FechaLogueo = fechaLogueo;
        Bienvenida = bienvenida;
        Atenciones = atenciones;
        this.rating = rating;
        this.imagen = imagen;
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

    public String getServicios() {
        return Servicios;
    }

    public void setServicios(String servicios) {
        Servicios = servicios;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
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
