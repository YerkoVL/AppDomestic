package pe.app.com.demo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import pe.app.com.demo.conexion.Singleton;
import pe.app.com.demo.entity.Respuesta;
import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static pe.app.com.demo.tools.GenericTools.GET_COMENTARIO;
import static pe.app.com.demo.tools.GenericTools.GET_CONTINUO;
import static pe.app.com.demo.tools.GenericTools.GET_ESPACIO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_SOCIO;
import static pe.app.com.demo.tools.GenericTools.GET_ID_SOLICITUD;
import static pe.app.com.demo.tools.GenericTools.GET_ID_USER;
import static pe.app.com.demo.tools.GenericTools.GET_INICIO;
import static pe.app.com.demo.tools.GenericTools.GET_USER;
import static pe.app.com.demo.tools.GenericTools.GET_VALOR;
import static pe.app.com.demo.tools.GenericTools.URL_APP;
import static pe.app.com.demo.tools.GenericUrls.BASE_ENVIO_CALIFICACION;
import static pe.app.com.demo.tools.GenericUrls.BASE_URL;

public class CustomDialog extends DialogFragment{

    LayoutInflater inflater;
    View v;

    TextView nombreCompleto;
    RadioButton radioExcelente;
    RadioButton radioMuyBueno;
    RadioButton radioBueno;
    RadioButton radioMalo;
    RadioButton radioMuyMalo;

    EditText comentario;

    Gson gson = new Gson();
    GenericTools tools = new GenericTools();
    GenericAlerts alertas = new GenericAlerts();
    Context mCtx;

    int idSolicitud, idUsuario;
    String nombreSocio;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.dialogo_finalizar_solicitud,null);

        mCtx = getContext();

        nombreCompleto = (TextView) v.findViewById(R.id.txtNombrePersonal);
        radioExcelente = (RadioButton) v.findViewById(R.id.rdbExcelente);
        radioMuyBueno = (RadioButton) v.findViewById(R.id.rdbMuyBueno);
        radioBueno = (RadioButton) v.findViewById(R.id.rdbBueno);
        radioMalo = (RadioButton) v.findViewById(R.id.rdbMalo);
        radioMuyMalo = (RadioButton) v.findViewById(R.id.rdbMuyMalo);
        comentario = (EditText) v.findViewById(R.id.txtComentarios);

        try{
            idSolicitud = getArguments().getInt("ID_SOLICITUD");
            idUsuario = getArguments().getInt("ID_PERSONA");
            nombreSocio = getArguments().getString("NOMBRES");

        }catch (Exception e){
            e.printStackTrace();
        }

        nombreCompleto.setText(nombreSocio);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int calificacion = valorCheckeado();
                String comentarios = obtenerComentario();
                enviarCalificacion(idSolicitud,idUsuario,calificacion,comentarios);
            }
        });

        return builder.create();
    }

    public String obtenerComentario(){
        String comentarios;

        String comentarioPrefinal = comentario.getText().toString();
        comentarios = comentarioPrefinal.replace(" ",GET_ESPACIO);

        tools.validarNulos(comentarios);
        return comentarios;
    }

    public int valorCheckeado(){
        int valor = 0;
        if(radioExcelente.isChecked()){
            valor = 5;
        }else{
            if(radioMuyBueno.isChecked()){
                valor = 4;
            }else{
                if(radioBueno.isChecked()){
                    valor = 3;
                }else{
                    if(radioMalo.isChecked()){
                        valor = 2;
                    }else{
                        if(radioMuyMalo.isChecked()){
                            valor = 1;
                        }
                    }
                }
            }
        }
        return valor;
    }

    public void enviarCalificacion(final int IdSolicitud, final int IdUsuario, final int Valor, final String Comentario) {

        final String url =  URL_APP + BASE_URL + BASE_ENVIO_CALIFICACION + GET_INICIO + GET_ID_SOLICITUD + IdSolicitud + GET_CONTINUO +
                            GET_ID_USER + IdUsuario + GET_CONTINUO + GET_VALOR + Valor + GET_CONTINUO + GET_COMENTARIO + Comentario;

        StringRequest respuestaCalificacion = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String Response) {
                        if(Response!=null) {
                            try {
                                Respuesta respuesta = gson.fromJson(Response, Respuesta.class);
                                if(respuesta.getMensaje()!=null) {
                                    alertas.mensajeInfo("Éxito",respuesta.getMensaje(),mCtx);
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

        Singleton.getInstance(mCtx).addToRequestQueue(respuestaCalificacion);
    }
}