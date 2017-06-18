package pe.app.com.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.app.com.demo.tools.GenericAlerts;
import pe.app.com.demo.tools.GenericTools;

import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRE_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_USUARIO;

public class ContenidoDetalleSolicitudes extends Fragment {
    GenericAlerts alertas = new GenericAlerts();
    GenericTools tools = new GenericTools();
    Context mCtx;

    TextView txtFechaSolicitud;
    TextView txtServicios;
    TextView txtRubros;
    TextView txtFechaInicio;
    TextView txtFechaFin;

    int idUsuario = 0;
    String nombreUsuario = "", idSolicitud, fechaSolicitud, servicios, rubros, fechaInicio, fechaFin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_detalle_solicitudes, container, false);
        mCtx = rootView.getContext();

        txtFechaSolicitud = (TextView) rootView.findViewById(R.id.txtFechaSolicitud);
        txtServicios = (TextView) rootView.findViewById(R.id.txtServicio);
        txtRubros = (TextView) rootView.findViewById(R.id.txtRubro);
        txtFechaInicio = (TextView) rootView.findViewById(R.id.txtFechaInicio);
        txtFechaFin = (TextView) rootView.findViewById(R.id.txtFechaFin);

        try {
            idSolicitud = this.getArguments().getString("ID");
            fechaSolicitud = this.getArguments().getString("FECHA_SOLICITUD");
            servicios = this.getArguments().getString("SERVICIOS");
            rubros = this.getArguments().getString("RUBROS");
            fechaInicio = getArguments().getString("INICIO");
            fechaFin = getArguments().getString("FIN");
        }catch (Exception e){
            e.printStackTrace();
            obtenerDatosUsuario();
        }

        asignarDatosUsuario();

        obtenerDatosUsuario();

        return rootView;
    }

    public void obtenerDatosUsuario(){
        SharedPreferences preferencia = mCtx.getSharedPreferences(PREFERENCIA_USUARIO,Context.MODE_PRIVATE);
        idUsuario = preferencia.getInt(PREFERENCIA_ID_USUARIO,0);
        nombreUsuario = preferencia.getString(PREFERENCIA_NOMBRE_USUARIO,"");
    }

    public void asignarDatosUsuario(){
        txtFechaSolicitud.setText(fechaSolicitud);
        txtServicios.setText(servicios);
        txtRubros.setText(rubros);
        txtFechaInicio.setText(fechaInicio);
        txtFechaFin.setText(fechaFin);
    }
}