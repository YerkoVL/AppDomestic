package pe.app.com.demo.entity;

public class Mapa {
    private String IdSocio;
    private String NombreSocio;
    private String Rubros;
    private String Latitud;
    private String Longitud;

    public Mapa() {
    }

    public Mapa(String idSocio, String nombreSocio, String rubros, String latitud, String longitud) {
        IdSocio = idSocio;
        NombreSocio = nombreSocio;
        Rubros = rubros;
        Latitud = latitud;
        Longitud = longitud;
    }

    public String getIdSocio() {
        return IdSocio;
    }

    public void setIdSocio(String idSocio) {
        IdSocio = idSocio;
    }

    public String getNombreSocio() {
        return NombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        NombreSocio = nombreSocio;
    }

    public String getRubros() {
        return Rubros;
    }

    public void setRubros(String rubros) {
        Rubros = rubros;
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
}
