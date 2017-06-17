package pe.app.com.demo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.entity.HistorialTrabajos;

public class HistorialTrabajosAdapter extends RecyclerView.Adapter<HistorialTrabajosAdapter.ViewHolder>{

    private List<HistorialTrabajos> listaHistorialTrabajos;
    private Context mCtx;

    public HistorialTrabajosAdapter(List<HistorialTrabajos> historialTrabajos, Context ctx){
        this.listaHistorialTrabajos = historialTrabajos;
        this.mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial_trabajos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final HistorialTrabajos historialTrabajos = listaHistorialTrabajos.get(position);
        holder.textViewServicio.setText(historialTrabajos.getServicio());
        holder.textViewInicioServicio.setText(historialTrabajos.getFechaInicio());
        holder.textViewFinServicio.setText(historialTrabajos.getFechaFin());
        holder.ratingBarViewReputacion.setRating(Float.valueOf(historialTrabajos.getCalificacion()));
        holder.textViewComentario.setText(historialTrabajos.getComentario());
    }

    @Override
    public int getItemCount() {
        return listaHistorialTrabajos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewServicio;
        public TextView textViewInicioServicio;
        public TextView textViewFinServicio;
        public RatingBar ratingBarViewReputacion;
        public TextView textViewComentario;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewInicioServicio = (TextView) itemView.findViewById(R.id.txtFechaInicio);
            textViewFinServicio = (TextView) itemView.findViewById(R.id.txtFinServicio);
            ratingBarViewReputacion = (RatingBar) itemView.findViewById(R.id.rntgReputacion);
            textViewComentario = (TextView) itemView.findViewById(R.id.txtComentarios);
        }
    }
}
