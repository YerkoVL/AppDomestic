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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.app.com.demo.adapters.ResultadoBusquedaAdapter;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.ResultadoBusqueda;
import pe.app.com.demo.tools.GenericTools;

import static android.content.ContentValues.TAG;
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

    Context mCtx;

    ProgressDialog progressDialog = null;

    GenericTools tools = new GenericTools();

    int idUsuario = 0;
    String nombreUsuario = "";
    String rubrosLista = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_resultado_busqueda, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerResultadoBusqueda);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(mCtx);

        resultadoBusquedaList = new ArrayList<>();

        obtenerDatosUsuario();
        obtenerRespuestaBusqueda();

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

                                //ResultadoBusqueda solicitud = new ResultadoBusqueda(
                                //       tools.validarNulos(object.getString()));

                                //resultadoBusquedaList.add(solicitud);
                            }

                            resultadoBusquedaAdapter = new ResultadoBusquedaAdapter(resultadoBusquedaList,mCtx);
                            recyclerView.setAdapter(resultadoBusquedaAdapter);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void recibirListaRubros(String Rubros){
        rubrosLista = Rubros;
    }
}
