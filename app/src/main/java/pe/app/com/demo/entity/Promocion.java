package pe.app.com.demo.entity;

public class Promocion {
    private String IdPromotor;
    private String NombrePromotor;
    private String CantidadPromociones;
    private String Foto;

    public Promocion() {}

    public Promocion(String idPromotor, String nombrePromotor, String cantidadPromociones, String foto) {
        IdPromotor = idPromotor;
        NombrePromotor = nombrePromotor;
        CantidadPromociones = cantidadPromociones;
        Foto = foto;
    }

    public String getIdPromotor() {
        return IdPromotor;
    }

    public void setIdPromotor(String idPromotor) {
        IdPromotor = idPromotor;
    }

    public String getNombrePromotor() {
        return NombrePromotor;
    }

    public void setNombrePromotor(String nombrePromotor) {
        NombrePromotor = nombrePromotor;
    }

    public String getCantidadPromociones() {
        return CantidadPromociones;
    }

    public void setCantidadPromociones(String cantidadPromociones) {
        CantidadPromociones = cantidadPromociones;
    }

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }
}
