package pe.app.com.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static pe.app.com.demo.tools.GenericEstructure.OBJETO_DIRECCION;
import static pe.app.com.demo.tools.GenericEstructure.OBJETO_NRO_DOCUMENTO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_APELLIDOS_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_DIRECCION_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_DNI_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_IMAGEN_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LATITUD_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LATITUD_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LONGITUD_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LONGITUD_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRES_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_COMPLETO_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PASS_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_RATING_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_PERFIL;

public class PerfilActivity extends Fragment {
    Toolbar toolbar;
    Context mCtx;

    String idPersona, nombresPersona ,apPersona, dirPersona, dniPersona, latitudPersona, longitudPersona, nombresCompletoPersona, imagenRecPersona;
    Float reputacionPersona;

    TextView direccionUsuario,dniUsuario, nombreUsuario,reputacion;
    RatingBar valoracion;
    ImageView imagenUsuario;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_perfil, container, false);
        mCtx = rootView.getContext();

        direccionUsuario = (TextView) rootView.findViewById(R.id.txtDireccionUsuario);
        dniUsuario = (TextView) rootView.findViewById(R.id.txtDniUsuario);
        nombreUsuario = (TextView) rootView.findViewById(R.id.txtNombreUsuario);
        imagenUsuario = (ImageView) rootView.findViewById(R.id.imagenUsuario);
        reputacion = (TextView) rootView.findViewById(R.id.txtReputacion);
        valoracion = (RatingBar) rootView.findViewById(R.id.rating);

        Glide.with(mCtx).load(imagenRecPersona).into(imagenUsuario);

        obtenerDatosUsuario();

        return rootView;
    }

    public void obtenerDatosUsuario(){
        try{
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREFERENCIA_NOMBRE_COMPLETO_USUARIO,Context.MODE_PRIVATE);
            idPersona = sharedPreferences.getString(PREFERENCIA_ID_PERSONAL, "");
                nombresPersona = sharedPreferences.getString(PREFERENCIA_NOMBRES_PERSONAL, "");
                apPersona = sharedPreferences.getString(PREFERENCIA_APELLIDOS_PERSONAL, "");
            nombresPersona = nombresPersona + apPersona;
            dirPersona = sharedPreferences.getString(PREFERENCIA_DIRECCION_PERSONAL, "");
            dniPersona = sharedPreferences.getString(PREFERENCIA_DNI_PERSONAL, "");
                String ratingPre = sharedPreferences.getString(PREFERENCIA_RATING_USUARIO, "");
            reputacionPersona = Float.valueOf(ratingPre + "f");
            imagenRecPersona = sharedPreferences.getString(PREFERENCIA_IMAGEN_USUARIO, "");
            latitudPersona = sharedPreferences.getString(PREFERENCIA_LATITUD_PERSONAL, "");
            longitudPersona = sharedPreferences.getString(PREFERENCIA_LONGITUD_PERSONAL, "");

        }catch (Exception e){
            SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
            idPersona = preferencia.getString(PREFERENCIA_ID_USUARIO, "");
            nombresPersona = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO, "");
            //preferencia.getString(PREFERENCIA_PASS_USUARIO, "");
            nombresCompletoPersona = preferencia.getString(PREFERENCIA_NOMBRE_COMPLETO_USUARIO, "");
                String reputacionPre = preferencia.getString(PREFERENCIA_RATING_USUARIO,"");
            reputacionPersona = Float.valueOf(reputacionPre + "f");
            imagenRecPersona = preferencia.getString(PREFERENCIA_IMAGEN_USUARIO,"");
            dniPersona = preferencia.getString(OBJETO_NRO_DOCUMENTO,"");
            dirPersona = preferencia.getString(OBJETO_DIRECCION, "");
            latitudPersona = preferencia.getString(PREFERENCIA_LATITUD_USUARIO, "");
            longitudPersona = preferencia.getString(PREFERENCIA_LONGITUD_USUARIO, "");

        }
    }

    public void leerSharedPreferencesValor() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREFERENCIA_PERFIL, mCtx.MODE_PRIVATE);
        String valorPerfil = sharedPreferences.getString(PREFERENCIA_VALOR_PERFIL,PREFERENCIA_VALOR_PERFIL);
    }
}
