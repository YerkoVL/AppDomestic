package pe.app.com.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ContenidoDetalleHistorial extends Fragment {
    Context mCtx;

    ImageView ImagenPersona;
    String imagen, nombreUsuario, rubros, fechaInicio, fechaFin, servicios, comentarios, calificacion;
    TextView NombreUsuario, Rubros, FechaInicio, FechaFin, Servicios, Comentarios;
    RatingBar Calificacion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_detalle_historial, container, false);
        mCtx = rootView.getContext();

        ImagenPersona = (ImageView) rootView.findViewById(R.id.imagenUsuario);
        NombreUsuario = (TextView) rootView.findViewById(R.id.txtNombreUsuario);
        Rubros = (TextView) rootView.findViewById(R.id.txtRubro);
        FechaInicio = (TextView) rootView.findViewById(R.id.txtFechaInicio);
        FechaFin = (TextView) rootView.findViewById(R.id.txtFechaFin);
        Servicios = (TextView) rootView.findViewById(R.id.txtServicio);
        Comentarios = (TextView) rootView.findViewById(R.id.txtComentarios);

        Calificacion = (RatingBar) rootView.findViewById(R.id.rtgCalificcacion);

        try {
            imagen = getArguments().getString("IMAGEN");
            nombreUsuario = this.getArguments().getString("NOMBRE");
            rubros = this.getArguments().getString("RUBROS");
            fechaInicio = this.getArguments().getString("INICIO");
            fechaFin = this.getArguments().getString("FIN");
            servicios = this.getArguments().getString("SERVICIOS");
            comentarios = this.getArguments().getString("COMENTARIOS");
            calificacion = this.getArguments().getString("CALIFICACION");
        }catch (Exception e){
            e.printStackTrace();
        }

        asignarDatosUsuario();

        return rootView;
    }

    public void asignarDatosUsuario(){
        Glide.with(mCtx).load(imagen).into(ImagenPersona);
        NombreUsuario.setText(nombreUsuario);
        if(rubros == null) {
            rubros = "Sin Asignar";
        }
        Rubros.setText(rubros);
        FechaInicio.setText(fechaInicio);
        FechaFin.setText(fechaFin);
        Servicios.setText(servicios);
        Comentarios.setText(comentarios);
        if(comentarios.equals("")){
           comentarios = "Sin asignar";
        }
        Calificacion.setRating(Float.valueOf(calificacion + "f"));
    }
}
