package pe.app.com.demo.tools;

public class GenericTools {
    final public static String KEY_APP = "APP_DOMESTIC";
    final public static String URL_APP = "http://dsystem-001-site1.btempurl.com";
    final public static String GET_INICIO = "?";
    final public static String GET_CONTINUO = "&";
    final public static String GET_ESPACIO = "%20";
    final public static String GET_COMAS = ",";
    final public static String GET_ID_USER = "IdUsuario=";
    final public static String GET_USER = "username=";
    final public static String GET_PASS = "password=";
    final public static String GET_ID_SOCIO = "idsocio=";
    final public static String GET_FECHA_INICIO = "FechaInicio=";
    final public static String GET_FECHA_FIN = "FechaFin=";
    final public static String GET_SERVICIO = "servicio=";
    final public static String GET_RUBROS = "rubros=";
    final public static String GET_LISTA_RUBROS = "listRubro=";
    final public static String GET_ESTADO_PARA_SOLICITUDES = "3";
    final public static String GET_ESTADO_PARA_HISTORIAL = "7";

    final public String validarNulos(String valor){

        if(valor == null){
            valor = " ";
        }

        return valor;
    }

    final public String checkearDigito(int numero){
        return numero<=9 ? "0" + numero:String.valueOf(numero);
    }
}
