package pe.app.com.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.app.com.demo.adapters.DetallePromocionAdapter;
import pe.app.com.demo.entity.DetallePromocion;

public class ContenidoDetallePromociones extends Fragment{
    Context mCtx;

    private RecyclerView recyclerView;
    private DetallePromocionAdapter detallePromocionAdapter;
    private List<DetallePromocion> detallePromocionList;

    String CODIGO_RETORNO ="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_detalle_promociones, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerDetallePromociones);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            CODIGO_RETORNO = this.getArguments().getString("VALOR");
        }catch (Exception e){
            e.printStackTrace();
        }

        detallePromocionList = new ArrayList<>();

        if(CODIGO_RETORNO.equals("MAESTRO")){
            obtenerDetallePromocionesMaestro();
        }else{
            if(CODIGO_RETORNO.equals("SODIMAC HOME CENTER")){
                obtenerDetallePromocionHomecenter();
            }else{
                obtenerDetallePromocionesWong();
            }
        }

        return rootView;
    }

    private void obtenerDetallePromocionesMaestro(){
        DetallePromocion detallePromocion = new DetallePromocion(
                "1",
                "1",
                "20% en cámaras de vigilancia",
                "CAMDOMESTIC",
                R.drawable.ic_promotor_maestro);

        detallePromocionList.add(detallePromocion);

        detallePromocionAdapter = new DetallePromocionAdapter(detallePromocionList, mCtx);
        recyclerView.setAdapter(detallePromocionAdapter);
    }

    private void obtenerDetallePromocionHomecenter(){
        DetallePromocion detallePromocion = new DetallePromocion(
                "1",
                "1",
                "10% en productos de limpieza",
                "LIMPDOMESTIC",
                R.drawable.ic_promotor_homecenter);
        detallePromocionList.add(detallePromocion);

        DetallePromocion detallePromocion1 = new DetallePromocion(
                "1",
                "2",
                "15% en productos de campo",
                "CAMPDOMESTIC",
                R.drawable.ic_promotor_homecenter);
        detallePromocionList.add(detallePromocion);
        detallePromocionList.add(detallePromocion1);

        detallePromocionAdapter = new DetallePromocionAdapter(detallePromocionList, mCtx);
        recyclerView.setAdapter(detallePromocionAdapter);
    }
    private void obtenerDetallePromocionesWong(){
        DetallePromocion detallePromocion = new DetallePromocion(
                "1",
                "1",
                "25% en útiles de repostería",
                "WONGDOMESTIC",
                R.drawable.ic_promotor_wong);

        detallePromocionList.add(detallePromocion);

        detallePromocionAdapter = new DetallePromocionAdapter(detallePromocionList, mCtx);
        recyclerView.setAdapter(detallePromocionAdapter);
    }
}
