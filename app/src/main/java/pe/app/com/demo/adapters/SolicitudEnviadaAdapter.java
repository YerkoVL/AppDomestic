package pe.app.com.demo.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.comunicators.ComunicadorSolicitudesXDetalle;
import pe.app.com.demo.comunicators.ComunicadorSolicitudesXEnviados;
import pe.app.com.demo.entity.Solicitud;
import pe.app.com.demo.entity.SolicitudesEnviadas;

public class SolicitudEnviadaAdapter extends RecyclerView.Adapter<SolicitudEnviadaAdapter.ViewHolder>{

    private List<SolicitudesEnviadas> listaSolicitudesEnviadas;
    private Context mCtx;

    public SolicitudEnviadaAdapter(List<SolicitudesEnviadas> solicitudesEnviadas, Context ctx){
        this.listaSolicitudesEnviadas = solicitudesEnviadas;
        this.mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_enviada, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SolicitudesEnviadas solicitudesEnviadas = listaSolicitudesEnviadas.get(position);
        holder.textViewNombrePersonal.setText(solicitudesEnviadas.getNombres() + " " +solicitudesEnviadas.getApellidos() );
        holder.textViewServicio.setText(solicitudesEnviadas.getSERVICIO());
        holder.textViewAtenciones.setText(solicitudesEnviadas.getAtenciones());
        holder.textViewEstado.setText(solicitudesEnviadas.getDesc_Estado());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_solicitudes);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){

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
        return listaSolicitudesEnviadas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewNombrePersonal;
        public TextView textViewServicio;
        public TextView textViewAtenciones;
        public RatingBar rtnViewReputacion;
        public TextView textViewEstado;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNombrePersonal = (TextView) itemView.findViewById(R.id.txtNombrePersonal);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewAtenciones = (TextView) itemView.findViewById(R.id.);
            rtnViewReputacion = (TextView) itemView.findViewById(R.id.);
            textViewEstado = (TextView) itemView.findViewById(R.id.txtEstado);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
