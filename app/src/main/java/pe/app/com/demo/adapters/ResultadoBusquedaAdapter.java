package pe.app.com.demo.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.comunicators.ComunicadorResultadoXPerfil;
import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Respuesta;
import pe.app.com.demo.entity.ResultadoBusqueda;
import pe.app.com.demo.tools.GenericAlerts;

import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_APELLIDOS_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_DIRECCION_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_DNI_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LATITUD_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LONGITUD_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRES_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_PERFIL;
import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_ESTADO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_SOLICITUD;
import static pe.app.com.demo.tools.GenericTools.GET_ID_USER;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_ENVIO_SOLICITUD;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class ResultadoBusquedaAdapter extends RecyclerView.Adapter<ResultadoBusquedaAdapter.ViewHolder>{

    private List<ResultadoBusqueda> resultadoBusquedaList;
    private ComunicadorResultadoXPerfil mCommunicator;
    ComunicadorResultadoXPerfil mComminication;
    private GenericAlerts alertas = new GenericAlerts();
    private Gson gson = new Gson();
    private Context mCtx;

    int idUsuario;
    String nombreUsuario;

    public ResultadoBusquedaAdapter(List<ResultadoBusqueda> resultadoBusqueda, Context ctx,ComunicadorResultadoXPerfil communication){
        this.resultadoBusquedaList = resultadoBusqueda;
        this.mCtx = ctx;
        mCommunicator=communication;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado_busqueda, parent, false);
        ViewHolder holder = new ViewHolder(v,mCommunicator);
        //return new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ResultadoBusqueda resultadoBusqueda = resultadoBusquedaList.get(position);

        holder.textViewId.setText(resultadoBusqueda.getId());
        Glide.with(holder.imgViewSolucionador.getContext()).load(resultadoBusqueda.getImagen()).into(holder.imgViewSolucionador);
        holder.textViewNombre.setText(resultadoBusqueda.getNombres() + " " + resultadoBusqueda.getApellidos());
        holder.textViewServicio.setText(resultadoBusqueda.getServicio());
        holder.textViewAtenciones.setText(resultadoBusqueda.getAtenciones());
        holder.rtbViewReputacion.setRating(resultadoBusqueda.getRating());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_resultado_busqueda);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_ResultadoBusquedaVerContacto:
                                guardarDatosPersonalDisponible(resultadoBusqueda.getId(),resultadoBusqueda.getNombres(),resultadoBusqueda.getApellidos(),resultadoBusqueda.getDireccion(),resultadoBusqueda.getNroDocumento(),resultadoBusqueda.getLatitud(),resultadoBusqueda.getLongitud());
                                guardarSharedPreferencesValor();
                                mComminication.comunicarResultadoPerfil(
                                        resultadoBusqueda.getId(),
                                        resultadoBusqueda.getNombreUsuario(),
                                        resultadoBusqueda.getNombres() + " " + resultadoBusqueda.getApellidos(),
                                        String.valueOf(resultadoBusqueda.getRating()),
                                        resultadoBusqueda.getImagen(),
                                        resultadoBusqueda.getNroDocumento(),
                                        resultadoBusqueda.getDireccion() + " - " + resultadoBusqueda.getDistrito(),
                                        resultadoBusqueda.getLatitud(),
                                        resultadoBusqueda.getLongitud()
                                        );
                                break;
                            case R.id.menu_ResultadoBusquedaEnviar:
                                guardarDatosPersonalDisponible(resultadoBusqueda.getId(),resultadoBusqueda.getNombres(),resultadoBusqueda.getApellidos(),resultadoBusqueda.getDireccion(),resultadoBusqueda.getNroDocumento(),resultadoBusqueda.getLatitud(),resultadoBusqueda.getLongitud());
                                obtenerDatosUsuario();
                                resultadoBusquedaList.remove(position);
                                enviarPeticionSolicitud(Integer.valueOf(resultadoBusqueda.getIdSolicitud()),Integer.valueOf(resultadoBusqueda.getId()),5);
                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultadoBusquedaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewId;
        public ImageView imgViewSolucionador;
        public TextView textViewNombre;
        public TextView textViewServicio;
        public TextView textViewAtenciones;
        public RatingBar rtbViewReputacion;
        public TextView buttonViewOption;

        public ViewHolder(View itemView, ComunicadorResultadoXPerfil Communicator) {
            super(itemView);

            textViewId = (TextView) itemView.findViewById(R.id.txtIdSocio);
            imgViewSolucionador = (ImageView) itemView.findViewById(R.id.imagenSolucionador);
            textViewNombre = (TextView) itemView.findViewById(R.id.txtNombreSolucionador);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewAtenciones = (TextView) itemView.findViewById(R.id.txtAtenciones);
            rtbViewReputacion = (RatingBar) itemView.findViewById(R.id.rntgReputacion);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);

            mComminication=Communicator;
        }
    }

    public void guardarDatosPersonalDisponible(String idPersonal, String nomPersonal, String apePersonal, String dirPersonal, String dniPersonal, String latPersonal, String longPersonal){
        SharedPreferences.Editor editor = mCtx.getSharedPreferences(PREFERENCIA_PERSONAL, mCtx.MODE_PRIVATE).edit();
        editor.putString(PREFERENCIA_ID_PERSONAL, idPersonal);
        editor.putString(PREFERENCIA_NOMBRES_PERSONAL, nomPersonal);
        editor.putString(PREFERENCIA_APELLIDOS_PERSONAL, apePersonal);
        editor.putString(PREFERENCIA_DIRECCION_PERSONAL, dirPersonal);
        editor.putString(PREFERENCIA_DNI_PERSONAL, dniPersonal);
        editor.putString(PREFERENCIA_LATITUD_PERSONAL, latPersonal);
        editor.putString(PREFERENCIA_LONGITUD_PERSONAL, longPersonal);
        editor.commit();
        }

    public void guardarSharedPreferencesValor() {
        SharedPreferences.Editor editor = mCtx.getSharedPreferences(PREFERENCIA_PERFIL, mCtx.MODE_PRIVATE).edit();
        editor.putString(PREFERENCIA_VALOR_PERFIL, PREFERENCIA_VALOR_PERFIL);
        editor.commit();
    }

    public void enviarPeticionSolicitud(final int IdSolicitud, final int IdUsuario, final int IdEstado) {

        final String url =  URL_APP + BASE_URL + BASE_ENVIO_SOLICITUD + GET_INICIO + GET_ID_SOLICITUD + IdSolicitud + GET_CONTINUO +
                GET_ID_USER + IdUsuario + GET_CONTINUO + GET_ID_ESTADO + IdEstado;

        StringRequest respuestaEnvioSolicitud = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        if(Response!=null) {
                            try {
                                Respuesta respuesta = gson.fromJson(Response, Respuesta.class);
                                if(respuesta.getMensaje()!=null) {
                                    alertas.mensajeInfo("Éxito",respuesta.getMensaje(),mCtx);
                                    notifyDataSetChanged();
                                }else{
                                    alertas.mensajeInfo("Fallo enviar calificacion",respuesta.getMensaje(),mCtx);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                alertas.mensajeInfo("Fallo enviar calificacion","None",mCtx);
                            }
                        }else{
                            Respuesta respuesta = gson.fromJson(Response, Respuesta.class);
                            alertas.mensajeInfo("Fallo enviar calificacion",respuesta.getMensaje(),mCtx);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                alertas.mensajeInfo("Fallo enviar solicitud","Error Desconocido",mCtx);
            }
        });

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaEnvioSolicitud);
    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nombreUsuario = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,"");
    }
}
