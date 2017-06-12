package pe.app.com.demo.tools;

public class GenericTools {
    final public static String KEY_APP = "APP_DOMESTIC";
    final public static String URL_APP = "http://dsystem-001-site1.btempurl.com";
    final public static String GET_USER = "?username=";
    final public static String GET_PASS = "&password=";

    final public String validarNulos(String valor){

        if(valor.equals(null)){
            valor = "";
        }

        return valor;
    }
}
