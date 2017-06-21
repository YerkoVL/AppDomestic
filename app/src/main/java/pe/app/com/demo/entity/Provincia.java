package pe.app.com.demo.entity;

public class Provincia {
    private String Id;
    private String Descripcion;
    private String IdDpto;

    public Provincia() {
    }

    public Provincia(String id, String descripcion, String idDpto) {
        Id = id;
        Descripcion = descripcion;
        IdDpto = idDpto;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getIdDpto() {
        return IdDpto;
    }

    public void setIdDpto(String idDpto) {
        IdDpto = idDpto;
    }
}
