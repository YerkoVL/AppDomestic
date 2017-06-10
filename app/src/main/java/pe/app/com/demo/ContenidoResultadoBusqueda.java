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

import pe.app.com.demo.adapters.ResultadoBusquedaAdapter;
import pe.app.com.demo.entity.ResultadoBusqueda;

public class ContenidoResultadoBusqueda extends Fragment {

    private RecyclerView recyclerView;
    private ResultadoBusquedaAdapter resultadoBusquedaAdapter;
    private List<ResultadoBusqueda> resultadoBusquedaList;

    Context mCtx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_resultado_busqueda, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerResultadoBusqueda);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        resultadoBusquedaList = new ArrayList<>();

        cargaRecyclerResultadosBusqueda();

        return rootView;

    }

    public void cargaRecyclerResultadosBusqueda(){
        //for (int i = 1; i <= 5; i++) {
        ResultadoBusqueda resultadoBusqueda = new ResultadoBusqueda(
                "Juan Perez",
                "Albañil, Gasfitero, Pintor",
                "8",
                4.5f,
                R.drawable.avatar_n1
        );

        resultadoBusquedaList.add(resultadoBusqueda);

        ResultadoBusqueda resultadoBusqueda1 = new ResultadoBusqueda(
                "Julia Lezama",
                "Gasfitera, Pintora",
                "25",
                2.5f,
                R.drawable.avatar_n2
        );

        resultadoBusquedaList.add(resultadoBusqueda1);

        ResultadoBusqueda resultadoBusqueda2 = new ResultadoBusqueda(
                "Jim Aguilar",
                "Pintor",
                "5",
                5f,
                R.drawable.avatar_n3
        );

        resultadoBusquedaList.add(resultadoBusqueda2);

        ResultadoBusqueda resultadoBusqueda3 = new ResultadoBusqueda(
                "Zosimo CHaupis",
                "Gasfitería, Albañil",
                "10",
                4.5f,
                R.drawable.avatar_n1
        );

        resultadoBusquedaList.add(resultadoBusqueda3);

        ResultadoBusqueda resultadoBusqueda4 = new ResultadoBusqueda(
                "Fatima Tamara",
                "Administradora, Empresaria",
                "12",
                4.5f,
                R.drawable.avatar_n2
        );

        resultadoBusquedaList.add(resultadoBusqueda4);
        //}

        resultadoBusquedaAdapter = new ResultadoBusquedaAdapter(resultadoBusquedaList,mCtx);
        recyclerView.setAdapter(resultadoBusquedaAdapter);
    }
}
