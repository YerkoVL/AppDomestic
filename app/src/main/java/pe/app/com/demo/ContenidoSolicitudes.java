package pe.app.com.demo;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        obtenerSolicitudes("demo");

        return rootView;

    }

    public void obtenerSolicitudes(String nomUsuario) {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_SOLICITUDES + GET_USER + nomUsuario;

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
                                        tools.validarNulos(object.getString("Id")),
                                                tools.validarNulos(object.getString("FechaInicio")),
                                                        tools.validarNulos(object.getString("FechaFin")),
                                                                tools.validarNulos(object.getString("Servicio")),
                                                                        tools.validarNulos(object.getString("Calificacion")),
                                                                                tools.validarNulos(object.getString("FechaSolicitud")),
                                                                                        tools.validarNulos(object.getString("Rubro")),
                                                                                                tools.validarNulos(object.getString("IdEstado")),
                                                                                                        tools.validarNulos(object.getString("Desc_Estado")));

                                solicitudList.add(solicitud);
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
}
