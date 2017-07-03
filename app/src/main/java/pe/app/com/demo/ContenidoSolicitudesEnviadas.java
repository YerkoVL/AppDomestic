package pe.app.com.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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

import pe.app.com.demo.adapters.SolicitudEnviadaAdapter;
import pe.app.com.demo.comunicators.ComunicadorHistorialXCalificación;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.SolicitudesEnviadas;
import pe.app.com.demo.tools.GenericAlerts;
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
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_DEPARTAMENTO;
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
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_SOLICITUD;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_CONSULTA_SOLICITUDES_ENVIADA;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ContenidoSolicitudesEnviadas extends Fragment {

    private RecyclerView recyclerView;
    private SolicitudEnviadaAdapter solicitudEnviadaAdapter;
    private List<SolicitudesEnviadas> solicitudesEnviadasList;

    ProgressDialog progressDialog = null;
    Gson gson = new Gson();
    GenericAlerts alertas = new GenericAlerts();
    GenericTools tools = new GenericTools();
    Context mCtx;

    LinearLayout dataEmpty;

    int idUsuario = 0;
    String nombreUsuario = "", idSolicitud;
    boolean valorData=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_solicitudes, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerSolicitudes);
        recyclerView.setHasFixedSize(true);

        dataEmpty = (LinearLayout) rootView.findViewById(R.id.lyDataEmpty);

        recyclerView.setLayoutManager(linearLayoutManager);
        progressDialog = new ProgressDialog(mCtx);

        try {
            idSolicitud = this.getArguments().getString("VALOR_ID_SOLICITUD");
        }catch (Exception e){
            e.printStackTrace();
            obtenerDatosUsuario();
        }

        solicitudesEnviadasList = new ArrayList<>();

        obtenerDatosUsuario();
        obtenerSolicitudes(idSolicitud);

        return rootView;

    }

    public void obtenerSolicitudes(String idSolicitud) {

        final String url = URL_APP + BASE_URL + BASE_CONSULTA_SOLICITUDES_ENVIADA + GET_INICIO + GET_ID_SOLICITUD + idSolicitud;

        progressDialog.show();
        progressDialog.setContentView(R.layout.content_progress_action);

        JsonArrayRequest respuestaSolicitudesEnviadas = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);

                                SolicitudesEnviadas solicitudesEnviadas = new SolicitudesEnviadas(
                                        tools.validarNulos(object.getString(OBJETO_ID)),
                                        tools.validarNulos(object.getString("IdUsuario")),
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
                                        tools.validarNulos(object.getString(OBJETO_LATITUD)),
                                        tools.validarNulos(object.getString(OBJETO_LONGITUD)),
                                        tools.validarNulos(object.getString(OBJETO_ID_PERFIL)),
                                        tools.validarNulos(object.getString(OBJETO_PERFIL)),
                                        tools.validarNulos(object.getString(OBJETO_ID_DEPARTAMENTO)),
                                        tools.validarNulos(object.getString("Dpto")),
                                        tools.validarNulos(object.getString(OBJETO_ID_DISTRITO)),
                                        tools.validarNulos(object.getString(OBJETO_DISTRITO)),
                                        tools.validarNulos(object.getString(OBJETO_FECHA_LOGEO)),
                                        tools.validarNulos(object.getString(OBJETO_BIENVENIDA)),
                                        tools.validarNulos(object.getString(OBJETO_ATENCIONES)),
                                        tools.validarNulos(object.getString(OBJETO_RATING)),
                                        tools.validarNulos(object.getString(OBJETO_IMAGEN)),
                                        tools.validarNulos(object.getString("SERVICIO")),
                                        tools.validarNulos(object.getString("RazonSocial")),
                                        tools.validarNulos(object.getString("NombreComercial")),
                                        tools.validarNulos(object.getString("Key")),
                                        tools.validarNulos(object.getString(OBJETO_ID_ESTADO)),
                                        tools.validarNulos(object.getString(OBJETO_DESC_ESTADO))
                                );

                                JSONArray objetoServicio = object.getJSONArray("SERVICIO");

                                if (objetoServicio != null || objetoServicio.length() > 0) {
                                    String descripcionServiciosTotales= "";
                                    for(int x = 0 ;x < objetoServicio.length();x++) {
                                        JSONObject servicio = objetoServicio.getJSONObject(x);
                                        String descripcionRubro = tools.validarNulos(servicio.getString(OBJETO_DESCRIPCION));
                                        if(objetoServicio.length()>1) {
                                            descripcionServiciosTotales = descripcionRubro + ", " + descripcionServiciosTotales;
                                        }else{
                                            descripcionServiciosTotales = descripcionRubro;
                                        }
                                    }
                                    solicitudesEnviadas.setSERVICIO(descripcionServiciosTotales);
                                }

                                solicitudesEnviadasList.add(solicitudesEnviadas);
                            }

                            solicitudEnviadaAdapter = new SolicitudEnviadaAdapter(solicitudesEnviadasList, mCtx, comunicadorHistorialXCalificación);
                            recyclerView.setAdapter(solicitudEnviadaAdapter);

                            if(solicitudesEnviadasList.size()>0){
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

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaSolicitudesEnviadas);
    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nombreUsuario = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,"");
    }

    ComunicadorHistorialXCalificación comunicadorHistorialXCalificación = new ComunicadorHistorialXCalificación() {
        @Override
        public void comunicarHistorial(String valorNOMBRE, int valorIDPERSONA, int valorIDSOLICITUD) {
            Bundle bundle = new Bundle();
            FragmentActivity activity = (FragmentActivity)(mCtx);
            FragmentManager fm = activity.getSupportFragmentManager();
            CustomDialog alertDialog = new CustomDialog();
            bundle.putInt("ID_PERSONA",valorIDPERSONA);
            bundle.putString("NOMBRES",valorNOMBRE);
            bundle.putInt("ID_SOLICITUD",valorIDSOLICITUD);
            alertDialog.setArguments(bundle);
            alertDialog.show(fm, "fragment_alert");
        }
    };
}
