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

import com.bumptech.glide.Glide;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.entity.DetallePromocion;

public class DetallePromocionAdapter extends RecyclerView.Adapter<DetallePromocionAdapter.ViewHolder>{

    private List<DetallePromocion> listaDetallePromocion;
    //private ComunicadorDetalleXAceptar comunicador;
    private Context mCtx;

    public DetallePromocionAdapter(List<DetallePromocion> detallePromocion, Context ctx){//, ComunicadorPromocionXDetalle comunicadorPromocionXDetalle){
        this.listaDetallePromocion = detallePromocion;
        this.mCtx = ctx;
        //this.comunicador = comunicadorPromocionXDetalle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detalle_oferta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DetallePromocion detalleOferta = listaDetallePromocion.get(position);
        Glide.with(holder.imageViewImagenOferta.getContext()).load(detalleOferta.getFoto()).into(holder.imageViewImagenOferta);
        holder.textViewNombreOferta.setText(detalleOferta.getNombreOferta());
        holder.textViewDescripcionOferta.setText(detalleOferta.getDescripcionOferta());
        holder.textViewCodigoOferta.setText(detalleOferta.getCodigoOferta());
        holder.buttonViewmOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewmOption);
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
        return listaDetallePromocion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewImagenOferta;
        public TextView textViewNombreOferta;
        public TextView textViewDescripcionOferta;
        public TextView textViewCodigoOferta;
        public TextView buttonViewmOption;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewImagenOferta = (ImageView) itemView.findViewById(R.id.imagenOferta);
            textViewNombreOferta = (TextView) itemView.findViewById(R.id.txxNombreOferta);
            textViewDescripcionOferta = (TextView) itemView.findViewById(R.id.txtDescripcionOferta);
            textViewCodigoOferta = (TextView) itemView.findViewById(R.id.txtCodigoOferta);
            buttonViewmOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
