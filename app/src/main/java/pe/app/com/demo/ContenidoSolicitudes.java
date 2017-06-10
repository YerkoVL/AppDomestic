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

import pe.app.com.demo.adapters.SolicitudAdapter;
import pe.app.com.demo.entity.Solicitud;

public class ContenidoSolicitudes extends Fragment {

    private RecyclerView recyclerView;
    private SolicitudAdapter solicitudAdapter;
    private List<Solicitud> solicitudList;

    Context mCtx;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_contenido_solicitudes, container, false);

        mCtx = rootView.getContext();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerSolicitudes);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        solicitudList = new ArrayList<>();

        cargaRecyclerSolicitudes();

        return rootView;

    }

    public void cargaRecyclerSolicitudes(){
        //for (int i = 1; i <= 5; i++) {
        Solicitud solicitud = new Solicitud(
                "06/06/2017",
                "Pintar la fachada de casa y empastado",
                "22/06/2017",
                "28/06/2017");
        solicitudList.add(solicitud);

        Solicitud solicitud1 = new Solicitud(
                "05/06/2017",
                "Pintar la sala y comedor de casa",
                "19/06/2017",
                "23/06/2017");
        solicitudList.add(solicitud1);

        Solicitud solicitud2 = new Solicitud(
                "07/06/2017",
                "Pintar la cocina de la casa y patio",
                "21/06/2017",
                "22/06/2017");
        solicitudList.add(solicitud2);

        Solicitud solicitud3 = new Solicitud(
                "09/06/2017",
                "Pintar la cocina de la casa y patio",
                "20/06/2017",
                "25/06/2017");
        solicitudList.add(solicitud3);

        Solicitud solicitud4 = new Solicitud(
                "10/06/2017",
                "Pintar la fachada de casa de Juan y cobertor",
                "10/06/2017",
                "15/06/2017");
        solicitudList.add(solicitud4);

        //}

        solicitudAdapter = new SolicitudAdapter(solicitudList,mCtx);
        recyclerView.setAdapter(solicitudAdapter);
    }
}
