package pe.app.com.demo;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.app.com.demo.adapters.HistorialAdapter;
import pe.app.com.demo.entity.Historial;

public class ContenidoHistorial extends Fragment {

    private RecyclerView recyclerView;
    private HistorialAdapter historialAdapter;
    private List<Historial> historialList;

    Context mCtx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_historial, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerHistorial);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        historialList = new ArrayList<>();

        cargaRecyclerHistorial();

        return rootView;

    }

    public void cargaRecyclerHistorial(){
        //for (int i = 1; i <= 5; i++) {
        Historial historial = new Historial(
                "Juan Perez",
                "Pendiente",
                "Pintar fachad de casa",
                "5",
                "10/06/2017",
                R.drawable.avatar_n1
        );
        historialList.add(historial);

        Historial historial1 = new Historial(
                "Peter Chauca",
                "Pendiente",
                "Pintar azotea de casa",
                "2",
                "22/05/2017",
                R.drawable.avatar_n3
        );
        historialList.add(historial1);

        Historial historial2 = new Historial(
                "Jim Aguilar",
                "Rechazado",
                "Pintar cerca",
                "1",
                "10/05/2017",
                R.drawable.avatar_n1
        );
        historialList.add(historial2);

        Historial historial3 = new Historial(
                "Fatima Tamara",
                "Terminado",
                "Administración consulta",
                "3",
                "10/05/2017",
                R.drawable.avatar_n2
        );
        historialList.add(historial3);

        Historial historial4 = new Historial(
                "Zosimo Chaupis Perez",
                "Pendiente",
                "Contruccion de 2do Piso de casa",
                "50",
                "02/05/2017",
                R.drawable.avatar_n3
        );
        historialList.add(historial4);

        //}

        historialAdapter = new HistorialAdapter(historialList,mCtx);
        recyclerView.setAdapter(historialAdapter);
    }

}
