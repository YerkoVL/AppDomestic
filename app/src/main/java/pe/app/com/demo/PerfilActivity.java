package pe.app.com.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pe.app.com.demo.comunicators.ComunicadorPerfilXHistorialTrabajo;
import pe.app.com.demo.comunicators.ComunicadorPerfilXMapa;

import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DIRECCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_ID_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NRO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_IMAGEN_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LATITUD_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LONGITUD_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_COMPLETO_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_RATING_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;

public class PerfilActivity extends Fragment {
    Context mCtx;

    String nombresPersona , dirPersona, dniPersona, latitudPersona, longitudPersona, nombresCompletoPersona, imagenRecPersona, reputacionPre;
    int idPersona, idPerfil;
    Float reputacionPersona = 0f;

    TextView direccionUsuario,dniUsuario, nombreUsuario,reputacion;
    RatingBar valoracion;
    ImageView imagen;
    LinearLayout linearLayoutPerfil;
    ImageButton verUbicacion, historialTrabajos;
    ComunicadorPerfilXHistorialTrabajo comunicador;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_perfil, container, false);
        mCtx = rootView.getContext();

        direccionUsuario = (TextView) rootView.findViewById(R.id.txtDireccionUsuario);
        dniUsuario = (TextView) rootView.findViewById(R.id.txtDniUsuario);
        nombreUsuario = (TextView) rootView.findViewById(R.id.txtNombreUsuario);
        imagen = (ImageView) rootView.findViewById(R.id.imagenUsuario);
        reputacion = (TextView) rootView.findViewById(R.id.txtReputacion);
        valoracion = (RatingBar) rootView.findViewById(R.id.rating);
        linearLayoutPerfil = (LinearLayout) rootView.findViewById(R.id.lyOcultarPerfil);
        verUbicacion = (ImageButton) rootView.findViewById(R.id.btnVerUbicacion);
        historialTrabajos = (ImageButton) rootView.findViewById(R.id.btnHistorialTrabajos);

        comunicador = comunicadorPerfilXHistorialTrabajo;

        try {
            idPersona = Integer.valueOf(this.getArguments().getString("ID"));
            nombresPersona = this.getArguments().getString("NOMBRES");
            nombresCompletoPersona = this.getArguments().getString("NOMBRES_COMPLETOS");
            reputacionPersona = this.getArguments().getFloat("REPUTACION");
            imagenRecPersona = getArguments().getString("IMAGEN");
            dniPersona = getArguments().getString("DNI");
            dirPersona = getArguments().getString("DIRECCION");
            latitudPersona = getArguments().getString("LATITUD");
            longitudPersona = getArguments().getString("LONGITUD");
        }catch (Exception e){
            e.printStackTrace();
            obtenerDatosUsuario();
        }

        asignarDatosUsuario();

        verUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comunicadorPerfilXMapa.comunicarPerfil(String.valueOf(idPersona), 1,"PERFIL_MAPA","cliente",nombresCompletoPersona,latitudPersona,longitudPersona);
            }
        });

        historialTrabajos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comunicador.comunicarHistorialTrabajo(1,"PERFIL_HISTORIAL",idPersona,nombresCompletoPersona);
            }
        });

        return rootView;
    }

    public void obtenerDatosUsuario(){
        try {
            SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO, Context.MODE_PRIVATE);
            idPersona = preferencia.getInt(PREFERENCIA_ID_USUARIO, 0);
            idPerfil = preferencia.getInt(OBJETO_ID_PERFIL, 0);
            nombresPersona = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO, "");
            //preferencia.getString(PREFERENCIA_PASS_USUARIO, "");
            nombresCompletoPersona = preferencia.getString(PREFERENCIA_NOMBRE_COMPLETO_USUARIO, "");
            reputacionPre = preferencia.getString(PREFERENCIA_RATING_USUARIO, "");
            if(reputacionPre.equals("") || !reputacionPre.equals("null")) {
                reputacionPersona = Float.valueOf(reputacionPre + "f");
            }
            imagenRecPersona = preferencia.getString(PREFERENCIA_IMAGEN_USUARIO, "");
            dniPersona = preferencia.getString(OBJETO_NRO_DOCUMENTO, "");
            dirPersona = preferencia.getString(OBJETO_DIRECCION, "");
            latitudPersona = preferencia.getString(PREFERENCIA_LATITUD_USUARIO, "");
            longitudPersona = preferencia.getString(PREFERENCIA_LONGITUD_USUARIO, "");
        }catch (Exception i){
            i.printStackTrace();
        }
    }

    public void asignarDatosUsuario(){
        direccionUsuario.setText(dirPersona);
        dniUsuario.setText(dniPersona);
        nombreUsuario.setText(nombresCompletoPersona);
        Glide.with(mCtx).load(imagenRecPersona).into(imagen);
        reputacion.setText(reputacionPre);
        valoracion.setRating(Float.valueOf(reputacionPersona));

        if(idPerfil == 1){
            linearLayoutPerfil.setVisibility(View.GONE);
        }
    }

    ComunicadorPerfilXHistorialTrabajo comunicadorPerfilXHistorialTrabajo = new ComunicadorPerfilXHistorialTrabajo() {
        @Override
        public void comunicarHistorialTrabajo(int valorACCION,String valorFRAGMENT, int idSocio, String nombre) {
            Bundle bundle = new Bundle();
            Intent in =new Intent(getActivity(),MenuPrincipalActivity.class);
                in.putExtra("VALOR_ACCION",valorACCION);
                in.putExtra("VALOR_FRAGMENT",valorFRAGMENT);
                in.putExtra("ID_SOCIO",idSocio);
                in.putExtra("NOMBRE_SOCIO",nombresCompletoPersona);
                in.putExtras(bundle);
            startActivity(in);
        }

    };

    ComunicadorPerfilXMapa comunicadorPerfilXMapa = new ComunicadorPerfilXMapa() {
        @Override
        public void comunicarPerfil(String valorID,int valorACCION,String valorFRAGMENT, String rubros, String nombre, String latitud, String longitud) {
            Bundle bundle = new Bundle();
            Intent in =new Intent(getActivity(),MenuPrincipalActivity.class);
            in.putExtra("VALOR_ACCION",valorACCION);
            in.putExtra("VALOR_FRAGMENT",valorFRAGMENT);
            in.putExtra("VALOR_ID",valorID);
            in.putExtra("VALOR_RUBROS",rubros);
            in.putExtra("VALOR_NOMBRE",nombre);
            in.putExtra("VALOR_LATITUD",latitud);
            in.putExtra("VALOR_LONGITUD",longitud);
            in.putExtras(bundle);
            startActivity(in);
        }
    };
}
