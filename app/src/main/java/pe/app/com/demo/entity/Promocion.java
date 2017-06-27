package pe.app.com.demo.entity;

public class Promocion {
    private int IdPromotor;
    private String NombrePromotor;
    private String CantidadPromociones;

    public Promocion() {    }

    public Promocion(int idPromotor, String nombrePromotor, String cantidadPromociones) {
        IdPromotor = idPromotor;
        NombrePromotor = nombrePromotor;
        CantidadPromociones = cantidadPromociones;
    }

    public int getIdPromotor() {
        return IdPromotor;
    }

    public void setIdPromotor(int idPromotor) {
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
}
