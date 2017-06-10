package pe.app.com.demo.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.entity.Solicitud;

public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.ViewHolder>{

    private List<Solicitud> listaSolicitud;
    private Context mCtx;

    public SolicitudAdapter(List<Solicitud> solicitud,Context ctx){
        this.listaSolicitud = solicitud;
        this.mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Solicitud solicitud = listaSolicitud.get(position);
        holder.textViewFechaSolicitud.setText(solicitud.getFechaSolicitud());
        holder.textViewServicio.setText(solicitud.getServicio());
        holder.textViewFechaInicio.setText(solicitud.getFechaInicio());
        holder.textViewFechaFin.setText(solicitud.getFechaFin());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_solicitudes);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_SolicitudesVerPersonal:
                                Toast.makeText(mCtx,"Ver Personal",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_SolicitudesVerDetalle:
                                Toast.makeText(mCtx,"Ver Detalle",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_SolicitudesCancelar:
                                listaSolicitud.remove(position);
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
        return listaSolicitud.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewFechaSolicitud;
        public TextView textViewServicio;
        public TextView textViewFechaInicio;
        public TextView textViewFechaFin;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewFechaSolicitud = (TextView) itemView.findViewById(R.id.txtFechaSolicitud);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewFechaInicio = (TextView) itemView.findViewById(R.id.txtFechaInicio);
            textViewFechaFin = (TextView) itemView.findViewById(R.id.txtFechaFin);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
