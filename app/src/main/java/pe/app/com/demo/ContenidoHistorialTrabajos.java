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
import android.widget.LinearLayout;

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

import pe.app.com.demo.adapters.HistorialTrabajosAdapter;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.HistorialTrabajos;
import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static android.content.ContentValues.TAG;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_CALIFICACION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_COMENTARIO;
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
import static pe.app.com.demo.tools.GenericTools.GET_ID_SOCIO;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_HISTORIAL_TRABAJO_SOCIO;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ContenidoHistorialTrabajos extends Fragment {

    private RecyclerView recyclerView;
    private HistorialTrabajosAdapter historialTrabajosAdapter;
    private List<HistorialTrabajos> historialTrabajosList;

    ProgressDialog progressDialog = null;
    Gson gson = new Gson();
    GenericAlerts alertas = new GenericAlerts();
    GenericTools tools = new GenericTools();
    Context mCtx;

    String nombreUsuario;
    int idUsuario, idSocio=0;

    LinearLayout dataEmpty;
    boolean valorData =false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_historial_trabajos, container, false);

        mCtx = rootView.getContext();

        progressDialog = new ProgressDialog(mCtx);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        dataEmpty = (LinearLayout) rootView.findViewById(R.id.lyDataEmpty);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerHistorialTrabajos);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        historialTrabajosList = new ArrayList<>();

        obtenerDatosUsuario();
        obtenerHistorialTrabajos();

        return rootView;

    }

    public void obtenerHistorialTrabajos() {

        try {
            idSocio = this.getArguments().getInt("ID_SOCIO");
        }catch (Exception e){
            e.printStackTrace();
        }

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_HISTORIAL_TRABAJO_SOCIO + GET_INICIO + GET_ID_SOCIO + idSocio;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaSolicitud = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);

                                HistorialTrabajos historialTrabajos = new HistorialTrabajos(
                                        tools.validarNulos(object.getString(OBJETO_ID)),
                                        tools.validarNulos(object.getString(OBJETO_FECHA_INICIO)),
                                        tools.validarNulos(object.getString(OBJETO_FECHA_FIN)),
                                        tools.validarNulos(object.getString(OBJETO_SERVICIO)),
                                        tools.validarNulos(object.getString(OBJETO_CALIFICACION)),
                                        tools.validarNulos(object.getString(OBJETO_FECHA_SOLICITUD)),
                                        tools.validarNulos(object.getString(OBJETO_RUBRO)),
                                        tools.validarNulos(object.getString(OBJETO_ID_SOCIO)),
                                        tools.validarNulos(object.getString(OBJETO_SOCIO)),
                                        tools.validarNulos(object.getString(OBJETO_FOTO)),
                                        tools.validarNulos(object.getString(OBJETO_COMENTARIO)),
                                        tools.validarNulos(object.getString(OBJETO_ID_ESTADO)),
                                        tools.validarNulos(object.getString(OBJETO_DESC_ESTADO)));

                                    historialTrabajosList.add(historialTrabajos);
                            }

                            historialTrabajosAdapter = new HistorialTrabajosAdapter(historialTrabajosList, mCtx);
                            recyclerView.setAdapter(historialTrabajosAdapter);

                            if(historialTrabajosList.size()>0){
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

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nombreUsuario = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,"");
    }

}
