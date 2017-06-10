package pe.app.com.demo.entity;

public class Historial {
    private String Nombre;
    private String Estado;
    private String Servicio;
    private String DiasEstimados;
    private String FinServicio;
    private int Imagen;

    public Historial() {}

    public Historial(String nombre, String estado, String servicio, String diasEstimados, String finServicio, int imagen) {
        Nombre = nombre;
        Estado = estado;
        Servicio = servicio;
        DiasEstimados = diasEstimados;
        FinServicio = finServicio;
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String servicio) {
        Servicio = servicio;
    }

    public String getDiasEstimados() {
        return DiasEstimados;
    }

    public void setDiasEstimados(String diasEstimados) {
        DiasEstimados = diasEstimados;
    }

    public String getFinServicio() {
        return FinServicio;
    }

    public void setFinServicio(String finServicio) {
        FinServicio = finServicio;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }
}
