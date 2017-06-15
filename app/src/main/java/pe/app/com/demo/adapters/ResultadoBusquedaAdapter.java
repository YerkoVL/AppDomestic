package pe.app.com.demo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import pe.app.com.demo.PerfilActivity;
import pe.app.com.demo.R;
import pe.app.com.demo.comunicators.ComunicadorAdapters;
import pe.app.com.demo.comunicators.ComunicadorFragment;
import pe.app.com.demo.entity.ResultadoBusqueda;

import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_APELLIDOS_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_DIRECCION_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_DNI_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_ID_USUARIO;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LATITUD_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_LONGITUD_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_NOMBRES_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PERFIL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_PERSONAL;
import static pe.app.com.demo.tools.GenericEstructure.PREFERENCIA_VALOR_PERFIL;

public class ResultadoBusquedaAdapter extends RecyclerView.Adapter<ResultadoBusquedaAdapter.ViewHolder>{

    private List<ResultadoBusqueda> resultadoBusquedaList;
    private ComunicadorAdapters mCommunicator;
    ComunicadorAdapters mComminication;
    private Context mCtx;

    public ResultadoBusquedaAdapter(List<ResultadoBusqueda> resultadoBusqueda, Context ctx,ComunicadorAdapters communication){
        this.resultadoBusquedaList = resultadoBusqueda;
        this.mCtx = ctx;
        mCommunicator=communication;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado_busqueda, parent, false);
        ViewHolder holder = new ViewHolder(v,mCommunicator);
        //return new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ResultadoBusqueda resultadoBusqueda = resultadoBusquedaList.get(position);

        Glide.with(holder.imgViewSolucionador.getContext()).load(resultadoBusqueda.getImagen()).into(holder.imgViewSolucionador);
        holder.textViewNombre.setText(resultadoBusqueda.getNombres() + " " + resultadoBusqueda.getApellidos());
        holder.textViewServicio.setText(resultadoBusqueda.getServicio());
        holder.textViewAtenciones.setText(resultadoBusqueda.getAtenciones());
        holder.rtbViewReputacion.setRating(resultadoBusqueda.getRating());
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(mCtx,holder.buttonViewOption);
                popupMenu.inflate(R.menu.menu_resultado_busqueda);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_ResultadoBusquedaVerContacto:
                                guardarDatosPersonalDisponible(resultadoBusqueda.getId(),resultadoBusqueda.getNombres(),resultadoBusqueda.getApellidos(),resultadoBusqueda.getDireccion(),resultadoBusqueda.getNroDocumento(),resultadoBusqueda.getLatitud(),resultadoBusqueda.getLongitud());
                                guardarSharedPreferencesValor();
                                mComminication.comunicarResultadoPerfil(
                                        resultadoBusqueda.getId(),
                                        resultadoBusqueda.getImagen(),
                                        resultadoBusqueda.getNombres());
                                break;
                            case R.id.menu_ResultadoBusquedaEnviar:
                                guardarDatosPersonalDisponible(resultadoBusqueda.getId(),resultadoBusqueda.getNombres(),resultadoBusqueda.getApellidos(),resultadoBusqueda.getDireccion(),resultadoBusqueda.getNroDocumento(),resultadoBusqueda.getLatitud(),resultadoBusqueda.getLongitud());
                                //ENVIAR
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

        public ViewHolder(View itemView, ComunicadorAdapters Communicator) {
            super(itemView);

            imgViewSolucionador = (ImageView) itemView.findViewById(R.id.imagenSolucionador);
            textViewNombre = (TextView) itemView.findViewById(R.id.txtNombreSolucionador);
            textViewServicio = (TextView) itemView.findViewById(R.id.txtServicio);
            textViewAtenciones = (TextView) itemView.findViewById(R.id.txtAtenciones);
            rtbViewReputacion = (RatingBar) itemView.findViewById(R.id.rntgReputacion);
            buttonViewOption = (TextView) itemView.findViewById(R.id.txtOptionPulse);

            mComminication=Communicator;
        }
    }

    public void guardarDatosPersonalDisponible(String idPersonal, String nomPersonal, String apePersonal, String dirPersonal, String dniPersonal, String latPersonal, String longPersonal){
        SharedPreferences.Editor editor = mCtx.getSharedPreferences(PREFERENCIA_PERSONAL, mCtx.MODE_PRIVATE).edit();
        editor.putString(PREFERENCIA_ID_PERSONAL, idPersonal);
        editor.putString(PREFERENCIA_NOMBRES_PERSONAL, nomPersonal);
        editor.putString(PREFERENCIA_APELLIDOS_PERSONAL, apePersonal);
        editor.putString(PREFERENCIA_DIRECCION_PERSONAL, dirPersonal);
        editor.putString(PREFERENCIA_DNI_PERSONAL, dniPersonal);
        editor.putString(PREFERENCIA_LATITUD_PERSONAL, latPersonal);
        editor.putString(PREFERENCIA_LONGITUD_PERSONAL, longPersonal);
        editor.commit();
        }

    public void guardarSharedPreferencesValor() {
        SharedPreferences.Editor editor = mCtx.getSharedPreferences(PREFERENCIA_PERFIL, mCtx.MODE_PRIVATE).edit();
        editor.putString(PREFERENCIA_VALOR_PERFIL, PREFERENCIA_VALOR_PERFIL);
        editor.commit();
    }
}
