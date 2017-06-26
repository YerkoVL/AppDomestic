package pe.app.com.demo.tools;

public class GenericTools {
    final public static String KEY_APP = "APP_DOMESTIC";
    final public static String URL_APP = "http://dsystem-001-site1.btempurl.com";
    final public static String GET_INICIO = "?";
    final public static String GET_CONTINUO = "&";
    final public static String GET_ESPACIO = "%20";
    final public static String GET_COMAS = ",";
    final public static String GET_ID_USER = "IdUsuario=";
    final public static String GET_ID_SOLICITUD = "IdSolicitud=";
    final public static String GET_ID_ESTADO = "IdEstado=";
    final public static String GET_USER = "username=";
    final public static String GET_PASS = "password=";
    final public static String GET_ID_SOCIO = "idsocio=";
    final public static String GET_FECHA_INICIO = "FechaInicio=";
    final public static String GET_FECHA_FIN = "FechaFin=";
    final public static String GET_SERVICIO = "servicio=";
    final public static String GET_RUBROS = "rubros=";
    final public static String GET_VALOR = "Valor=";
    final public static String GET_COMENTARIO = "Comentario=";
    final public static String GET_LISTA_RUBROS = "listRubro=";
    final public static String GET_ESTADO_PARA_SOLICITUDES = "3";
    final public static String GET_ESTADO_PARA_HISTORIAL = "7";

    final public static String GET_NOMBRES = "Nombres=";
    final public static String GET_APELLIDOS = "Apellidos=";
    final public static String GET_ID_TIPO_DOCUMENTO = "IdTipoDocumento=";
    final public static String GET_NRO_DOCUMENTO = "NroDocumento=";
    final public static String GET_DIRECCION = "Direccion=";
    final public static String GET_CORREO = "Correo=";
    final public static String GET_TELEFONO = "Telefono=";
    final public static String GET_ID_DISTRITO = "IdDistrito=";
    final public static String GET_ID_PERFIL = "IdPerfil=";
    final public static String GET_RAZON_SOCIAL = "RazonSocial=";
    final public static String GET_NOMBRE_COMERCIAL = "NombreComercial=";
    final public static String GET_LONGITUD = "Longitud=";
    final public static String GET_LATITUD = "Latitud=";
    final public static String GET_SERVICIO_REGISTRO = "SERVICIO=";
    final public static String GET_FOTO = "Foto=";

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
