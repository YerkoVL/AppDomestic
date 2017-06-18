package pe.app.com.demo;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.app.com.demo.SQLiteHelper.MapasSQLHelper;
import pe.app.com.demo.adapters.ResultadoBusquedaAdapter;
import pe.app.com.demo.comunicators.ComunicadorAdapters;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.ResultadoBusqueda;
import pe.app.com.demo.tools.GenericTools;

import static android.content.ContentValues.TAG;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_APELLIDOS;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ATENCIONES;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_BIENVENIDA;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_CORREO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESCRIPCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESC_ESTADO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DIRECCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DISTRITO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_LOGEO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_DISTRITO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_ESTADO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_TIPO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_IMAGEN;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_LATITUD;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_LONGITUD;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NOMBRES;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NRO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_PASS_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_RATING;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_TELEFONO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_TIPO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_BUSQUEDA_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_FRAGMENT_RUBROS;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_USER;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_LISTA_RUBROS;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_SERVICIOS;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ContenidoResultadoBusqueda extends Fragment {

    private RecyclerView recyclerView;
    private ResultadoBusquedaAdapter resultadoBusquedaAdapter;
    private List<ResultadoBusqueda> resultadoBusquedaList;
    private MapasSQLHelper db;

    Context mCtx;
    LinearLayout dataEmpty;

    ProgressDialog progressDialog = null;
    GenericTools tools = new GenericTools();

    int idUsuario = 0;
    String nombreUsuario = "";
    boolean valorData = false;

    String valorVerContacto = "PERFIL_PERSONAL",rubrosList, rubrosLista = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_resultado_busqueda, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerResultadoBusqueda);
        recyclerView.setHasFixedSize(true);

        dataEmpty = (LinearLayout) rootView.findViewById(R.id.lyDataEmpty);

        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(mCtx);

        resultadoBusquedaList = new ArrayList<>();

        obtenerDatosUsuario();

        return rootView;

    }

    public void obtenerRespuestaBusqueda() {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_SERVICIOS + GET_INICIO + GET_LISTA_RUBROS + rubrosLista +
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

                                ResultadoBusqueda resultadoBusqueda = new ResultadoBusqueda(
                                        tools.validarNulos(object.getString(OBJETO_ID)),
                                        tools.validarNulos(object.getString(OBJETO_ID_TIPO_DOCUMENTO)),
                                        tools.validarNulos(object.getString(OBJETO_TIPO_DOCUMENTO)),
                                        tools.validarNulos(object.getString(OBJETO_NRO_DOCUMENTO)),
                                        tools.validarNulos(object.getString(OBJETO_NOMBRES)),
                                        tools.validarNulos(object.getString(OBJETO_APELLIDOS)),
                                        tools.validarNulos(object.getString(OBJETO_CORREO)),
                                        tools.validarNulos(object.getString(OBJETO_TELEFONO)),
                                        tools.validarNulos(object.getString(OBJETO_DIRECCION)),
                                        tools.validarNulos(object.getString(OBJETO_NOMBRE_USUARIO)),
                                        tools.validarNulos(object.getString(OBJETO_PASS_USUARIO)),
                                        tools.validarNulos(""),
                                        tools.validarNulos(object.getString(OBJETO_LATITUD)),
                                        tools.validarNulos(object.getString(OBJETO_LONGITUD)),
                                        tools.validarNulos(object.getString(OBJETO_ID_PERFIL)),
                                        tools.validarNulos(object.getString(OBJETO_PERFIL)),
                                        tools.validarNulos(object.getString(OBJETO_ID_DISTRITO)),
                                        tools.validarNulos(object.getString(OBJETO_DISTRITO)),
                                        tools.validarNulos(object.getString(OBJETO_FECHA_LOGEO)),
                                        tools.validarNulos(object.getString(OBJETO_BIENVENIDA)),
                                        tools.validarNulos(object.getString(OBJETO_ATENCIONES)),
                                        Float.valueOf(tools.validarNulos(object.getString(OBJETO_RATING)+ "f")),
                                        tools.validarNulos(object.getString(OBJETO_IMAGEN)),
                                        tools.validarNulos(object.getString(OBJETO_ID_ESTADO)),
                                        tools.validarNulos(object.getString(OBJETO_DESC_ESTADO)));

                                JSONArray objetoSERVICIO = object.getJSONArray("SERVICIO");

                                if (objetoSERVICIO != null || objetoSERVICIO.length() > 0) {
                                    String idPersona = "", nombreCompletoPersona = "", latitudPersona="", longitudPersona="";

                                    String descripcionServicioTotales= "";

                                    for(int x = 0 ;x < objetoSERVICIO.length();x++) {
                                        JSONObject servicio = objetoSERVICIO.getJSONObject(x);
                                        String descripcionServicio = tools.validarNulos(servicio.getString(OBJETO_DESCRIPCION));
                                        descripcionServicioTotales = descripcionServicio + ", " + descripcionServicioTotales;
                                    }

                                    resultadoBusqueda.setServicio(descripcionServicioTotales);

                                    idPersona = tools.validarNulos(object.getString(OBJETO_ID));
                                    nombreCompletoPersona = tools.validarNulos(object.getString(OBJETO_NOMBRES)) + " " + tools.validarNulos(object.getString(OBJETO_APELLIDOS));
                                    latitudPersona = tools.validarNulos(object.getString(OBJETO_LATITUD));
                                    longitudPersona = tools.validarNulos(object.getString(OBJETO_LONGITUD));

                                    insertarDatosMapas(idPersona,nombreCompletoPersona,descripcionServicioTotales, latitudPersona,longitudPersona);
                                    resultadoBusquedaList.add(resultadoBusqueda);
                                }
                            }

                            resultadoBusquedaAdapter = new ResultadoBusquedaAdapter(resultadoBusquedaList,mCtx,comunicadorAdapters);
                            recyclerView.setAdapter(resultadoBusquedaAdapter);

                            if(resultadoBusquedaList.size()>0){
                                valorData = true;
                            }

                            if(valorData){
                                dataEmpty.setVisibility(View.GONE);
                            }else{
                                dataEmpty.setVisibility(View.VISIBLE);
                            }
                            progressDialog.dismiss();

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            rubrosList = tools.validarNulos(this.getArguments().getString("VALOR_RUBROS"));
            guardarDatosTemporales(rubrosList);
        }catch (Exception e){
            e.printStackTrace();
        }

        obtenerDatosTemporales();

    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nombreUsuario = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,"");
    }

    ComunicadorAdapters comunicadorAdapters = new ComunicadorAdapters() {
        @Override
        public void comunicarResultadoPerfil(String idPersona, String nombre, String nombreCompleto, String reputacion, String imagen, String dni, String direccion, String latitud, String longitud) {
            Bundle bundle = new Bundle();
            Intent in =new Intent(getActivity(),MenuPrincipalActivity.class);
                in.putExtra("VALOR_ACCION",1);
                in.putExtra("VALOR_FRAGMENT",valorVerContacto);
                in.putExtra("ID", idPersona);
                in.putExtra("NOMBRES", nombre);
                in.putExtra("NOMBRES_COMPLETOS", nombreCompleto);
                in.putExtra("REPUTACION", reputacion);
                in.putExtra("IMAGEN", imagen);
                in.putExtra("DNI", dni);
                in.putExtra("DIRECCION", direccion);
                in.putExtra("LATITUD", latitud);
                in.putExtra("LONGITUD", longitud);
            in.putExtras(bundle);
            startActivity(in);
        }
    };

    public void guardarDatosTemporales(String ListaRubros) {
        SharedPreferences.Editor editor = mCtx.getSharedPreferences(PREFERENCIA_BUSQUEDA_SERVICIO, Context.MODE_PRIVATE).edit();
        editor.putString(PREFERENCIA_FRAGMENT_RUBROS, ListaRubros);
        editor.commit();
    }

    public void obtenerDatosTemporales(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_BUSQUEDA_SERVICIO,Context.MODE_PRIVATE);
        rubrosLista = preferencia.getString(PREFERENCIA_FRAGMENT_RUBROS,"");

        obtenerRespuestaBusqueda();
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

}
