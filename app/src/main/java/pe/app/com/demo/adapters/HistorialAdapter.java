package pe.app.com.demo.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.comunicators.ComunicadorHistorialXDetalle;
import pe.app.com.demo.entity.Historial;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder>{

    private List<Historial> listaHistorial;
    private ComunicadorHistorialXDetalle comunicador;
    private Context mCtx;

    public HistorialAdapter(List<Historial> historial, Context ctx, ComunicadorHistorialXDetalle comunicadorHistorialXDetalle){
        this.listaHistorial = historial;
        this.mCtx = ctx;
        this.comunicador = comunicadorHistorialXDetalle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Historial historial = listaHistorial.get(position);
        Glide.with(holder.imgViewSolucionador.getContext()).load(historial.getFoto()).into(holder.imgViewSolucionador);
        holder.textViewNombre.setText(historial.getSocio());
        holder.textViewEstadoAtencion.setText(historial.getDesc_Estado());
        holder.textViewServicio.setText(historial.getServicio());
        holder.textViewInicioServicio.setText(historial.getFechaInicio());
        holder.textViewFinServicio.setText(historial.getFechaFin());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                if(Integer.valueOf(historial.getIdEstado())==7){
                    popupMenu.inflate(R.menu.menu_historial_terminados);
                }else {
                    popupMenu.inflate(R.menu.menu_historial);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_HistorialFinalizar:
                                listaHistorial.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mCtx,"Finalizado",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_HistorialVerDetalle:
                                comunicador.comunicarHistorial(
                                        historial.getFoto(),
                                        historial.getSocio(),
                                        historial.getDescripcionRubro(),
                                        historial.getFechaInicio(),
                                        historial.getFechaFin(),
                                        historial.getServicio(),
                                        historial.getComentario(),
                                        historial.getCalificacion()
                                );
                                break;
                            case R.id.menu_HistorialCancelar:
                                listaHistorial.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mCtx,"Cancelado",Toast.LENGTH_SHORT).show();
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
        return listaHistorial.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgViewSolucionador;
        public TextView textViewNombre;
        public TextView textViewServicio;
        public TextView textViewEstadoAtencion;
        public TextView textViewInicioServicio;
        public TextView textViewFinServicio;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            imgViewSolucionador = (ImageView) itemView.findViewById(R.id.imagenSolucionador);
            textViewNombre = (TextView) itemView.findViewById(R.id.txtNombreSolucionador);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewEstadoAtencion = (TextView) itemView.findViewById(R.id.txtEstadoAtencion);
            textViewInicioServicio = (TextView) itemView.findViewById(R.id.txtFechaInicio);
            textViewFinServicio = (TextView) itemView.findViewById(R.id.txtFinServicio);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
