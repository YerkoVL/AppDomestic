package pe.app.com.demo.entity;

public class Respuesta {
    private String Mensaje;
    private boolean Valor;
    private String Bienvenida;

    public Respuesta() {}

    public Respuesta(String mensaje, boolean valor, String bienvenida) {
        Mensaje = mensaje;
        Valor = valor;
        Bienvenida = bienvenida;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public boolean isValor() {
        return Valor;
    }

    public void setValor(boolean valor) {
        Valor = valor;
    }

    public String getBienvenida() {
        return Bienvenida;
    }

    public void setBienvenida(String bienvenida) {
        Bienvenida = bienvenida;
    }
}
