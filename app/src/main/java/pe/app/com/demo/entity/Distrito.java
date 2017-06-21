package pe.app.com.demo.entity;

public class Distrito {
    private String Id;
    private String Descripcion;
    private String IdProvincia;

    public Distrito() {
    }

    public Distrito(String id, String descripcion, String idProvincia) {
        Id = id;
        Descripcion = descripcion;
        IdProvincia = idProvincia;
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

    public String getIdProvincia() {
        return IdProvincia;
    }

    public void setIdProvincia(String idProvincia) {
        IdProvincia = idProvincia;
    }
}
