package pe.app.com.demo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pe.app.com.demo.SQLiteHelper.MapasSQLHelper;
import pe.app.com.demo.comunicators.ComunicadorBuscarServicioXResultado;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Respuesta;
import pe.app.com.demo.entity.ResultadoInsercionSolicitud;
import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static android.content.ContentValues.TAG;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_APELLIDOS;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESCRIPCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_LATITUD;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_LONGITUD;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NOMBRES;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_AFIRMACION;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_FRAGMENT;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_FRAGMENT_ID_SOLICITUD;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_FRAGMENT_RUBROS;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericTools.GET_COMAS;
import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_ESPACIO;
import static pe.app.com.demo.tools.GenericTools.GET_FECHA_FIN;
import static pe.app.com.demo.tools.GenericTools.GET_FECHA_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_USER;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_LISTA_RUBROS;
import static pe.app.com.demo.tools.GenericTools.GET_RUBROS;
import static pe.app.com.demo.tools.GenericTools.GET_SERVICIO;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_SERVICIOS;
import static pe.app.com.demo.tools.GenericUrls.BASE_INSERTAR_SOLICITUD;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ContenidoBuscarServicios extends Fragment{

    Button busqueda;
    EditText descripcionServicio;
    EditText fechaInicio;
    EditText fechaFin;
    CheckBox pintorCheck;
    CheckBox albanilCheck;
    CheckBox gasfiteroCheck;

    Context mCtx;

    GenericTools tools = new GenericTools();
    GenericAlerts alertas = new GenericAlerts();
    Gson gson = new Gson();

    int idUsuario=0;
    String reenviarRubros;
    int diaInicioComparar, mesInicioComparar, anioInicioComparar;
    int diaFinComparar, mesFinComparar, anioFinComparar;

    String IdSolicitudEnviar;

    Calendar miCalendario = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ProgressDialog progressDialog = null;
    private ComunicadorBuscarServicioXResultado comunicadorBuscar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_buscar_servicios, container, false);

        mCtx = rootView.getContext();

        busqueda = (Button)rootView.findViewById(R.id.btnBuscarServicios);
        fechaInicio = (EditText) rootView.findViewById(R.id.edtFechaInicio);
        fechaFin = (EditText) rootView.findViewById(R.id.edtFechaFin);
        descripcionServicio = (EditText) rootView.findViewById(R.id.edtDescripcionServicios);
        pintorCheck = (CheckBox) rootView.findViewById(R.id.chkPintor);
        albanilCheck = (CheckBox) rootView.findViewById(R.id.chkAlbañil);
        gasfiteroCheck = (CheckBox) rootView.findViewById(R.id.chkGasfitero);

        progressDialog = new ProgressDialog(mCtx);
        comunicadorBuscar = comunicadorBuscarServicioXResultado;

        fechaInicio.setFocusableInTouchMode(false);
        fechaFin.setFocusableInTouchMode(false);

        final DatePickerDialog.OnDateSetListener dialogFechaFin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                anio = datePicker.getYear();
                mes = datePicker.getMonth() + 1;
                dia = datePicker.getDayOfMonth();

                diaFinComparar = dia;
                mesFinComparar = mes;
                anioFinComparar = anio;

                datePicker.setMinDate(Calendar.DATE);

                String nuevaFecha = anio + "/" + tools.checkearDigito(mes) + "/" + tools.checkearDigito(dia);

                fechaFin.setText(nuevaFecha);

            }
        };

        final DatePickerDialog.OnDateSetListener dialogFechaInicio = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                anio = datePicker.getYear();
                mes = datePicker.getMonth() + 1;
                dia = datePicker.getDayOfMonth();

                diaInicioComparar = dia;
                mesInicioComparar = mes;
                anioInicioComparar = anio;

                datePicker.setMinDate(Calendar.DATE);

                String nuevaFecha =  anio + "/" + tools.checkearDigito(mes) + "/" + tools.checkearDigito(dia);

                fechaInicio.setText(nuevaFecha);

            }
        };

        fechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(mCtx, dialogFechaInicio, miCalendario
                        .get(Calendar.YEAR), miCalendario.get(Calendar.MONTH),
                        miCalendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(mCtx, dialogFechaFin, miCalendario
                        .get(Calendar.YEAR), miCalendario.get(Calendar.MONTH),
                        miCalendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validarDatosEnviar();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        obtenerDatosUsuario();

        return rootView;
    }

    public void validarDatosEnviar() throws ParseException {
        String fechaInicioSolicitud = fechaInicio.getText().toString();
        String fechaFinSolicitud = fechaFin.getText().toString();
        String serviciosSolicitud = obtenerServicios();
        String rubrosSolicitud = obtenerRubros();

        if(!fechaInicioSolicitud.equals("")){
            if(!fechaFinSolicitud.equals("")){
                if(!serviciosSolicitud.equals("")){
                    if(!rubrosSolicitud.equals("")){
                        if(validarFechaXHoy()) {
                            if(validarFechas()) {
                                reenviarRubros = rubrosSolicitud;
                                inicioBusqueda(idUsuario, fechaInicioSolicitud, fechaFinSolicitud, serviciosSolicitud, rubrosSolicitud);
                                actualizarSP();
//                                guardarPreferenciaFragment(rubrosSolicitud, IdSolicitudEnviar);
                            }else{
                                alertas.mensajeInfo("Error","Fecha Inicio debe ser menor al de Fin",mCtx);
                            }
                        }else{
                            alertas.mensajeInfo("Error","Fecha Inicio debe ser mayor del día de hoy",mCtx);
                        }
                    }else{
                        alertas.mensajeInfo("Error","Debe seleccionar rubros",mCtx);
                    }
                }else{
                    alertas.mensajeInfo("Error","Debe ingresar descripcion",mCtx);
                }
            }else{
                alertas.mensajeInfo("Error","Debe ingresar Fecha Fin",mCtx);
            }
        }else{
            alertas.mensajeInfo("Error","Debe ingresar Fecha Inicio",mCtx);
        }
    }

    public void actualizarSP(){
        SharedPreferences prefs = mCtx.getSharedPreferences(PREFERENCIA_BUSQUEDA_SERVICIO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREFERENCIA_VALOR_BUSQUEDA_SERVICIO, PREFERENCIA_AFIRMACION);
        editor.commit();
    }

    public void inicioBusqueda(int idUsuario, String fechaInicio, String fechaFin, String servicios, final String rubros) {

        final String url = URL_APP + BASE_URL + BASE_INSERTAR_SOLICITUD + GET_INICIO + GET_ID_USER + idUsuario + GET_CONTINUO +
                           GET_FECHA_INICIO + fechaInicio + GET_CONTINUO + GET_FECHA_FIN + fechaFin +  GET_CONTINUO + GET_SERVICIO + servicios +
                           GET_CONTINUO + GET_RUBROS + rubros;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        StringRequest respuestaInsercion = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {

                        if(Response!=null) {
                            try {
                                ResultadoInsercionSolicitud resultadoInsercionSolicitud = gson.fromJson(Response, ResultadoInsercionSolicitud.class);
                                if(resultadoInsercionSolicitud.getId()!= null) {
                                    IdSolicitudEnviar = resultadoInsercionSolicitud.getId();
                                    eliminarMapasAnteriores();
                                    generarMapas(rubros);

                                    guardarPreferenciaFragment(reenviarRubros, Integer.valueOf(IdSolicitudEnviar));

                                    progressDialog.dismiss();
                                }else{
                                    Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                                    alertas.mensajeInfo("Fallo Insertar",respuesta.getMensaje(),mCtx);
                                    progressDialog.dismiss();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                alertas.mensajeInfo("Fallo Insertar","None",mCtx);
                                progressDialog.dismiss();
                            }
                        }else{
                            Respuesta respuesta = gson.fromJson(Response,Respuesta.class);
                            alertas.mensajeInfo("Fallo Insertar",respuesta.getMensaje(),mCtx);
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                progressDialog.dismiss();
                alertas.mensajeInfo("Fallo Insertar","Error Desconocido",mCtx);
            }
        });

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaInsercion);
    }

    public void generarMapas(final String rubros) {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_SERVICIOS + GET_INICIO + GET_LISTA_RUBROS + rubros +
                GET_CONTINUO + GET_ID_USER + idUsuario;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaSolicitud = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);

                                JSONArray objetoSERVICIO = object.getJSONArray("SERVICIO");

                                if (objetoSERVICIO != null || objetoSERVICIO.length() > 0) {
                                    String idPersona , nombreCompletoPersona , latitudPersona, longitudPersona;

                                    String descripcionServicioTotales= "";

                                    for(int x = 0 ;x < objetoSERVICIO.length();x++) {
                                        JSONObject servicio = objetoSERVICIO.getJSONObject(x);
                                        String descripcionServicio = tools.validarNulos(servicio.getString(OBJETO_DESCRIPCION));
                                        descripcionServicioTotales = descripcionServicio + ", " + descripcionServicioTotales;
                                    }

                                    idPersona = tools.validarNulos(object.getString(OBJETO_ID));
                                    nombreCompletoPersona = tools.validarNulos(object.getString(OBJETO_NOMBRES)) + " " + tools.validarNulos(object.getString(OBJETO_APELLIDOS));
                                    latitudPersona = tools.validarNulos(object.getString(OBJETO_LATITUD));
                                    longitudPersona = tools.validarNulos(object.getString(OBJETO_LONGITUD));

                                    insertarDatosMapas(idPersona,nombreCompletoPersona,descripcionServicioTotales, latitudPersona,longitudPersona);
                                }
                            }
                            progressDialog.dismiss();
                            comunicadorBuscar.comunicarResultadoPerfil(1,"PERFIL_BUSQUEDA",rubros,Integer.valueOf(IdSolicitudEnviar));

                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        });

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaSolicitud);
    }

    public void insertarDatosMapas(String idPersona,String nombreCompletoPersona,String descripcionServicioTotales, String latitudPersona,String longitudPersona){
        MapasSQLHelper usdbh =
                new MapasSQLHelper(mCtx, "DBMapas", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("idPersona", idPersona);
        nuevoRegistro.put("nombrePersona", nombreCompletoPersona);
        nuevoRegistro.put("rubros", descripcionServicioTotales);
        nuevoRegistro.put("latitud", latitudPersona);
        nuevoRegistro.put("longitud",longitudPersona);

        db.insert("Mapas", null, nuevoRegistro);

        db.close();
    }

    public void eliminarMapasAnteriores(){
        MapasSQLHelper usdbh =
                new MapasSQLHelper(mCtx, "DBMapas", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        db.delete("Mapas", null, null);
    }

    public String obtenerServicios(){
        String serviciosFinales;

        String serviciosPreFinal = descripcionServicio.getText().toString();
        serviciosFinales = serviciosPreFinal.replace(" ",GET_ESPACIO);

        tools.validarNulos(serviciosFinales);
        return serviciosFinales;
    }

    public String obtenerRubros(){
        String rubrosFinales = "";

        if(pintorCheck.isChecked()){
            rubrosFinales = rubrosFinales + "1";
            if(albanilCheck.isChecked()){
                rubrosFinales = rubrosFinales + GET_COMAS + "2" ;
                if(gasfiteroCheck.isChecked()){
                    rubrosFinales = rubrosFinales + GET_COMAS + "3";
                }
            }else{
                if(gasfiteroCheck.isChecked()){
                    rubrosFinales = rubrosFinales + GET_COMAS + "3";
                }
            }
        }else{
            if(albanilCheck.isChecked()){
                rubrosFinales = rubrosFinales + "2";
                if(gasfiteroCheck.isChecked()){
                    rubrosFinales = rubrosFinales + GET_COMAS + "3";
                }
            }else{
                if(gasfiteroCheck.isChecked()){
                    rubrosFinales = rubrosFinales + "3";
                }
            }
        }

        tools.validarNulos(rubrosFinales);
        return rubrosFinales;
    }

    public boolean validarFechaXHoy() throws ParseException {
        boolean valor=false;

        int dia = miCalendario.get(Calendar.DAY_OF_MONTH);
        int mes = miCalendario.get(Calendar.MONTH);
        int anio = miCalendario.get(Calendar.YEAR);

        if(diaInicioComparar>=dia){
            if(mesInicioComparar>=mes + 1){
                if (anioInicioComparar>=anio){
                    valor = true;
                }
            }
        }

        return valor;
    }

    public boolean validarFechas() throws ParseException {
        boolean valor=false;

        if(diaFinComparar>=diaInicioComparar){
            if(mesFinComparar>=mesInicioComparar){
                if(anioFinComparar>=mesFinComparar){
                    valor = true;
                }
            }
        }

        return valor;
    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
    }

    public void guardarPreferenciaFragment(String listaRubro,int idSolicitud) {
        SharedPreferences.Editor editor = mCtx.getSharedPreferences(PREFERENCIA_FRAGMENT, Context.MODE_PRIVATE).edit();
        editor.putString(PREFERENCIA_FRAGMENT_RUBROS, listaRubro);
        editor.putInt(PREFERENCIA_FRAGMENT_ID_SOLICITUD, idSolicitud);
        editor.commit();
    }

    ComunicadorBuscarServicioXResultado comunicadorBuscarServicioXResultado = new ComunicadorBuscarServicioXResultado() {
        @Override
        public void comunicarResultadoPerfil(int valorACCION,String valorFRAGMENT, String valorRUBROS, int IdSolicitud) {
            Bundle bundle = new Bundle();
            Intent in =new Intent(getActivity(),MenuPrincipalActivity.class);
            in.putExtra("VALOR_ACCION",valorACCION);
            in.putExtra("VALOR_FRAGMENT",valorFRAGMENT);
            in.putExtra("VALOR_RUBROS",valorRUBROS);
            in.putExtra("VALOR_ID_SOLICITUD",IdSolicitud);
            in.putExtras(bundle);
            startActivity(in);
        }
    };
}
