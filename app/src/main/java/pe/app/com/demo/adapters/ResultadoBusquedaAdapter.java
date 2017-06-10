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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.app.com.demo.R;
import pe.app.com.demo.entity.ResultadoBusqueda;

public class ResultadoBusquedaAdapter extends RecyclerView.Adapter<ResultadoBusquedaAdapter.ViewHolder>{

    private List<ResultadoBusqueda> resultadoBusquedaList;
    private Context mCtx;

    public ResultadoBusquedaAdapter(List<ResultadoBusqueda> resultadoBusqueda, Context ctx){
        this.resultadoBusquedaList = resultadoBusqueda;
        this.mCtx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado_busqueda, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ResultadoBusqueda resultadoBusqueda = resultadoBusquedaList.get(position);
        Glide.with(holder.imgViewSolucionador.getContext()).load(resultadoBusqueda.getImagen()).into(holder.imgViewSolucionador);
        holder.textViewNombre.setText(resultadoBusqueda.getNombre());
        holder.textViewServicio.setText(resultadoBusqueda.getServicios());
        holder.textViewAtenciones.setText(resultadoBusqueda.getAtenciones());
        holder.rtbViewReputacion.setRating(resultadoBusqueda.getRating());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_resultado_busqueda);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_ResultadoBusquedaVerContacto:
                                Toast.makeText(mCtx,"Ver Contacto",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_ResultadoBusquedaEnviar:
                                resultadoBusquedaList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mCtx,"Enviado",Toast.LENGTH_SHORT).show();
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
        return resultadoBusquedaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgViewSolucionador;
        public TextView textViewNombre;
        public TextView textViewServicio;
        public TextView textViewAtenciones;
        public RatingBar rtbViewReputacion;
        public TextView buttonViewOption;

        public ViewHolder(View itemView) {
            super(itemView);

            imgViewSolucionador = (ImageView) itemView.findViewById(R.id.imagenSolucionador);
            textViewNombre = (TextView) itemView.findViewById(R.id.txtNombreSolucionador);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewAtenciones = (TextView) itemView.findViewById(R.id.txtAtenciones);
            rtbViewReputacion = (RatingBar) itemView.findViewById(R.id.rntgReputacion);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);
        }
    }
}
