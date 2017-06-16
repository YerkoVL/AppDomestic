package pe.app.com.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.app.com.demo.adapters.HistorialAdapter;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Historial;
import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static android.content.ContentValues.TAG;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_CALIFICACION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_COMENTARIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESCRIPCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESC_ESTADO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_FIN;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_INICIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_SOLICITUD;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FOTO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_ESTADO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_SOCIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_RUBRO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_SOCIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericTools.GET_ESTADO_PARA_HISTORIAL;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_USER;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_SOLICITUDES;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ContenidoHistorial extends Fragment {

    private RecyclerView recyclerView;
    private HistorialAdapter historialAdapter;
    private List<Historial> historialList;

    ProgressDialog progressDialog = null;
    Gson gson = new Gson();
    GenericAlerts alertas = new GenericAlerts();
    GenericTools tools = new GenericTools();
    Context mCtx;

    String nombreUsuario;
    int idUsuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_historial, container, false);

        mCtx = rootView.getContext();

        progressDialog = new ProgressDialog(mCtx);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerHistorial);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        historialList = new ArrayList<>();

        obtenerDatosUsuario();
        obtenerHistorialUsuario(nombreUsuario);

        //cargaRecyclerHistorial();

        return rootView;

    }

    public void obtenerHistorialUsuario(String nomUsuario){

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_SOLICITUDES + GET_INICIO + GET_USER + nomUsuario;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaSolicitud = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);
                                Historial historial = new Historial();

                                try {
                                    historial.setId(tools.validarNulos(object.getString(OBJETO_ID)));
                                    historial.setFechaInicio(tools.validarNulos(object.getString(OBJETO_FECHA_INICIO)));
                                    historial.setFechaFin(tools.validarNulos(object.getString(OBJETO_FECHA_FIN)));
                                    historial.setServicio(tools.validarNulos(object.getString(OBJETO_SERVICIO)));
                                    historial.setCalificacion(tools.validarNulos(object.getString(OBJETO_CALIFICACION)));
                                    historial.setFechaSolicitud(tools.validarNulos(object.getString(OBJETO_FECHA_SOLICITUD)));
                                    historial.setIdSocio(tools.validarNulos(object.getString(OBJETO_ID_SOCIO)));
                                    historial.setSocio(tools.validarNulos(object.getString(OBJETO_SOCIO)));
                                    historial.setFoto(tools.validarNulos(object.getString(OBJETO_FOTO)));
                                    historial.setComentario(tools.validarNulos(object.getString(OBJETO_COMENTARIO)));
                                    historial.setIdEstado(tools.validarNulos(object.getString(OBJETO_ID_ESTADO)));
                                    historial.setDesc_Estado(tools.validarNulos(object.getString(OBJETO_DESC_ESTADO)));

                                    JSONArray objetoRubro = object.getJSONArray(OBJETO_RUBRO);
                                    JSONObject rubro = objetoRubro.getJSONObject(0);

                                    if (objetoRubro != null || objetoRubro.length() > 0) {
                                        historial.setIdRubro(tools.validarNulos(rubro.getString(OBJETO_ID)));
                                        historial.setDescripcionRubro(tools.validarNulos(rubro.getString(OBJETO_DESCRIPCION)));
                                        historial.setIdEstadoRubro(tools.validarNulos(rubro.getString(OBJETO_ID_ESTADO)));
                                        historial.setDescripcionRubro(tools.validarNulos(rubro.getString(OBJETO_DESC_ESTADO)));
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                if(historial.getIdEstado().equals(GET_ESTADO_PARA_HISTORIAL)){
                                    historialList.add(historial);
                                }
                            }

                            historialAdapter = new HistorialAdapter(historialList,mCtx);
                            recyclerView.setAdapter(historialAdapter);
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

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nombreUsuario = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,"");
    }

}
