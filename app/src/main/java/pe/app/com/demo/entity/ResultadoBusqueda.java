package pe.app.com.demo.entity;

public class ResultadoBusqueda {
    private String Nombre;
    private String Servicios;
    private String Atenciones;
    private Float rating ;
    private int imagen;

    public ResultadoBusqueda() {}

    public ResultadoBusqueda(String nombre, String servicios, String atenciones, Float rating, int imagen) {
        Nombre = nombre;
        Servicios = servicios;
        Atenciones = atenciones;
        this.rating = rating;
        this.imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getServicios() {
        return Servicios;
    }

    public void setServicios(String servicios) {
        Servicios = servicios;
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
}
