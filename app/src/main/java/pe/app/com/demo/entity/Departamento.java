package pe.app.com.demo.entity;

public class Departamento {
    private String Id;
    private String Descripcion;
    private String IdPais;

    public Departamento() {
    }

    public Departamento(String id, String descripcion, String idPais) {
        Id = id;
        Descripcion = descripcion;
        IdPais = idPais;
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

    public String getIdPais() {
        return IdPais;
    }

    public void setIdPais(String idPais) {
        IdPais = idPais;
    }
}
