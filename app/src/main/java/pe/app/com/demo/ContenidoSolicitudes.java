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

import pe.app.com.demo.adapters.SolicitudAdapter;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Solicitud;
import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static android.content.ContentValues.TAG;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_CALIFICACION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DESC_ESTADO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_FIN;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_INICIO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_FECHA_SOLICITUD;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_ESTADO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_RUBRO;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_SERVICIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericTools.GET_ESTADO_PARA_SOLICITUDES;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_USER;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_SOLICITUDES;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ContenidoSolicitudes extends Fragment {

    private RecyclerView recyclerView;
    private SolicitudAdapter solicitudAdapter;
    private List<Solicitud> solicitudList;

    ProgressDialog progressDialog = null;
    Gson gson = new Gson();
    GenericAlerts alertas = new GenericAlerts();
    GenericTools tools = new GenericTools();
    Context mCtx;

    int idUsuario = 0;
    String nombreUsuario = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_solicitudes, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerSolicitudes);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(mCtx);

        solicitudList = new ArrayList<>();

        obtenerDatosUsuario();
        obtenerSolicitudes(nombreUsuario);

        return rootView;

    }

    public void obtenerSolicitudes(String nomUsuario) {

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

                                Solicitud solicitud = new Solicitud(
                                        tools.validarNulos(object.getString(OBJETO_ID)),
                                                tools.validarNulos(object.getString(OBJETO_FECHA_INICIO)),
                                                        tools.validarNulos(object.getString(OBJETO_FECHA_FIN)),
                                                                tools.validarNulos(object.getString(OBJETO_SERVICIO)),
                                                                        tools.validarNulos(object.getString(OBJETO_CALIFICACION)),
                                                                                tools.validarNulos(object.getString(OBJETO_FECHA_SOLICITUD)),
                                                                                        tools.validarNulos(object.getString(OBJETO_RUBRO)),
                                                                                                tools.validarNulos(object.getString(OBJETO_ID_ESTADO)),
                                                                                                        tools.validarNulos(object.getString(OBJETO_DESC_ESTADO)));

                                if(solicitud.getIdEstado().equals(GET_ESTADO_PARA_SOLICITUDES)) {
                                    solicitudList.add(solicitud);
                                }
                            }

                            solicitudAdapter = new SolicitudAdapter(solicitudList, mCtx);
                            recyclerView.setAdapter(solicitudAdapter);
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
