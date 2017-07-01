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

import pe.app.com.demo.adapters.PromocionAdapter;
import pe.app.com.demo.entity.Promocion;

public class ContenidoPromociones extends Fragment{
    Context mCtx;

    private RecyclerView recyclerView;
    private PromocionAdapter promocionAdapter;
    private List<Promocion> promocionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_promociones, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerPromociones);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        promocionList = new ArrayList<>();

        obtenerPromociones();

        return rootView;
    }

    private void obtenerPromociones(){
        Promocion promocion1 = new Promocion(
                "1",
                 "MAESTRO",
                "1",
                R.drawable.ic_promotor_maestro);
        Promocion promocion2 = new Promocion(
                "1",
                "SODIMAC HOME CENTER",
                "2",
                R.drawable.ic_promotor_homecenter);
        Promocion promocion3 = new Promocion(
                "1",
                "WONG",
                "1",
                R.drawable.ic_promotor_wong);

        promocionList.add(promocion1);
        promocionList.add(promocion2);
        promocionList.add(promocion3);

        promocionAdapter = new PromocionAdapter(promocionList, mCtx);
        recyclerView.setAdapter(promocionAdapter);
    }
}
