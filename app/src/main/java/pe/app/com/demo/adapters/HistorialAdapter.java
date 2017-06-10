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
import pe.app.com.demo.entity.Historial;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder>{

    private List<Historial> listaHistorial;
    private Context mCtx;

    public HistorialAdapter(List<Historial> historial, Context ctx){
        this.listaHistorial = historial;
        this.mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Historial historial = listaHistorial.get(position);
        Glide.with(holder.imgViewSolucionador.getContext()).load(historial.getImagen()).into(holder.imgViewSolucionador);
        holder.textViewNombre.setText(historial.getNombre());
        holder.textViewEstadoAtencion.setText(historial.getEstado());
        holder.textViewServicio.setText(historial.getServicio());
        holder.textViewDiasEstimados.setText(historial.getDiasEstimados());
        holder.textViewFinServicio.setText(historial.getFinServicio());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_historial);
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
                                Toast.makeText(mCtx,"Ver Detalle",Toast.LENGTH_SHORT).show();
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
        public TextView textViewDiasEstimados;
        public TextView textViewFinServicio;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            imgViewSolucionador = (ImageView) itemView.findViewById(R.id.imagenSolucionador);
            textViewNombre = (TextView) itemView.findViewById(R.id.txtNombreSolucionador);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewEstadoAtencion = (TextView) itemView.findViewById(R.id.txtEstadoAtencion);
            textViewDiasEstimados = (TextView) itemView.findViewById(R.id.txtDiasEstimados);
            textViewFinServicio = (TextView) itemView.findViewById(R.id.txtFinServicio);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
