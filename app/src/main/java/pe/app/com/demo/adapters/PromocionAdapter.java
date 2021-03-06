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
import pe.app.com.demo.comunicators.ComunicadorPromocionXDetalle;
import pe.app.com.demo.entity.Promocion;

public class PromocionAdapter extends RecyclerView.Adapter<PromocionAdapter.ViewHolder>{

    private List<Promocion> listaPromociones;
    private ComunicadorPromocionXDetalle comunicador;
    private Context mCtx;

    public PromocionAdapter(List<Promocion> promocion, Context ctx, ComunicadorPromocionXDetalle comunicadorPromocionXDetalle){//, ComunicadorPromocionXDetalle comunicadorPromocionXDetalle){
        this.listaPromociones = promocion;
        this.mCtx = ctx;
        this.comunicador = comunicadorPromocionXDetalle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promocion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Promocion promocion = listaPromociones.get(position);
        Glide.with(holder.imageViewImagenPromotor.getContext()).load(promocion.getFoto()).into(holder.imageViewImagenPromotor);
        holder.textViewNombrePromotor.setText(promocion.getNombrePromotor());
        holder.textViewCantidadPromociones.setText(promocion.getCantidadPromociones());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_promociones);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_PromocionesVerDetalle:
                                comunicador.comunicarDetallePromocion(promocion.getNombrePromotor());
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
        return listaPromociones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewImagenPromotor;
        public TextView textViewNombrePromotor;
        public TextView textViewCantidadPromociones;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewImagenPromotor = (ImageView) itemView.findViewById(R.id.imagenPromotor);
            textViewNombrePromotor = (TextView) itemView.findViewById(R.id.txtNombrePromocionador);
            textViewCantidadPromociones = (TextView) itemView.findViewById(R.id.txtCantidadPromociones);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
