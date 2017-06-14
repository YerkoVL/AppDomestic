package pe.app.com.demo.entity;

public class Usuario {
    private int Id;
    private int IdTipoDocumento;
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
    private int IdPerfil;
    private String Perfil;
    private int IdDistrito;
    private String Distrito;
    private String FechaLogueo;
    private String Bienvenida;
    private Float rating;
    private String imagen;
    private String SERVICIO;
    private int IdEstado;
    private String Desc_Estado;

    public Usuario() {}

    public Usuario(int id, int idTipoDocumento, String tipoDocumento, String nroDocumento, String nombres, String apellidos, String correo, String telefono, String direccion, String nombreUsuario, String password, String latitud, String longitud, int idPerfil, String perfil, int idDistrito, String distrito, String fechaLogueo, String bienvenida, Float rating, String imagen, String SERVICIO, int idEstado, String desc_Estado) {
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
        IdDistrito = idDistrito;
        Distrito = distrito;
        FechaLogueo = fechaLogueo;
        Bienvenida = bienvenida;
        this.rating = rating;
        this.imagen = imagen;
        this.SERVICIO = SERVICIO;
        IdEstado = idEstado;
        Desc_Estado = desc_Estado;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdTipoDocumento() {
        return IdTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
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

    public int getIdPerfil() {
        return IdPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        IdPerfil = idPerfil;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }

    public int getIdDistrito() {
        return IdDistrito;
    }

    public void setIdDistrito(int idDistrito) {
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
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

    public int getIdEstado() {
        return IdEstado;
    }

    public void setIdEstado(int idEstado) {
        IdEstado = idEstado;
    }

    public String getDesc_Estado() {
        return Desc_Estado;
    }

    public void setDesc_Estado(String desc_Estado) {
        Desc_Estado = desc_Estado;
    }
}
