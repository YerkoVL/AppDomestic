package pe.app.com.demo.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.comunicators.ComunicadorHistorialXCalificación;
import pe.app.com.demo.entity.SolicitudesEnviadas;

public class SolicitudEnviadaAdapter extends RecyclerView.Adapter<SolicitudEnviadaAdapter.ViewHolder>{

    private List<SolicitudesEnviadas> listaSolicitudesEnviadas;
    private ComunicadorHistorialXCalificación comunicadorCalificacion;
    private Context mCtx;

    public SolicitudEnviadaAdapter(List<SolicitudesEnviadas> solicitudesEnviadas, Context ctx,ComunicadorHistorialXCalificación comunicadorHistorialXCalificación){
        this.listaSolicitudesEnviadas = solicitudesEnviadas;
        this.mCtx = ctx;
        this.comunicadorCalificacion = comunicadorHistorialXCalificación;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud_enviada, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SolicitudesEnviadas solicitudesEnviadas = listaSolicitudesEnviadas.get(position);
        Glide.with(holder.imgViewPersonal.getContext()).load(solicitudesEnviadas.getImagen()).into(holder.imgViewPersonal);
        holder.textViewNombrePersonal.setText(solicitudesEnviadas.getNombres() + " " +solicitudesEnviadas.getApellidos() );
        holder.textViewServicio.setText(solicitudesEnviadas.getSERVICIO());
        holder.textViewAtenciones.setText(solicitudesEnviadas.getAtenciones());
        holder.rtnViewReputacion.setRating(Float.valueOf(solicitudesEnviadas.getRating() + "f"));
        holder.textViewEstado.setText(solicitudesEnviadas.getDesc_Estado());
        if(Integer.valueOf(solicitudesEnviadas.getIdEstado())!=11){
            holder.buttonViewOption.setVisibility(View.GONE);
        }
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                if(Integer.valueOf(solicitudesEnviadas.getIdEstado())==11){
                    popupMenu.inflate(R.menu.menu_solicitudes_enviadas);
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_SolicitudesEnviadasCerrar:
                                comunicadorCalificacion.comunicarHistorial(solicitudesEnviadas.getNombres() + " " + solicitudesEnviadas.getApellidos()
                                        ,Integer.valueOf(solicitudesEnviadas.getIdUsuario()),
                                        Integer.valueOf(solicitudesEnviadas.getId()));
                                notifyDataSetChanged();
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
        return listaSolicitudesEnviadas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgViewPersonal;
        public TextView textViewNombrePersonal;
        public TextView textViewServicio;
        public TextView textViewAtenciones;
        public RatingBar rtnViewReputacion;
        public TextView textViewEstado;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            imgViewPersonal = (ImageView) itemView.findViewById(R.id.imagenPersonal);
            textViewNombrePersonal = (TextView) itemView.findViewById(R.id.txtNombrePersonal);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewAtenciones = (TextView) itemView.findViewById(R.id.txtAtenciones);
            rtnViewReputacion = (RatingBar) itemView.findViewById(R.id.rntgReputacion);
            textViewEstado = (TextView) itemView.findViewById(R.id.txtEstado);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
