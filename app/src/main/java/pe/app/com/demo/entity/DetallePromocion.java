package pe.app.com.demo.entity;

public class DetallePromocion {
    private String Id;
    private String NombreOferta;
    private String DescripcionOferta;
    private String CodigoOferta;
    private String Foto;

    public DetallePromocion() {
    }

    public DetallePromocion(String id, String nombreOferta, String descripcionOferta, String codigoOferta, String foto) {
        Id = id;
        NombreOferta = nombreOferta;
        DescripcionOferta = descripcionOferta;
        CodigoOferta = codigoOferta;
        Foto = foto;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombreOferta() {
        return NombreOferta;
    }

    public void setNombreOferta(String nombreOferta) {
        NombreOferta = nombreOferta;
    }

    public String getDescripcionOferta() {
        return DescripcionOferta;
    }

    public void setDescripcionOferta(String descripcionOferta) {
        DescripcionOferta = descripcionOferta;
    }

    public String getCodigoOferta() {
        return CodigoOferta;
    }

    public void setCodigoOferta(String codigoOferta) {
        CodigoOferta = codigoOferta;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
